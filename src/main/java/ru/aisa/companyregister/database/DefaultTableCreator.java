package ru.aisa.companyregister.database;

public class DefaultTableCreator implements ITableCreator
{
    private Boolean ifNotExists = true;
    private String SQL_CREATE_TABLE;

    @Override
    public String createTable(String tableName, String[] objects, String[] types)
    {
        if (objects.length != types.length)
            throw new IllegalArgumentException("createTable: objects.length != types.length...");
        String str;
        for (int i = 0; i < types.length; i++)
        {
            if (i == 0)
                if (!this.ifNotExists)
                    SQL_CREATE_TABLE = "CREATE TABLE " + tableName + " (";
                else if (this.ifNotExists)
                    SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + tableName + " (";

            str = i == types.length - 1 ? ");" : ",";
            SQL_CREATE_TABLE += " " + objects[i] + " " + types[i] + str;
        }
        return SQL_CREATE_TABLE;
    }

    @Override
    public void setIfNotExists(Boolean ifNonExists)
    {
        this.ifNotExists = ifNonExists;
    }

    @Override
    public boolean getIfNotExists()
    {
        return ifNotExists;
    }
}
