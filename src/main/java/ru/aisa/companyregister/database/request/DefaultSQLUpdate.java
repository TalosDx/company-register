package ru.aisa.companyregister.database.request;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import static ru.aisa.companyregister.database.DBConnector.sqlUpdate;

public class DefaultSQLUpdate implements SQLUpdate
{
    //private final String SQL_INSERT = "INSERT INTO :TABLE "
    // + " (companyName, inn, address, phone) "
    // + "VALUES "
    // + " (:COMPANY_NAME, :INN, :ADDRESS, :PHONE)";
    final String UPDATE_QUERY = "update employee " +
            "set company_name = :company_name, inn = :inn, address = :address, phone = :phone " +
            "where id = :id";

    @Override
    public String getSqlRequest(String table, String[] nameColumns, String[] nameVariables, String[] conditionColumn, String[] conditionVariable)
    {
        int size;
        if ((nameColumns.length == nameVariables.length)) size = nameVariables.length;
        else throw new IllegalArgumentException("Length of nameColumns and nameVariables not equal");
        int sizeCondition;
        if(conditionColumn.length == conditionVariable.length)sizeCondition = conditionColumn.length;
        else throw new IllegalArgumentException("Length of conditionColumn and conditionVariable not equal");
        String SQL_UPDATE = "UPDATE " + table + " SET ";
        SQL_UPDATE += getPartStr(nameColumns, nameVariables, size);
        SQL_UPDATE += "where" + " ";
        SQL_UPDATE += getPartStr(conditionColumn, conditionVariable, sizeCondition);

        return SQL_UPDATE;
    }

    private static String getPartStr(String[] nameColumns, String[] nameVariables, int size)
    {
        String sqlPart="";
        String str;
        for(int i = 0; i<size; i++)
        {
            str = i == size-1 ? " " : ", ";
            sqlPart +=  nameColumns[i] + " = " + ":" + nameVariables[i] + str;
        }
        return sqlPart;
    }


    @Override
    public int Update(String table, String[] nameColumns, String[] nameVariables, Object[] objects, String[] conditionColumn, String[] conditionVariable, Object[] objectCondition)
    {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        int size;
        if (nameColumns.length == objects.length) size = objects.length;
        else throw new IllegalArgumentException("Length of nameColumns and objects not equal");

        for(int i=0; i<size; i++)
        {
            sqlParameterSource.addValue(nameColumns[i], objects[i]);

        }

        int sizeCondition;
        if(conditionColumn.length == conditionVariable.length)sizeCondition = conditionColumn.length;
        else throw new IllegalArgumentException("Length of conditionColumn and conditionVariable not equal");

        for(int i=0; i<sizeCondition; i++)
        {
            sqlParameterSource.addValue(conditionColumn[i], objectCondition[i]);

        }
        return sqlUpdate(sqlParameterSource, getSqlRequest(table, nameColumns, nameVariables, conditionColumn, conditionVariable));
    }
}
