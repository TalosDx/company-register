package ru.aisa.companyregister.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.aisa.companyregister.dao.entities.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapperImpl implements RowMapper<Employee>
{
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        return new Employee(rs.getInt("id"), rs.getString("full_name").replaceAll("[\\s]{2,}", ""), rs.getDate("birthday").toLocalDate(), rs.getString("email").replaceAll("[\\s]{2,}", ""), rs.getInt("company_id"));
    }
}
