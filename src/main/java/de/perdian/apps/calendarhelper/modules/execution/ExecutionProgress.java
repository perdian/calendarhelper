package de.perdian.apps.calendarhelper.modules.execution;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ExecutionProgress {

    private final BooleanProperty busy = new SimpleBooleanProperty();
    private final DoubleProperty progress = new SimpleDoubleProperty();

    public BooleanProperty busyProperty() {
        return this.busy;
    }

    public DoubleProperty progressProperty() {
        return this.progress;
    }

}
