package ru.aisa.companyregister.ui;

import com.vaadin.ui.Table;

public abstract class AbstractWindowConfirmation extends WindowController
{
    //Проверяет является ли сейчас окно подтверждения -> окном подтверждения операции отмены -> вызывается если хоть в одном поле есть данные
    public abstract boolean isCancelOperation();

    public abstract void setCancelOperation(Boolean cancel);

    //Отображает изменные данные по колонках в окне подтверждения
    public abstract boolean setShowData(Table table);
}
