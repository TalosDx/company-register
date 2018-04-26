package ru.aisa.companyregister.database.request;

public class SelectGetSqlRequestTester
{
    static SQLSelect sqlSelect = new DefaultSQLSelector();

    public static void main(String[] args)
    {
        //sqlSelect.getInsertRequest(
        //                new String[]{ "FULL_NAME", "STACK", "BIRTHDAY", "AGE", "COMPANY"},
        //                new Object[]{"Bobr ex Cat", 64, LocalDate.of(1992,01, 24), 21, "OAO Bobrs forest building"})
        System.out.println(sqlSelect.getSelectRequest("cats", new String[]{"fullName", "stack", "birthday", "age", "company"}));
    }
}
