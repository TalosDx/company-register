package ex.talosdx.companyregister.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ex.talosdx.companyregister.dao.entities.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyMapperImpl implements RowMapper<Company>
{
    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        return new Company(rs.getInt("id"), rs.getString("company_name").replaceAll("[\\s]{2,}", ""), rs.getInt("inn"), rs.getString("address").replaceAll("[\\s]{2,}", ""), rs.getString("phone").replaceAll("[\\s]{2,}", ""));
    }
}
