package ru.aisa.companyregister.database;

import org.junit.jupiter.api.Test;
import ru.aisa.companyregister.database.dao.AbstractTableDAO;
import ru.aisa.companyregister.database.dao.TableDAOImpl;

import static org.junit.jupiter.api.Assertions.*;

class TableCreatorImplTest
{
    AbstractTableCreator tableCreator = new TableCreatorImpl();

    @Test
    void getCreateRequest()
    {
        System.out.println(tableCreator.getCreateRequest("companies",
                new String[]{"id", "company_name", "inn", "address", "phone"},
                new String[]{"serial primary key", "char(150)", "integer", "char(150)", "char(18)"}));
    }

    @Test
    void setIfNotExists()
    {
    }

    @Test
    void getIfNotExists()
    {
    }
}