package ru.aisa.companyregister.database.request;

public interface SQLUpdate
{
    String getSqlRequest(String table, String[] nameColumns, String[] nameVariables, String[] conditionColumn, String[] conditionVariable);

    int Update(String table, String[] nameColumns, String[] nameVariables, Object[] objects, String[] conditionColumn, String[] conditionVariable, Object[] objectCondition);

}
