package ru.aisa.companyregister.database.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.aisa.companyregister.entity.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultCompanyMapper implements RowMapper<Company>
{
    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        return new Company(rs.getInt("id"), rs.getString("company_name").replaceAll("[\\s]{2,}", ""), rs.getInt("inn"), rs.getString("address").replaceAll("[\\s]{2,}", ""), rs.getString("phone").replaceAll("[\\s]{2,}", ""));
    }
}
