package input;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ui.UIManager;


public class MouseManager {

    private boolean leftPressed, rightPressed;
    private int mouseX, mouseY;
    private UIManager uiManager;

    Scene scene;

    public MouseManager(Scene scene){
        this.scene = scene;
    }

    public void addListener(){
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleasedEventHandler);
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, mouseMovedEventHandler);

    }

    private EventHandler<MouseEvent> mousePressedEventHandler = e -> {
        if(e.getButton() == MouseButton.PRIMARY)
            leftPressed = true;
        else if(e.getButton() == MouseButton.SECONDARY)
            rightPressed = true;
    };

    private EventHandler<MouseEvent> mouseReleasedEventHandler = e -> {
        if(e.getButton() == MouseButton.PRIMARY)
            leftPressed = false;
        else if(e.getButton() == MouseButton.SECONDARY)
            rightPressed = false;
        if (uiManager != null)
            uiManager.onMouseRelease(e);
    };

    private EventHandler<MouseEvent> mouseMovedEventHandler = e -> {
        mouseX = (int) e.getX();
        mouseY = (int) e.getY();

        if (uiManager != null)
            uiManager.onMouseMove(e);

    };

    //Getters & Setters
    public boolean isLeftPressed(){
        return leftPressed;
    }

    public boolean isRightPressed(){
        return rightPressed;
    }

    public int getMouseX(){
        return mouseX;
    }

    public int getMouseY(){
        return mouseY;
    }

    public void setUiManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }
}
