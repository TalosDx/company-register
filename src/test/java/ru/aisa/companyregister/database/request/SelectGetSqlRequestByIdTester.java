package ru.aisa.companyregister.database.request;

import ru.aisa.companyregister.database.request.DefaultSQLSelector;
import ru.aisa.companyregister.database.request.SQLSelect;

public class SelectGetSqlRequestByIdTester
{
    static SQLSelect sqlSelect = new DefaultSQLSelector();

    public static void main(String[] args)
    {
        //sqlSelect.getSqlRequest(
        //                new String[]{ "FULL_NAME", "STACK", "BIRTHDAY", "AGE", "COMPANY"},
        //                new Object[]{"Bobr ex Cat", 64, LocalDate.of(1992,01, 24), 21, "OAO Bobrs forest building"})
        System.out.println(sqlSelect.getSqlRequestById("cats", new String[]{"fullName", "stack", "birthday", "age", "company"}, 7));
    }
}
