package GUI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class CanvasEvents {
    private static final double maxScale = 30.0d;
    private static final double minScale = 0.1d;

    private double mouseAnchorX;
    private double mouseAnchorY;

    private double translateX;
    private double translateY;

    WorldCanvas canvas;

    public CanvasEvents(WorldCanvas canvas) {
        this.canvas = canvas;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHadler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHadler;
    }

    public EventHandler<MouseEvent> getOnClickEventHandler() {return onClickEventHandler; }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {

            if(!mouseEvent.isPrimaryButtonDown())
                return;

            mouseAnchorX = mouseEvent.getSceneX();
            mouseAnchorY = mouseEvent.getSceneY();

            translateX = canvas.getTranslateX();
            translateY = canvas.getTranslateY();
        }
    };

    private EventHandler<MouseEvent> onMouseDraggedEventHadler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(!mouseEvent.isPrimaryButtonDown())
                return;

            canvas.setTranslateX(translateX + mouseEvent.getSceneX() - mouseAnchorX);
            canvas.setTranslateY(translateY + mouseEvent.getSceneY() - mouseAnchorY);

            mouseEvent.consume();
        }
    };

    private EventHandler<ScrollEvent> onScrollEventHadler = new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent scrollEvent) {
            double d = 1.1d;

            double scale = canvas.getScale();
            double prevScale = scale;

            scale = scrollEvent.getDeltaY() > 0 ? scale*d : scale/d;
            scale = Math.min(scale, maxScale);
            scale = Math.max(scale,minScale);

            double f = (scale/prevScale)-1;

            double dx = (scrollEvent.getX() - (canvas.getBoundsInParent().getWidth()/2 + canvas.getBoundsInParent().getMinX()));
            double dy = (scrollEvent.getY() - (canvas.getBoundsInParent().getHeight()/2 + canvas.getBoundsInParent().getMinY()));

            canvas.setScale(scale);
            canvas.setScaleX(scale);
            canvas.setScaleY(scale);

            canvas.setPivot(dx*f, dy*f);

            scrollEvent.consume();
        }
    };

    private EventHandler<MouseEvent> onClickEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            System.out.println("Col:" + canvas.getPositionByPixel(mouseEvent.getX()));
            System.out.println("Row:" + canvas.getPositionByPixel(mouseEvent.getY()));
        }
    };

}
