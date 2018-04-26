package ru.aisa.companyregister.database.request;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.aisa.companyregister.database.DBConnector;

import java.util.List;

public class DefaultSQLSelector implements SQLSelect
{

    @Override
    public String getSqlRequest(String table, String[] nameColumns)
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
    public String getSqlRequestById(String table, String[] nameColumns, int id)
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
    public <T extends RowMapper> List<T> SelectAll(String table, String[] nameColumns, T mapper)
    {
        return DBConnector.sqlExecute(getSqlRequest(table, nameColumns), mapper);
    }

    @Override
    public <T extends RowMapper> Object SelectByID(String table, String[] nameColumns, int id, T mapper)
    {
        return DBConnector.sqlExecuteForObject(getSqlRequestById(table, nameColumns, id), new MapSqlParameterSource("id", id), mapper);
    }
}
