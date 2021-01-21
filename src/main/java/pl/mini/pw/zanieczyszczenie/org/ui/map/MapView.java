package pl.mini.pw.zanieczyszczenie.org.ui.map;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MapView {

    private static final String IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/9/9d/Poland_CIA_map_PL.png";
    private static final int MIN_PIXELS = 10;


    private final double width;
    private final double height;
    private final ImageView view;

    public MapView() {
        var img = new Image(IMAGE_URL);
        this.width = img.getWidth();
        this.height = img.getHeight();
        view = new ImageView(img);
        view.setPreserveRatio(true);
        reset(view, width, height);
        addListeners(view);
    }

    private void addListeners(ImageView view) {
        // magia beansowa, stack kazał tak zrobić
        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

        view.setOnMousePressed(e -> {
            Point2D mousePress = imageViewToImage(view, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });

        view.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewToImage(view, new Point2D(e.getX(), e.getY()));
            shift(view, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(view, new Point2D(e.getX(), e.getY())));
        });

        view.setOnScroll(e -> {
            double delta = e.getDeltaY();
            Rectangle2D viewport = view.getViewport();

            double scale = clamp(Math.pow(1.001, delta),
                    Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),
                    Math.max(width / viewport.getWidth(), height / viewport.getHeight())
            );

            Point2D mouse = imageViewToImage(view, new Point2D(e.getX(), e.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;

            // To keep the visual point under the mouse from moving, we need
            // (x - newViewportMinX) / (x - currentViewportMinX) = scale
            // where x is the mouse X coordinate in the image

            // solving this for newViewportMinX gives

            // newViewportMinX = x - (x - currentViewportMinX) * scale

            // we then clamp this value so the image never scrolls out
            // of the imageview:

            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, height - newHeight);

            view.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

        view.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                reset(view, width, height);
            }
        });
    }

    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    private void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double maxX = imageView.getImage().getWidth() - viewport.getWidth();
        double maxY = imageView.getImage().getHeight() - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight()
        );
    }

    public ImageView getView() {
        return view;
    }
}