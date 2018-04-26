package ru.aisa.companyregister.database.request;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public interface SQLSelect
{
    String getSqlRequest(String table, String[] nameColumns);
    String getSqlRequestById(String table, String[] nameColumns, int id);


    <T extends RowMapper> List<T> SelectAll(String table, String[] nameColumns, T mapper);
    <T extends RowMapper> Object SelectByID(String table, String[] nameColumns, int id, T mapper);

}
