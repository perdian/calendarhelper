package de.perdian.apps.calendarbuddy;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CalendarBuddyKeyRegistry {

    private final List<EventHandler<KeyEvent>> keyEventHandlers = new ArrayList<>();

    CalendarBuddyKeyRegistry() {
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        for (EventHandler<KeyEvent> keyEventHandler : this.getKeyEventHandlers()) {
            if (keyEvent.isConsumed()) {
                break;
            }
            keyEventHandler.handle(keyEvent);
        }
    }

    public void addKeyEventHandler(EventHandler<KeyEvent> keyEventHandler) {
        this.getKeyEventHandlers().add(keyEventHandler);
    }
    private List<EventHandler<KeyEvent>> getKeyEventHandlers() {
        return this.keyEventHandlers;
    }

}
