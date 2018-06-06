package ru.aisa.companyregister.dao.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Employee implements Serializable
{
    private int id;
    private String fullName, email;
    private LocalDate birthday;
    private int companyId;

    public Employee(int id, String fullName, LocalDate birthday, String email , int companyId)
    {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
        this.email = email;
        this.companyId = companyId;
    }

    public Employee(String fullName, LocalDate birthday, String email , int companyId)
    {
        this.fullName = fullName;
        this.birthday = birthday;
        this.email = email;
        this.companyId = companyId;
    }

    public void setId(int id)
    {
        this.id = id;
    }


    public int getId()
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

    public int getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(int companyId)
    {
        this.companyId = companyId;
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
        return "EmployeePopUpControllerImpl{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", companyId='" + companyId + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
