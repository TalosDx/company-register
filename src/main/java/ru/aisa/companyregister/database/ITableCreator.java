package ru.aisa.companyregister.database;

import java.util.Objects;

public interface ITableCreator
{
    String createTable(String tableName, String[] objects, String[] types);

    void setIfNotExists(Boolean ifNonExists);

    boolean getIfNotExists();
}
