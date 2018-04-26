package ru.aisa.companyregister.database;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.aisa.companyregister.entity.Company;

import static ru.aisa.companyregister.database.DBConnector.sqlUpdate;

public class AddCompany
{
    private final String SQL_INSERT_COMPANY = "INSERT INTO companies "
            + " (companyName, inn, address, phone) "
            + "VALUES "
            + " (:COMPANY_NAME, :INN, :ADDRESS, :PHONE)";

    public int addCompany(String companyName, long inn , String address, String phone)
    {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        sqlParameterSource.addValue("COMPANY_NAME", companyName);
        sqlParameterSource.addValue("INN", inn);
        sqlParameterSource.addValue("ADDRESS", address);
        sqlParameterSource.addValue("PHONE", phone);
        return sqlUpdate(sqlParameterSource, SQL_INSERT_COMPANY);
    }
    public int addCompany(Company company)
    {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        sqlParameterSource.addValue("COMPANY_NAME", company.getCompanyName());
        sqlParameterSource.addValue("INN", company.getInn());
        sqlParameterSource.addValue("ADDRESS", company.getAddress());
        sqlParameterSource.addValue("PHONE", company.getPhone());
        return sqlUpdate(sqlParameterSource, SQL_INSERT_COMPANY);
    }
}
