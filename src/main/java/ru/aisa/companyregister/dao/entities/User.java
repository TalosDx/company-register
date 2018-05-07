package ru.aisa.companyregister.dao.entities;

import java.io.Serializable;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class User implements Serializable
{
    private int id;
    private String username, password;

    public User(int id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }


    public User(String username, String password)
    {
        this.username = username;
        this.password = md5Hex(password);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
