package ru.aisa.companyregister.database.request;

public class InsertGetSqlRequestTester
{

    static SQLInsert sqlInsert = new DefaultSQLInsert();

    public static void main(String[] args)
    {
        //sqlSelect.getInsertRequest(
        //                new String[]{ "FULL_NAME", "STACK", "BIRTHDAY", "AGE", "COMPANY"},
        //                new Object[]{"Bobr ex Cat", 64, LocalDate.of(1992,01, 24), 21, "OAO Bobrs forest building"})
        System.out.println(sqlInsert.getInsertRequest("companies", new String[]{"fullName", "stack", "birthday", "age", "company"},
                new String[]{"FULL_NAME", "STACK", "BIRTHDAY", "AGE", "COMPANY"}));
    }
}
