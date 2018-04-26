package ru.aisa.companyregister.database.request;


public class DefaultSQLUpdateTest
{

    //new Object[]{"OAO_Builders_and_Cars", 624124, "Санкт-Петербург, ул. Сверлова, д.6", "+798242-41-24"}
    static SQLUpdate sqlUpdate = new DefaultSQLUpdate();
    public static void main(String[] args)
    {
        System.out.println(sqlUpdate.getSqlRequest("companies", new String[]{"company_name", "inn", "address", "phone"},
                new String[]{"company_name", "inn", "address", "phone"},
                new String[] {"id"}, new String[]{"id"}));
    }
}
