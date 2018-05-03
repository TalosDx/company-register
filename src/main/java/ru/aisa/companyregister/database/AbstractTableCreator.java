package ru.aisa.companyregister.database;

interface AbstractTableCreator
{
    /**
     * Возвращает sql запрос
     * @param tableName - название таблицы
     * @param nameColumns -название колонок
     * @param typeColumns - типы колонок
     * @return - возвращает сформированный sql запрос в виде String
     */
    String getCreateRequest(String tableName, String[] nameColumns, String[] typeColumns);

    void setIfNotExists(Boolean ifNonExists);

    boolean getIfNotExists();
}
