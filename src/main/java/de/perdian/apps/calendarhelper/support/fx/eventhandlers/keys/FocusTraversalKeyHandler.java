package de.perdian.apps.calendarhelper.support.fx.eventhandlers.keys;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FocusTraversalKeyHandler implements EventHandler<KeyEvent> {

    private Control previousControl = null;
    private Control nextControl = null;

    public FocusTraversalKeyHandler(Control previousControl, Control nextControl) {
        this.setPreviousControl(previousControl);
        this.setNextControl(nextControl);
    }

    @Override
    public void handle(KeyEvent event) {
        if (KeyCode.TAB.equals(event.getCode())) {
            if (event.isShiftDown()) {
                if (this.getPreviousControl() != null) {
                    event.consume();
                    Platform.runLater(() -> this.getPreviousControl().requestFocus());
                }
            } else {
                if (this.getNextControl() != null) {
                    event.consume();
                    Platform.runLater(() -> this.getNextControl().requestFocus());
                }
            }
        }
    }

    private Control getPreviousControl() {
        return this.previousControl;
    }
    private void setPreviousControl(Control previousControl) {
        this.previousControl = previousControl;
    }

    private Control getNextControl() {
        return this.nextControl;
    }
    private void setNextControl(Control nextControl) {
        this.nextControl = nextControl;
    }

}
