package de.perdian.apps.calendarhelper.modules.items.support;

import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemTemplate;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.util.List;

public abstract class AbstractParentTemplate<P extends AbstractParentItem<C>, C extends Item> extends ItemTemplate<P> {

    public AbstractParentTemplate(String title, Ikon icon) {
        super(title, icon);
    }

    protected abstract C createChildItem(P parentItem);

    @Override
    public List<Button> createAdditionalButtons(P item) {
        Button newChildButton = new Button("", new FontIcon(MaterialDesignP.PLUS));
        newChildButton.setTooltip(new Tooltip("New child"));
        newChildButton.setOnAction(event -> Platform.runLater(() -> item.getChildren().add(this.createChildItem(item))));
        newChildButton.setFocusTraversable(false);
        return List.of(newChildButton);
    }

}
