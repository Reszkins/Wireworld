package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

public class CanvasEvents {
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
        return onMouseDraggedEventHandler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }

    //public EventHandler<MouseEvent> getOnClickEventHandler() {return onClickEventHandler; }

    private final EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<>() {
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

    private final EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(!mouseEvent.isPrimaryButtonDown())
                return;

            canvas.setTranslateX(translateX + mouseEvent.getSceneX() - mouseAnchorX);
            canvas.setTranslateY(translateY + mouseEvent.getSceneY() - mouseAnchorY);

            mouseEvent.consume();
        }
    };

    private final EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<>() {
        @Override
        public void handle(ScrollEvent scrollEvent) {
            double d = 2.0d;

            double scale = canvas.getScale();
            double prevScale = scale;

            scale = scrollEvent.getDeltaY() > 0 ? scale*d : scale/d;

            double f = (scale/prevScale)-1;

            double dx = (scrollEvent.getX() - (canvas.getBoundsInParent().getWidth()/2 + canvas.getBoundsInParent().getMinX()));
            double dy = (scrollEvent.getY() - (canvas.getBoundsInParent().getHeight()/2 + canvas.getBoundsInParent().getMinY()));

            canvas.setScale(scale);

            canvas.setPivot(dx*f, dy*f);

            scrollEvent.consume();
        }
    };

    /*
    private final EventHandler<MouseEvent> onClickEventHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(!mouseEvent.isSecondaryButtonDown())
                return;
            canvas.draw(canvas.getPositionByPixel(mouseEvent.getY()),canvas.getPositionByPixel(mouseEvent.getX()), Color.DARKRED);
        }
    };
    */
}
