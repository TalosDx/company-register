package ru.aisa.companyregister.database;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.aisa.companyregister.entity.Coworker;

import java.time.LocalDate;

import static ru.aisa.companyregister.database.DBConnector.sqlUpdate;

public class AddCoworker
{
    private final String SQL_INSERT_COWORKER = "INSERT INTO coworkers "
            + " (fullName, birthday, email, companyName) "
            + "VALUES "
            + " (:COMPANY_NAME, :INN, :ADDRESS, :PHONE)";

    public int addCompany(String fullName, LocalDate birthday , String email, String companyName)
    {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        sqlParameterSource.addValue("FULL_NAME", fullName);
        sqlParameterSource.addValue("BIRTHDAY", birthday);
        sqlParameterSource.addValue("EMAIL", email);
        sqlParameterSource.addValue("COMPANY_NAME", companyName);
        return sqlUpdate(sqlParameterSource, SQL_INSERT_COWORKER);
    }

    public int addCompany(Coworker coworker)
    {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        sqlParameterSource.addValue("FULL_NAME", coworker.getFullName());
        sqlParameterSource.addValue("BIRTHDAY", coworker.getBirthday());
        sqlParameterSource.addValue("EMAIL", coworker.getEmail());
        sqlParameterSource.addValue("COMPANY_NAME", coworker.getCompanyName());
        return sqlUpdate(sqlParameterSource, SQL_INSERT_COWORKER);
    }

}
