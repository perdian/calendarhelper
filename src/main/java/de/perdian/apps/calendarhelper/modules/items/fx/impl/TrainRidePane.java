package de.perdian.apps.calendarhelper.modules.items.fx.impl;

import de.perdian.apps.calendarhelper.modules.items.model.impl.TrainRideItem;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TrainRidePane extends GridPane {

    TrainRidePane(TrainRideItem trainRideItem) {
        this.add(new Label("HELLO!"), 0, 0);
    }

}
