package ru.aisa.companyregister.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.aisa.companyregister.dao.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapperImpl implements RowMapper<User>
{
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        return new User(
                rs.getInt("id"),
                rs.getString("username").replaceAll("[\\s]{2,}", ""),
                rs.getString("password").replaceAll("[\\s]{2,}", ""));
    }
}
