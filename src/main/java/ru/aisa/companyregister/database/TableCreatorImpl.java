package ru.aisa.companyregister.database;

public class TableCreatorImpl implements AbstractTableCreator
{
    private Boolean ifNotExists = true;
    private String SQL_CREATE_TABLE;

    @Override
    public String getCreateRequest(String tableName, String[] nameColumns, String[] typeColumns)
    {
        if (nameColumns.length != typeColumns.length)
            throw new IllegalArgumentException("getCreateRequest: objects.length != types.length...");
        String str;
        for (int i = 0; i < typeColumns.length; i++)
        {
            if (i == 0)
                if (!this.ifNotExists)
                    SQL_CREATE_TABLE = "CREATE TABLE " + tableName + " (";
                else if (this.ifNotExists)
                    SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + tableName + " (";

            str = i == typeColumns.length - 1 ? ");" : ",";
            SQL_CREATE_TABLE += " " + nameColumns[i] + " " + typeColumns[i] + str;
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
