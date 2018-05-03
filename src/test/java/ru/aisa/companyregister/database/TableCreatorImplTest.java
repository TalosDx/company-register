package ru.aisa.companyregister.database;

import org.junit.jupiter.api.Test;

class TableCreatorImplTest
{
    private final AbstractTableCreator tableCreator = new TableCreatorImpl();

    @Test
    void getCreateRequest()
    {
        System.out.println(tableCreator.getCreateRequest("companies",
                new String[]{"id", "company_name", "inn", "address", "phone"},
                new String[]{"serial primary key", "char(150)", "integer", "char(150)", "char(18)"}));
    }
}