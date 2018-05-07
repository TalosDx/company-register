package ru.aisa.companyregister.dao.wrapper;

public interface AbstractTableCreator
{
    /**
     * Возвращает sql запрос
     * @param tableName - название таблицы
     * @param nameColumns -название колонок
     * @param typeColumns - типы колонок
     * @return - возвращает сформированный sql запрос в виде String
     */
    String getCreateRequest(String tableName, String[] nameColumns, String[] typeColumns, String uniqueColumn);

    void setIfNotExists(Boolean ifNonExists);

    boolean getIfNotExists();
}
