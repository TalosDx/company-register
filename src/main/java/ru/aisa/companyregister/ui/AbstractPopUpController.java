package ru.aisa.companyregister.ui;

import com.vaadin.ui.Layout;

public interface AbstractPopUpController<T>
{
    /**
     * Обновляет данные в полях
     */
    void updateItemData();

    void init(Layout layout);
    void displayAddItem(Layout layout);
    void displayEditItem(Layout layout, T item);
    void displayDeleteItem(Layout layout, T item);
}
