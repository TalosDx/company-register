package ru.aisa.companyregister.database.request;

public interface SQLInsert
{
    String getInsertRequest(String table, String[] nameColumns, String[] nameVariables);

    int insert(String table, String[] nameColumns, String[] nameVariables, Object[] objects);
}
