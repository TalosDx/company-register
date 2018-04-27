package ru.aisa.companyregister.database;

import java.util.Objects;

public interface AbstractTableCreator
{
    /**
     * Возвращает sql запрос
     * @param tableName - название таблицы
     * @param nameColumns -название колонок
     * @param typeColumns - типы колонок
     * @return
     */
    String getCreateRequest(String tableName, String[] nameColumns, String[] typeColumns);

    void setIfNotExists(Boolean ifNonExists);

    boolean getIfNotExists();
}
