package ru.aisa.companyregister.database.dao;

import org.junit.jupiter.api.Test;
import ru.aisa.companyregister.database.dao.entities.Company;

class CompanyGenericDAOImplTest
{

    Company company = new Company("OAO Bobrs and Cats", 51214, "г. Бобруйск, улица Бобров, д.6", "+7981-812-15-15");
    Company company1 = new Company("OAO Bobrs and Cats", 51214, "г. Бобруйск, улица Бобров, д.6", "+7981-812-15-15");

    GenericDAO genericDAO = new CompanyGenericDAOImpl();

    @Test
    void create()
    {
        System.out.println(genericDAO.create(company));
    }

    @Test
    void read()
    {
        System.out.println(genericDAO.read(2));
    }

    @Test
    void read1()
    {
        System.out.println(genericDAO.read(
                new String[] {"id"},
                new Object[] {2}
                ));
    }

    @Test
    void readAll1()
    {
        System.out.print(genericDAO.readAll());
    }

    @Test
    void updateById()
    {
        System.out.print(genericDAO.updateById(company1, 2));
    }

    @Test
    void delete()
    {
        System.out.print(genericDAO.delete(genericDAO.read(2)));
    }

    @Test
    void deleteByID()
    {
        System.out.print(genericDAO.deleteByID(3));
    }

    @Test
    void deleteWithoutID()
    {
        System.out.print(genericDAO.deleteWithoutID(company));
    }

    @Test
    void getTableColumns()
    {
    }

    @Test
    void getTableName()
    {
    }
}