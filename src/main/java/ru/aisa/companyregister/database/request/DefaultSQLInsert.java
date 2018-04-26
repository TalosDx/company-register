package ru.aisa.companyregister.database.request;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import static ru.aisa.companyregister.database.DBConnector.sqlUpdate;

public class DefaultSQLInsert implements SQLInsert
{
    //private final String SQL_INSERT = "INSERT INTO :TABLE "
    // + " (companyName, inn, address, phone) "
    // + "VALUES "
    // + " (:COMPANY_NAME, :INN, :ADDRESS, :PHONE)";

    @Override
    public String getInsertRequest(String table, String[] nameColumns, String[] nameVariables)
    {
        int size=0;
        if (nameColumns.length == nameVariables.length) size = nameVariables.length;
        else throw new IllegalArgumentException("Length of nameColumns and objects not equal");
        String SQL_INSERT = "INSERT INTO " + table + " (";
        SQL_INSERT += getPartStr(nameColumns, size, false);
        SQL_INSERT += " VALUES" + " (";
        SQL_INSERT += getPartStr(nameVariables, size, true);

        return SQL_INSERT;
    }

    private static String getPartStr(String[] nameVariables, int size, boolean isVariable)
    {
        String sqlPart="";
        String str;
        for(int i = 0; i<size; i++)
        {
            str = i == size-1 ? ")" : ", ";
            if (isVariable) sqlPart += ":";
            sqlPart +=  nameVariables[i] + str;
        }
        return sqlPart;
    }


    @Override
    public int insert(String table, String[] nameColumns, String[] nameVariables, Object[] objects)
    {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        int size=0;
        if (nameColumns.length == objects.length) size = objects.length;
        else throw new IllegalArgumentException("Length of nameColumns and objects not equal");

        for(int i=0; i<size; i++)
        {
            sqlParameterSource.addValue(nameColumns[i], objects[i]);

        }
        return sqlUpdate(sqlParameterSource, getInsertRequest(table, nameColumns, nameVariables));
    }
}
