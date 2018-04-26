package ru.aisa.companyregister.entity;

public class Company
{
    private long id;
    private String companyName;
    private long inn;
    private String address, phone;

    public Company(long id, String companyName, long inn , String address, String phone)
    {
        this.id = id;
        this.companyName = companyName;
        this.inn = inn;
        this.address = address;
        this.phone = phone;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public long getInn()
    {
        return inn;
    }

    public void setInn(long inn)
    {
        this.inn = inn;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Override
    public String toString()
    {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", inn=" + inn +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
