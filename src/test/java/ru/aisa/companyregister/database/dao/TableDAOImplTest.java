package ru.aisa.companyregister.database.dao;

import org.junit.jupiter.api.Test;
import ru.aisa.companyregister.database.dao.mapper.CompanyMapperImpl;

class TableDAOImplTest
{

    private final AbstractTableDAO sql = new TableDAOImpl();

    @Test
    void getInsertRequest()
    {
        System.out.println(sql.getInsertRequest("companies", new String[]{"company_name", "inn", "address", "phone"},
                new String[]{"company_name", "inn", "address", "phone"}));
    }

    @Test
    void insertTable()
    {
        System.out.println(sql.insertTable("companies", new String[]{"company_name", "inn", "address", "phone"},
                new String[]{"company_name", "inn", "address", "phone"},
                new Object[]{"OAO_Builders_and_Cars", 624124, "Санкт-Петербург, ул. Сверлова, д.6", "+798242-41-24"}));

    }

    @Test
    void getSelectRequest()
    {
        System.out.println(sql.getSelectRequest("companies", new String[]{"id", "company_name", "inn", "address", "phone"}));
    }

    @Test
    void getSelectRequestById()
    {
        System.out.println(sql.getSelectRequestById("companies", new String[]{"id", "company_name", "inn", "address", "phone"}));
        System.out.println(sql.getSelectRequestById("company", new String[]{"id", "company_name", "inn", "address", "phone"}));
    }

    @Test
    void selectAllColumns()
    {
        System.out.println(sql.selectAllColumns("companies", new String[]{"id", "company_name", "inn", "address", "phone"}, new CompanyMapperImpl()));
        System.out.println(sql.selectAllColumns("companies", new String[]{"*"}, new CompanyMapperImpl()));

    }

    @Test
    void selectColumnsById()
    {
        System.out.println(sql.selectColumnsById("companies", new String[]{"*"}, 1, new CompanyMapperImpl()));
    }

    @Test
    void getUpdateRequest()
    {
        System.out.println(sql.getUpdateRequest("companies", new String[]{"company_name", "inn", "address", "phone"},
                new String[]{"company_name", "inn", "address", "phone"},
                new String[] {"id"}, new String[]{"id"}));
    }

    @Test
    void updateTable()
    {
        System.out.println(sql.updateTable("companies", new String[]{"company_name", "inn", "address", "phone"},
                new String[]{"company_name", "inn", "address", "phone"},
                new Object[]{"OAO_Builders_and_Cars", 624124, "Санкт-Петербург, ул. Сверлова, д.6", "+798242-41-24"},
                new String[]{"id"}, new String[]{"id"},
                new Object[]{1}));
    }

    @Test
    void getDeleteRequest()
    {
        System.out.println(sql.getDeleteRequest("companies",
                new String[] {"id"}, new String[]{"id"}));
    }

    @Test
    void deleteFromTable()
    {
        System.out.println(sql.deleteFromTable("companies", new String[] {"id"}, new String[]{"id"}, new Object[] {1}));
    }
}