package ru.aisa.companyregister.entity;

import java.time.LocalDate;

public class Employee
{
    private int id;
    private String fullName, email, companyName;
    private  LocalDate birthday;

    public Employee(int id, String fullName, LocalDate birthday, String email , String companyName)
    {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
        this.email = email;
        this.companyName = companyName;
    }

    public Employee(String fullName, LocalDate birthday, String email , String companyName)
    {
        this.fullName = fullName;
        this.birthday = birthday;
        this.email = email;
        this.companyName = companyName;
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
        return "EmployeePopUpControllerImpl{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", companyName='" + companyName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
