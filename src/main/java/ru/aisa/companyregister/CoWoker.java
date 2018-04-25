package ru.aisa.companyregister;

import java.time.LocalDate;

public class CoWoker
{
    private long id;
    private String fullName, email, companyName;
    private  LocalDate birthday;

    public CoWoker(long id, String fullName, LocalDate birthday, String email , String companyName)
    {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
        this.email = email;
        this.companyName = companyName;
    }

    public long getId()
    {
        return id;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public LocalDate getBirthday()
    {
        return birthday;
    }

    public void setBirthday(LocalDate birthday)
    {
        this.birthday = birthday;
    }

    @Override
    public String toString()
    {
        return "CoWoker{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", companyName='" + companyName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
