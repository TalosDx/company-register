package ru.aisa.companyregister.utils;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import ru.aisa.companyregister.CompanyRegister;

import java.io.IOException;
import java.util.Properties;

public class LazyUtils
{


    /**
     * Пример запроса для JDBCTemplate
     * <p>
     * Ivan Sinelnikov, [24.04 .18 17:16]
     * final String SQL_INSERT_ERROR_MESSAGE = "INSERT INTO ttk.control_result_log "
     * + " (code_mo, cdoc, doc_year, doc_month, doc_type, id_control, error_message, date_control, is_actual) "
     * + "VALUES "
     * + " (:CODE_MO, :CDOC, :DOC_YEAR, :DOC_MONTH, :DOC_TYPE, :ID_CONTROL, :ERROR_MESSAGE, :DATE_CONTROL, :IS_ACTUAL)";
     * <p>
     * Ivan Sinelnikov, [24.04 .18 17:17]
     */

    public static NamedParameterJdbcTemplate getCrateNPJT()
    {
        //SimpleDriverDataSource

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(LazyUtils.getProperties("cratedb"));
        return new NamedParameterJdbcTemplate(ds);
    }


    public int addControlErrorMessage(String cdoc, String codeMO, int fo, int month, int year)
    {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        sqlParameterSource.addValue("CODE_MO", codeMO);
        sqlParameterSource.addValue("CDOC", cdoc);
        sqlParameterSource.addValue("DOC_MONTH", month);
        sqlParameterSource.addValue("DOC_YEAR", year);
        sqlParameterSource.addValue("DOC_TYPE", fo);
        sqlParameterSource.addValue("ID_CONTROL", "");
        sqlParameterSource.addValue("ERROR_MESSAGE", "");
        sqlParameterSource.addValue("DATE_CONTROL", "");
        sqlParameterSource.addValue("IS_ACTUAL", 1);
        //selectForObject
        try
        {
            //logger.debug(sqlParameterSource.getValues().toString());
            //logger.debug(SQL_INSERT_ERROR_MESSAGE);
            NamedParameterJdbcTemplate npjt;
            return 0;//npjt.update(SQL_INSERT_ERROR_MESSAGE, sqlParameterSource);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            //logger.error(null, exc);
            return -1;
        }
    }

    public static String getProperties(String property)
    {
        Properties prop = new Properties();
        try
        {
            prop.load(LazyUtils.class.getClassLoader().getResourceAsStream("properties.conf"));
            return prop.getProperty(property);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
