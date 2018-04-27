package ru.aisa.companyregister.database.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.aisa.companyregister.database.DBConnector;

import java.util.List;

import static ru.aisa.companyregister.database.DBConnector.sqlExecute;
import static ru.aisa.companyregister.database.DBConnector.sqlExecuteForInt;
import static ru.aisa.companyregister.database.DBConnector.sqlUpdate;

public class TableDAOImpl implements AbstractTableDAO
{
    @Override
    public String getInsertRequest(String table, String[] nameColumns, String[] nameVariables)
    {
        int size=0;
        if (nameColumns.length == nameVariables.length) size = nameVariables.length;
        else throw new IllegalArgumentException("Length of nameColumns and objects not equal");
        String SQL_INSERT = "INSERT INTO " + table + " (";
        SQL_INSERT += getInsertPartStr(nameColumns, size, false);
        SQL_INSERT += " VALUES" + " (";
        SQL_INSERT += getInsertPartStr(nameVariables, size, true);

        return SQL_INSERT;    }

    private static String getInsertPartStr(String[] nameVariables, int size, boolean isVariable)
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
    public int insertTable(String tableName, String[] nameColumns, String[] nameVariables, Object[] objects)
    {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        int size=0;
        if (nameColumns.length == objects.length) size = objects.length;
        else throw new IllegalArgumentException("Length of nameColumns and objects not equal");

        for(int i=0; i<size; i++)
        {
            sqlParameterSource.addValue(nameColumns[i], objects[i]);

        }
        return sqlUpdate(sqlParameterSource, getInsertRequest(tableName, nameColumns, nameVariables));    }

    @Override
    public String getSelectRequest(String table, String[] nameColumns)
    {
        String sql = "SELECT ";
        String str;
        for (int i=0; i<nameColumns.length; i++)
        {
            str = i == nameColumns.length-1 ? " from " + table : ", ";
            sql += nameColumns[i] + str;
        }
        return sql;
    }

    @Override
    public String getSelectRequestById(String tableName, String[] nameColumns)
    {
        String sql = "SELECT ";
        String str;
        for (int i=0; i<nameColumns.length; i++)
        {
            str = i == nameColumns.length-1 ? " from " + tableName + " where id = :id" : ", ";
            sql += nameColumns[i] + str;
        }
        return sql;
    }

    @Override
    public String getSelectRequestByCondition(String tableName, String[] nameColumns, String[] conditionColumns, String[] conditionVariables)
    {
        int sizeCondition;
        if(conditionColumns.length == conditionVariables.length)sizeCondition = conditionColumns.length;
        else throw new IllegalArgumentException("Length of conditionColumn and conditionVariable not equal");
        String sql = "SELECT ";
        String str;
        for (int i=0; i<nameColumns.length; i++)
        {
            str = i == nameColumns.length-1 ? " from " + tableName + " where " : ", ";
            sql += nameColumns[i] + str;
        }
        for(int i=0; i<sizeCondition; i++)
        {
            sql += getSelectPartStr(conditionColumns, conditionVariables, sizeCondition);
        }
        return sql;
    }

    private static String getSelectPartStr(String[] conditionColumns, String[] conditionVariables, int size)
    {
        String sqlPart="";
        String str;
        for(int i = 0; i<size; i++)
        {
            str = i == size-1 ? " " : ", ";
            sqlPart +=  conditionColumns[i] + " = " + ":" + conditionVariables[i] + str;
        }
        return sqlPart;
    }

    @Override
    public <T extends RowMapper> List selectAllColumns(String table, String[] nameColumns, T mapper)
    {
        return DBConnector.sqlExecute(getSelectRequest(table, nameColumns), mapper);
    }

    @Override
    public <T extends RowMapper> Object selectColumnsById(String tableName, String[] nameColumns, int id, T mapper)
    {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        return DBConnector.sqlExecuteForObject(getSelectRequestById(tableName, nameColumns), new MapSqlParameterSource("id", id), mapper);
    }


    @Override
    public <T extends RowMapper> List selectColumnsByCondition(String tableName, String[] nameColumns, String[] conditionColumns, String[] conditionVariables, Object[] conditionObjects, T mapper)
    {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        int sizeCondition;
        if(conditionColumns.length == conditionVariables.length && conditionVariables.length == conditionObjects.length)sizeCondition = conditionVariables.length;
        else throw new IllegalArgumentException("Length of conditionColumn and conditionVariable not equal");

        for(int i=0; i<sizeCondition; i++)
        {
            parameterSource.addValue(conditionColumns[i], conditionObjects[i]);

        }
        return DBConnector.sqlExecute(getSelectRequestByCondition(tableName, nameColumns, conditionColumns, conditionVariables), parameterSource, mapper);
    }

    @Override
    public String getUpdateRequest(String tableName, String[] nameColumns, String[] nameVariables, String[] conditionColumn, String[] conditionVariable)
    {
        int size;
        if ((nameColumns.length == nameVariables.length)) size = nameVariables.length;
        else throw new IllegalArgumentException("Length of nameColumns and nameVariables not equal");
        int sizeCondition;
        if(conditionColumn.length == conditionVariable.length)sizeCondition = conditionColumn.length;
        else throw new IllegalArgumentException("Length of conditionColumn and conditionVariable not equal");
        String SQL_UPDATE = "UPDATE " + tableName + " SET ";
        SQL_UPDATE += getUpdatePartStr(nameColumns, nameVariables, size);
        SQL_UPDATE += "where" + " ";
        SQL_UPDATE += getUpdatePartStr(conditionColumn, conditionVariable, sizeCondition);

        return SQL_UPDATE;
    }

    private static String getUpdatePartStr(String[] nameColumns, String[] nameVariables, int size)
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
    public int updateTable(String tableName, String[] nameColumns, String[] nameVariables, Object[] objects, String[] conditionColumn, String[] conditionVariable, Object[] objectCondition)
    {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        int size;
        if (nameColumns.length == objects.length) size = objects.length;
        else throw new IllegalArgumentException("Length of nameColumns and objects not equal nameColumns.length: " + nameColumns.length + " objects.length: " + objects.length);

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
        return sqlUpdate(sqlParameterSource, getUpdateRequest(tableName, nameColumns, nameVariables, conditionColumn, conditionVariable));
    }

    @Override
    public String getDeleteRequest(String tableName, String[] conditionColumns, String[] conditionVariables)
    {
        int sizeCondition;
        if(conditionColumns.length == conditionVariables.length)sizeCondition = conditionColumns.length;
        else throw new IllegalArgumentException("Length of conditionColumn and conditionVariable not equal");
        String SQL_UPDATE = "DELETE FROM " + tableName;
        SQL_UPDATE += " WHERE ";
        SQL_UPDATE += getDeletePartStr(conditionColumns, conditionVariables, sizeCondition);

        return SQL_UPDATE;
    }

    private static String getDeletePartStr(String[] conditionColumns, String[] conditionVariables, int size)
    {
        String sqlPart="";
        String str;
        for(int i = 0; i<size; i++)
        {
            str = i == size-1 ? "" : " AND ";
            sqlPart +=  conditionColumns[i] + " = " + ":" + conditionVariables[i] + str;
        }
        return sqlPart;
    }

    @Override
    public int deleteFromTable(String tableName, String[] conditionColumns, String[] conditionVariables, Object[] conditionObjects)
    {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        int sizeCondition;
        if(conditionColumns.length == conditionVariables.length)sizeCondition = conditionColumns.length;
        else throw new IllegalArgumentException("Length of conditionColumn and conditionVariable not equal");

        for(int i=0; i<sizeCondition; i++)
        {
            sqlParameterSource.addValue(conditionColumns[i], conditionObjects[i]);

        }
        return sqlUpdate(sqlParameterSource, getDeleteRequest(tableName, conditionColumns, conditionVariables));    }

    @Override
    public int getCountFromTable(String tableName)
    {
        return sqlExecuteForInt("SELECT COUNT(*) " + "FROM " + tableName);
    }
}
