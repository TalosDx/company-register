package ex.talosdx.companyregister.dao;

import ex.talosdx.companyregister.dao.wrapper.AbstractTableCreator;
import ex.talosdx.companyregister.dao.wrapper.TableCreatorImpl;
import ex.talosdx.companyregister.utils.LazyUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.*;
import java.util.List;

public class DBConnector
{
    private static final String COMPANIES_TABLE = "companies";
    private static final String USERS_TABLE = "users";
    private static final String EMPLOYEE_TABLE = "employee";
    private static Connection connection;
    private static Statement statement;
    private static final AbstractTableCreator tableCreator = new TableCreatorImpl();

    final static String createCompany = tableCreator.getCreateRequest(COMPANIES_TABLE,
            new String[]{"id", "COMPANY_NAME", "INN", "ADDRESS", "PHONE"},
            new String[]{"serial primary key", "char(120)", "integer", "char(250)", "char(15)"}, null);

    final static String createEmployee = tableCreator.getCreateRequest(EMPLOYEE_TABLE,
            new String[]{"id", "FULL_NAME", "BIRTHDAY", "EMAIL", "COMPANY_ID"},
            new String[]{"serial primary key", "char(40)", "date", "char(30)", " INTEGER NOT NULL REFERENCES " + COMPANIES_TABLE + " ON DELETE CASCADE ON UPDATE CASCADE"}, null);

    final static String createUsers = tableCreator.getCreateRequest(USERS_TABLE,
            new String[]{"id", "username", "password"},
            new String[]{"serial primary key", "varchar(255)", "varchar(255)"}, "username");

    public static void createTable()
    {
        String url = "jdbc:" + LazyUtils.getProperties("host") + ":" + LazyUtils.getProperties("port") + "/" + LazyUtils.getProperties("database");
        String username = LazyUtils.getProperties("username");
        String password = LazyUtils.getProperties("password");
        try
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established");
            statement = connection.createStatement();
            tableCreator.setIfNotExists(true);
                System.out.println(createCompany);
                statement.executeUpdate(createCompany);
                System.out.println(createEmployee);
                statement.executeUpdate(createEmployee);
                System.out.print(createUsers);
                statement.executeUpdate(createUsers);
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
    }

    private static NamedParameterJdbcTemplate getCrateNPJT()
    {
        String url = "jdbc:" + LazyUtils.getProperties("host") + ":" + LazyUtils.getProperties("port") + "/" + LazyUtils.getProperties("database");
        String username = LazyUtils.getProperties("username");
        String password = LazyUtils.getProperties("password");
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return new NamedParameterJdbcTemplate(ds);
    }

    public static <T extends RowMapper> List<T> sqlExecute(String sql_insert_employee, MapSqlParameterSource mapSqlParameterSource, RowMapper<T> mapper)
    {
        try
        {
            NamedParameterJdbcTemplate jdbcTemplate = getCrateNPJT();
            return jdbcTemplate.query(sql_insert_employee, mapSqlParameterSource, mapper);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            return null;
        }
    }


    public static int sqlExecuteForInt(String sql_insert_employee, MapSqlParameterSource sqlParameterSource)
    {
        try
        {
            NamedParameterJdbcTemplate jdbcTemplate = getCrateNPJT();
            return jdbcTemplate.queryForInt(sql_insert_employee, sqlParameterSource);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            return 0;
        }
    }

    public static int sqlExecuteForInt(String sql_insert_employee)
    {
        try
        {
            NamedParameterJdbcTemplate jdbcTemplate = getCrateNPJT();
            return jdbcTemplate.queryForInt(sql_insert_employee, (SqlParameterSource) null);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            return 0;
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
