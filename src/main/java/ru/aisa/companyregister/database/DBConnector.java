package ru.aisa.companyregister.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.aisa.companyregister.utils.LazyUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class DBConnector
{
    public static final String COMPANIES_TABLE = "companies";
    public static final String EMPLOYEE_TABLE = "employee";
    private static Connection connection;
    private static Statement statement;
    private static String url, username, password;
    private static ITableCreator tableCreator = new DefaultTableCreator();
    private static final String createCompany = tableCreator.createTable(COMPANIES_TABLE, new String[]{"id", "COMPANY_NAME", "INN", "ADDRESS", "PHONE"}, new String[]{"serial primary key", "char(120)", "integer", "char(250)", "char(16)"});
    private static final String createEmployee = tableCreator.createTable(EMPLOYEE_TABLE, new String[]{"id", "FULL_NAME", "BIRTHDAY", "EMAIL", "COMPANY_NAME"}, new String[]{"serial primary key", "char(40)", "date", "char(30)", "char(120)"});


    public static Statement createTable()
    {
        url = "jdbc:postgresql://" + LazyUtils.getProperties("host") + ":" + LazyUtils.getProperties("port") + "/" + LazyUtils.getProperties("database");
        username = LazyUtils.getProperties("username");
        password = LazyUtils.getProperties("password");
        try
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established");
            statement = connection.createStatement();
            if(selectTable(COMPANIES_TABLE) == null)
            {
                System.out.println(createCompany);
                statement.executeUpdate(createCompany);
            }
            if(selectTable(EMPLOYEE_TABLE) == null)
            {
                System.out.println(createEmployee);
                statement.executeUpdate(createEmployee);
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try
            {
                statement.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return statement;
    }

    public static Connection getConnection()
    {
        return connection;
    }

    public static Statement getStatement()
    {
        return statement;
    }

    public ITableCreator getTableCreator()
    {
        return tableCreator;
    }

    public void setTableCreator(ITableCreator tableCreator)
    {
        this.tableCreator = tableCreator;
    }

    public static NamedParameterJdbcTemplate getCrateNPJT()
    {
        url = "jdbc:postgresql://" + LazyUtils.getProperties("host") + ":" + LazyUtils.getProperties("port") + "/" + LazyUtils.getProperties("database");
        username = LazyUtils.getProperties("username");
        password = LazyUtils.getProperties("password");
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return new NamedParameterJdbcTemplate(ds);
    }

    public static ResultSet selectTable(String tableName)
    {
        if (statement == null)
            throw new IllegalArgumentException("selectTable: global variable 'statement' is NULL");
        try
        {
            return statement.executeQuery("SELECT * FROM " + tableName);
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public static <T extends RowMapper> List<T> sqlExecute(String sql_insert_employee, RowMapper<T> mapper)
    {
        try
        {
            NamedParameterJdbcTemplate jdbcTemplate = getCrateNPJT();
            return jdbcTemplate.query(sql_insert_employee, (SqlParameterSource) null, mapper);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            return null;
        }
    }

    public static <T extends RowMapper> Object sqlExecuteForObject(String sql_insert_employee, MapSqlParameterSource mapSqlParameterSource, RowMapper<T> mapper)
    {
        try
        {
            NamedParameterJdbcTemplate jdbcTemplate = getCrateNPJT();
            return jdbcTemplate.queryForObject(sql_insert_employee, mapSqlParameterSource, mapper);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            return null;
        }
    }

    public static int sqlUpdate(MapSqlParameterSource sqlParameterSource, String sql_insert_employee)
    {
        try
        {
            NamedParameterJdbcTemplate jdbcTemplate = getCrateNPJT();
            return jdbcTemplate.update(sql_insert_employee, sqlParameterSource);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            return -1;
        }
    }

}
