package de.perdian.apps.calendarbuddy.modules.items;

import org.kordamp.ikonli.Ikon;

import java.util.function.Supplier;

public class ItemType<T extends Item> {

    private Class<T> itemClass = null;
    private Supplier<T> newItemSupplier = null;
    private ItemPaneFactory<T> itemPaneFactory = null;
    private String title = null;
    private Ikon icon = null;

    public ItemType(Class<T> itemClass) {
        this.setItemClass(itemClass);
        this.setNewItemSupplier(() -> {
            try {
                return itemClass.getConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("No default constructor found for item class " + itemClass.getName(), e);
            } catch (ReflectiveOperationException e) {
                throw new IllegalArgumentException("Cannot instantiate item class " + itemClass.getName(), e);
            }
        });
    }

    public Class<T> getItemClass() {
        return this.itemClass;
    }
    private void setItemClass(Class<T> itemClass) {
        this.itemClass = itemClass;
    }

    public ItemType<T> withNewItemSupplier(Supplier<T> newItemSupplier) {
        this.setNewItemSupplier(newItemSupplier);
        return this;
    }
    public Supplier<T> getNewItemSupplier() {
        return this.newItemSupplier;
    }
    public void setNewItemSupplier(Supplier<T> newItemSupplier) {
        this.newItemSupplier = newItemSupplier;
    }

    public ItemType<T> withItemPaneFactory(ItemPaneFactory<T> itemPaneFactory) {
        this.setItemPaneFactory(itemPaneFactory);
        return this;
    }
    public ItemPaneFactory<T> getItemPaneFactory() {
        return this.itemPaneFactory;
    }
    public void setItemPaneFactory(ItemPaneFactory<T> itemPaneFactory) {
        this.itemPaneFactory = itemPaneFactory;
    }

    public ItemType<T> withTitle(String title) {
        this.setTitle(title);
        return this;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public ItemType<T> withIcon(Ikon icon) {
        this.setIcon(icon);
        return this;
    }
    public Ikon getIcon() {
        return this.icon;
    }
    public void setIcon(Ikon icon) {
        this.icon = icon;
    }

}
