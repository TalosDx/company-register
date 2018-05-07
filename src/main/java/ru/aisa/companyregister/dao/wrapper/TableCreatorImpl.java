package ru.aisa.companyregister.dao.wrapper;

public class TableCreatorImpl implements AbstractTableCreator
{
    private Boolean ifNotExists = true;


    @Override
    public String getCreateRequest(String tableName, String[] nameColumns, String[] typeColumns, String uniqueColumn)
    {
        String SQL_CREATE_TABLE="";
        String constraint="";
        if(uniqueColumn != null)
            constraint = ", CONSTRAINT AK_" + uniqueColumn + " UNIQUE(" + uniqueColumn + ")";
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

            str = i == typeColumns.length - 1 ? constraint + ");" : ",";
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
