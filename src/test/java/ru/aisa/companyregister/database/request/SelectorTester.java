package ru.aisa.companyregister.database.request;

import ru.aisa.companyregister.database.mapper.DefaultCompanyMapper;

public class SelectorTester
{
    static SQLSelect sqlInsert = new DefaultSQLSelector();

    public static void main(String[] args)
    {
        //sqlSelect.getInsertRequest(
        //                new String[]{ "FULL_NAME", "STACK", "BIRTHDAY", "AGE", "COMPANY"},
        //                new Object[]{"Bobr ex Cat", 64, LocalDate.of(1992,01, 24), 21, "OAO Bobrs forest building"})
        System.out.println(sqlInsert.getSelectRequest("companies", new String[]{"id", "company_name", "inn", "address", "phone"}));

        System.out.println(sqlInsert.selectAllColumns("companies", new String[]{"id", "company_name", "inn", "address", "phone"}, new DefaultCompanyMapper()));
        System.out.println(sqlInsert.selectAllColumns("companies", new String[]{"*"}, new DefaultCompanyMapper()));

    }
}
