package ru.aisa.companyregister.utils;

import java.time.LocalDate;
import java.util.HashMap;

public class DataMapper
{
    private final HashMap<String, Class<?>> DATA_MAP = new HashMap<>();

    public DataMapper()
    {
        DATA_MAP.put("boolean", Boolean.class);
        DATA_MAP.put("date", LocalDate.class);
        DATA_MAP.put("integer", Integer.class);
        DATA_MAP.put("serial", Integer.class);
        DATA_MAP.put("char", String.class);
        DATA_MAP.put("money", Double.class);
        DATA_MAP.put("real", Float.class);
        DATA_MAP.put("character", String.class);
    }

    public Class<?> getClass(String database_type)
    {
        return DATA_MAP.get(database_type);
    }

}
