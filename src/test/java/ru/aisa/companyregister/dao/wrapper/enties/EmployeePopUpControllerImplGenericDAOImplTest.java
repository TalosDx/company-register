package ru.aisa.companyregister.dao.wrapper.enties;

import org.junit.jupiter.api.Test;
import ru.aisa.companyregister.dao.entities.Employee;

import java.time.LocalDate;

class EmployeePopUpControllerImplGenericDAOImplTest
{

    private final Employee employee = new Employee("Simon Ivanovich", LocalDate.of(1984, 2, 21), "ivan@mail.ru", 1);
    private final Employee employee1 = new Employee("Kamina Evckovich", LocalDate.of(1984, 2, 21), "ivan@mail.ru", 2);

    private final GenericDAO genericDAO = new EmployeeGenericDAOImpl();

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

}