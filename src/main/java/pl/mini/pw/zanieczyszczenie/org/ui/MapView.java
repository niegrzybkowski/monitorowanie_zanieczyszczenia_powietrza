package pl.mini.pw.zanieczyszczenie.org.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;


public class MapView {
    private static final double CIRCLE_SIZE = 10.0;

    private final double width;
    private final double height;
    private final ImageView view;
    private final AnchorPane container;
    private final Group poiGroup;

    private static final double longitudeLow = 13.89;
    private static final double longitudeHigh = 24.5;

    private static final double latitudeLow = 55.2;
    private static final double latitudeHigh = 48.7;

    private final List<POI> pois = new ArrayList<>();

    public MapView() {
        var img = new Image("obr1.png");
        this.width = img.getWidth();
        this.height = img.getHeight();
        view = new ImageView(img);
        view.setPreserveRatio(true);
        resetView();
        addListeners(view);
        container = new AnchorPane(view);
        container.setMinSize(544, 544);
        container.setPrefSize(544, 544);
        container.setMaxSize(544, 544);
        poiGroup = new Group();
        container.getChildren().add(poiGroup);
        view.fitWidthProperty().bind(container.widthProperty());
        view.fitHeightProperty().bind(container.heightProperty());
        drawPOIs();
    }

    private void addListeners(ImageView view) {
        // magia beansowa, stack kazał tak zrobić
        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();
        view.setOnMouseEntered(e -> drawPOIs());

        view.setOnMousePressed(e -> {
            Point2D mousePress = viewToImage(new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
            drawPOIs();
        });

        view.setOnMouseDragged(e -> {
            Point2D dragPoint = viewToImage(new Point2D(e.getX(), e.getY()));
            move(dragPoint.subtract(mouseDown.get()));
            mouseDown.set(viewToImage(new Point2D(e.getX(), e.getY())));
            drawPOIs();
        });

        view.setOnScroll(e -> {
            scale(e.getDeltaY(), new Point2D(e.getX(), e.getY()));
            drawPOIs();
        });

        view.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                resetView();
                drawPOIs();
            }
        });
    }

    private void scale(double delta, Point2D identity) {
        var viewport = view.getViewport();

        double scale = Math.pow(1.001, delta);

        Point2D mouse = viewToImage(new Point2D(identity.getX(), identity.getY()));

        double newWidth = Math.min(viewport.getWidth() * scale, width);
        double newHeight = Math.min(viewport.getHeight() * scale, height);
        double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                0, width - newWidth);
        double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                0, height - newHeight);

        view.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
    }

    private void resetView() {
        view.setViewport(new Rectangle2D(0, 0, width, height));
    }

    private void move(Point2D delta) {
        var viewport = view.getViewport();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, width - viewport.getWidth());
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, height - viewport.getHeight());

        view.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private Point2D viewToImage(Point2D viewCoordinates) {
        double scaledX = viewCoordinates.getX() / view.getFitWidth();
        double scaledY = viewCoordinates.getY() / view.getFitHeight();

        var viewport = view.getViewport();
        return new Point2D(
                viewport.getMinX() + scaledX * viewport.getWidth(),
                viewport.getMinY() + scaledY * viewport.getHeight()
        );
    }

    private Point2D imageToView(Point2D imageCoordinates) {
        var viewport = view.getViewport();
        double scale = viewport.getHeight() / view.getFitHeight();
        return new Point2D(
                (imageCoordinates.getX() - viewport.getMinX()) / scale,
                (imageCoordinates.getY() - viewport.getMinY()) / scale
        );
    }

    private Point2D scaleGeographicToImage(double latitude, double longitude) {
        if(!(latitudeLow > latitude && latitude > latitudeHigh &&
                longitudeLow <= longitude && longitude <= longitudeHigh)) {
            System.err.println("" + latitude + " " + longitude);
            throw new RuntimeException("POI out of bounds in map");
        }
        double xScale = width / (longitudeHigh - longitudeLow);
        double yScale = height / (latitudeHigh - latitudeLow);

        double x = (longitude - longitudeLow) * xScale;
        double y = (latitude - latitudeLow) * yScale;
        return new Point2D(x, y);
    }

    public void drawPOIs() {
        try {
            poiGroup.getChildren().clear();
            Rectangle2D smolView = new Rectangle2D(
                    view.getViewport().getMinX() + 25,
                    view.getViewport().getMinY() + 25,
                    view.getViewport().getWidth() - 50,
                    view.getViewport().getHeight() - 50);
            pois.stream()
                    .filter(c -> smolView.contains(c.originalLocation))
                    .peek(poi -> {
                        var c = poi.representation;
                        Point2D p = imageToView(poi.originalLocation);
                        c.setCenterX(p.getX());
                        c.setCenterY(p.getY());
                    })
                    .forEach(c -> poiGroup.getChildren().add(c.representation));
        } catch (IllegalArgumentException exception) {}
    }

    public void addPOI(double latitude, double longitude, Paint color,
                       EventHandler<? super MouseEvent> eventHandler) {
        Point2D pos = scaleGeographicToImage(latitude, longitude);
        var circle = new Circle(pos.getX(), pos.getY(), CIRCLE_SIZE, color);
        circle.setOnScroll(e -> {
            scale(e.getDeltaY(), new Point2D(e.getX(), e.getY()));
            drawPOIs();
        });
        circle.setOnMouseClicked(eventHandler);

        POI poi = new POI(circle, pos);
        POI poiCurrent = pois.stream().filter(poi1 -> poi1.originalLocation.equals(poi.originalLocation))
                .findFirst().orElse(null);

        if (poiCurrent != null) {
            poiCurrent.setColor(color);
        } else {
            pois.add(poi);
        }
    }
    public void toFront(POI poi) {
        pois.forEach(e -> e.representation.setViewOrder(1));
        poi.representation.setViewOrder(0);
    }

    public List<POI> getPois() {
        return pois;
    }

    public POI getPoi(double lat, double lon) {
        Point2D pos = scaleGeographicToImage(lat,lon);
        for (POI e: getPois()) {
            if (e.originalLocation.equals(pos)) {
                return e;
            }
        }
        return null;
    }

    public AnchorPane getPane() {
        return container;
    }

    public static class POI {
        public Circle representation;
        public Point2D originalLocation;

        public POI(Circle representation, Point2D originalLocation) {
            this.representation = representation;
            this.originalLocation = originalLocation;
        }

        public void setRadius(double radius, Paint color) {
            representation.setRadius(radius);
            representation.setFill(color);
            representation.toFront();
        }
        public void setRadius(double radius) {
            representation.setRadius(radius);
            representation.setStroke(null);
        }
        public void setColor(Paint color) {
            representation.setFill(color);
        }
        public void setStroke(Paint color) {
            representation.setStroke(color);
        }
    }
}