package ex.talosdx.companyregister.dao.wrapper.enties;

import ex.talosdx.companyregister.dao.entities.Employee;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class EmployeePopUpControllerImplGenericDAOImplTest
{

    private final Employee employee = new Employee("Simon Ivanovich", LocalDate.of(1984, 2, 21), "ivan@mail.ru", 1);
    private final Employee employee1 = new Employee("Kamina Evckovich", LocalDate.of(1984, 2, 21), "ivan@mail.ru", 2);

    private final GenericDAO genericDAO = new EmployeeGenericDAOImpl();

    @Test
    public void create()
    {
        System.out.println(genericDAO.create(employee));
    }

    @Test
    public void read()
    {
        System.out.println(genericDAO.read(2));
    }

    @Test
    public void read1()
    {
        System.out.println(genericDAO.read(
                new String[] {"id"},
                new Object[] {2}
                ));
    }

    @Test
    public void readAll1()
    {
        System.out.print(genericDAO.readAll());
    }

    @Test
    public void updateById()
    {
        System.out.print(genericDAO.updateById(employee1, 2));
    }

    @Test
    public void delete()
    {
        System.out.print(genericDAO.delete(genericDAO.read(2)));
    }

    @Test
    public void deleteByID()
    {
        System.out.print(genericDAO.deleteByID(2));
    }

    @Test
    public void deleteWithoutID()
    {
        System.out.print(genericDAO.deleteWithoutID(employee));
    }

}