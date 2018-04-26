package ru.aisa.companyregister.database.request;

public class InsertTester
{

    static SQLInsert sqlInsert = new DefaultSQLInsert();

    public static void main(String[] args)
    {
        //sqlSelect.getInsertRequest(
        //                new String[]{ "FULL_NAME", "STACK", "BIRTHDAY", "AGE", "COMPANY"},
        //                new Object[]{"Bobr ex Cat", 64, LocalDate.of(1992,01, 24), 21, "OAO Bobrs forest building"})
        System.out.println(sqlInsert.getInsertRequest("companies", new String[]{"company_name", "inn", "address", "phone"},
                new String[]{"company_name", "inn", "address", "phone"}));

        System.out.println(sqlInsert.insert("companies", new String[]{"company_name", "inn", "address", "phone"},
                new String[]{"company_name", "inn", "address", "phone"},
                new Object[]{"OAO_Builders_and_Cars", 624124, "Санкт-Петербург, ул. Сверлова, д.6", "+798242-41-24"}));
    }
}
