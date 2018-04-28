package ru.aisa.companyregister.ui;

import com.vaadin.data.Validator;

public interface ColumnsValidatorMapper
{

    /**
     * Получаем имя таблицы
     */
    String getTableName();

    /**
     * Получаем валидатор по имени колонки
     * @param columnName - имя колонки
     */
    Validator getValidatorFromColumn(String columnName);

}
