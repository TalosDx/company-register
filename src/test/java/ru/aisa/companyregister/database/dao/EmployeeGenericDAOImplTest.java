package ru.aisa.companyregister.database.dao;

import org.junit.jupiter.api.Test;
import ru.aisa.companyregister.entity.Employee;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeGenericDAOImplTest
{

    Employee employee = new Employee("Ivan Ivanovich", LocalDate.of(1984, 02, 21), "ivan@mail.ru", "OAO MetaPhone");
    Employee employee1 = new Employee("Ivan Evckovich", LocalDate.of(1984, 02, 21), "ivan@mail.ru", "OAO MetaPhone");

    GenericDAO genericDAO = new EmployeeGenericDAOImpl();

    @Test
    void create()
    {
        System.out.println(genericDAO.create(employee));
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
        System.out.print(genericDAO.updateById(employee1, 2));
    }

    @Test
    void delete()
    {
        System.out.print(genericDAO.delete(genericDAO.read(2)));
    }

    @Test
    void deleteByID()
    {
        System.out.print(genericDAO.deleteByID(2));
    }

    @Test
    void deleteWithoutID()
    {
        System.out.print(genericDAO.deleteWithoutID(employee));
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