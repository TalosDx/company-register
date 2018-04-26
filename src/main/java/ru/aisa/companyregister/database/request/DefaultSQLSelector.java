package ru.aisa.companyregister.database.request;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.aisa.companyregister.database.DBConnector;

import java.util.List;

public class DefaultSQLSelector implements SQLSelect
{

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
    public String getSelectRequestById(String table, String[] nameColumns, int id)
    {
        String sql = "SELECT ";
        String str;
        for (int i=0; i<nameColumns.length; i++)
        {
            str = i == nameColumns.length-1 ? " from " + table + " where " + id: ", ";
            sql += nameColumns[i] + str;
        }
        return sql;
    }

    @Override
    public <T extends RowMapper> List<T> selectAllColumns(String table, String[] nameColumns, T mapper)
    {
        return DBConnector.sqlExecute(getSelectRequest(table, nameColumns), mapper);
    }

    @Override
    public <T extends RowMapper> Object selectColumnsById(String table, String[] nameColumns, int id, T mapper)
    {
        return DBConnector.sqlExecuteForObject(getSelectRequestById(table, nameColumns, id), new MapSqlParameterSource("id", id), mapper);
    }
}
