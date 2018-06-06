package ex.talosdx.companyregister.dao.wrapper.enties;

import ex.talosdx.companyregister.dao.entities.Company;
import org.junit.Test;

public class CompanyGenericDAOImplTest
{

    private final Company company = new Company("OAO Beavers and Cats", 51214, "г. Бобруйск, улица Бобров, д.6", "+7981-812-15-15");
    private final Company company1 = new Company("OAO Beavers and Cats", 51214, "г. Бобруйск, улица Бобров, д.6", "+7981-812-15-15");

    private final GenericDAO genericDAO = new CompanyGenericDAOImpl();

    @Test
    public void create()
    {
        System.out.println(genericDAO.create(company));
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
        System.out.print(genericDAO.updateById(company1, 2));
    }

    @Test
    public void delete()
    {
        System.out.print(genericDAO.delete(genericDAO.read(2)));
    }

    @Test
    public void deleteByID()
    {
        System.out.print(genericDAO.deleteByID(3));
    }

    @Test
    public void deleteWithoutID()
    {
        System.out.print(genericDAO.deleteWithoutID(company));
    }

}