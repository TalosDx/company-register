package ru.aisa.companyregister.database.request;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public interface SQLSelect
{
    String getSelectRequest(String table, String[] nameColumns);
    String getSelectRequestById(String table, String[] nameColumns, int id);


    <T extends RowMapper> List<T> selectAllColumns(String table, String[] nameColumns, T mapper);
    <T extends RowMapper> Object selectColumnsById(String table, String[] nameColumns, int id, T mapper);

}
