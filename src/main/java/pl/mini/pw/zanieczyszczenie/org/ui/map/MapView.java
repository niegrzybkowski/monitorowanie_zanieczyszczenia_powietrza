package pl.mini.pw.zanieczyszczenie.org.ui.map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;


public class MapView {

    private static final String IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/9/9d/Poland_CIA_map_PL.png";

    private final double width;
    private final double height;
    private final ImageView view;

    private static final double longitudeLow = 14.0;
    private static final double longitudeHigh = 25.0;

    private static final double latitudeLow = 55.0;
    private static final double latitudeHigh = 48.0;

    private final List<Point2D> pois = new ArrayList<>();

    public MapView() {
        var img = new Image(IMAGE_URL);
        this.width = img.getWidth();
        this.height = img.getHeight();
        view = new ImageView(img);
        view.setPreserveRatio(true);
        resetView();
        addListeners(view);
    }

    private void addListeners(ImageView view) {
        // magia beansowa, stack kazał tak zrobić
        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

        view.setOnMousePressed(e -> {
            Point2D mousePress = viewToImage(new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });

        view.setOnMouseDragged(e -> {
            Point2D dragPoint = viewToImage(new Point2D(e.getX(), e.getY()));
            move(dragPoint.subtract(mouseDown.get()));
            mouseDown.set(viewToImage(new Point2D(e.getX(), e.getY())));
        });

        view.setOnScroll(e -> {
            var viewport = view.getViewport();

            double scale = Math.pow(1.001, e.getDeltaY());

            Point2D mouse = viewToImage(new Point2D(e.getX(), e.getY()));

            double newWidth = Math.min(viewport.getWidth() * scale, width);
            double newHeight = Math.min(viewport.getHeight() * scale, height);
            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, height - newHeight);

            view.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

        view.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                resetView();
            }
        });
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
        double scaleX = viewCoordinates.getX() / view.getBoundsInLocal().getWidth();
        double scaleY = viewCoordinates.getY() / view.getBoundsInLocal().getHeight();

        Rectangle2D viewport = view.getViewport();
        return new Point2D(
                viewport.getMinX() + scaleX * viewport.getWidth(),
                viewport.getMinY() + scaleY * viewport.getHeight()
        );
    }

    private Point2D imageToView() {
        return null;
    }

    private Point2D scaleGeographicToImage(double latitude, double longitude) {
        if(!(latitudeLow <= latitude && latitude <= latitudeHigh &&
                longitudeLow <= longitude && longitude <= longitudeHigh))
            throw new RuntimeException("POI out of bounds in map");

        double xScale = (longitudeHigh - longitudeLow) / width;
        double yScale = (latitudeHigh - latitudeLow) / height;

        double x = (longitude - longitudeLow) * xScale;
        double y = (latitude - latitudeLow) * yScale;
        return new Point2D(x, y);
    }

    public void addPOI(double latitude, double longitude) {
        if(latitudeLow <= latitude && latitude <= latitudeHigh &&
        longitudeLow <= longitude && longitude <= longitudeHigh) {
            pois.add(new Point2D(latitude, longitude));
        }
    }

    public ImageView getView() {
        return view;
    }
}