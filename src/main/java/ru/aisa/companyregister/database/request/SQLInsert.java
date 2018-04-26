package ru.aisa.companyregister.database.request;

public interface SQLInsert
{
    String getSqlRequest(String table, String[] nameColumns, String[] nameVariables);

    int Insert(String table, String[] nameColumns, String[] nameVariables, Object[] objects);
}
