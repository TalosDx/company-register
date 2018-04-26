package ru.aisa.companyregister;

import ru.aisa.companyregister.database.mapper.DefaultCompanyMapper;
import ru.aisa.companyregister.database.request.DefaultSQLSelector;
import ru.aisa.companyregister.database.request.SQLSelect;

public class SelectorTester
{
    static SQLSelect sqlInsert = new DefaultSQLSelector();

    public static void main(String[] args)
    {
        //sqlSelect.getSqlRequest(
        //                new String[]{ "FULL_NAME", "STACK", "BIRTHDAY", "AGE", "COMPANY"},
        //                new Object[]{"Bobr ex Cat", 64, LocalDate.of(1992,01, 24), 21, "OAO Bobrs forest building"})
        System.out.println(sqlInsert.getSqlRequest("companies", new String[]{"id", "company_name", "inn", "address", "phone"}));

        System.out.println(sqlInsert.SelectAll("companies", new String[]{"id", "company_name", "inn", "address", "phone"}, new DefaultCompanyMapper()));
        System.out.println(sqlInsert.SelectAll("companies", new String[]{"*"}, new DefaultCompanyMapper()));

    }
}
