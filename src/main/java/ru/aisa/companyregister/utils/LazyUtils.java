package ru.aisa.companyregister.utils;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class LazyUtils
{

    public static String getProperties(String property)
    {
        Properties prop = new Properties();
        return getString(property, prop, "properties.conf", false);
    }

    public static String getLangProperties(String property)
    {
        Properties prop = new Properties();
        return getString(property, prop, "lang.conf", true);
    }

    private static String getString(String property, Properties prop, String s, boolean isLang)
    {
        try
        {
            if (isLang)
                prop.load(new InputStreamReader(LazyUtils.class.getClassLoader().getResourceAsStream(s), "UTF-8"));
            else
                prop.load(LazyUtils.class.getClassLoader().getResourceAsStream(s));
            String str = prop.getProperty(property);
            if ((str == null || str.isEmpty()) && isLang) return property;
            else return str;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean hasWindow(UI ui,  Window window)
    {
        return ui.getWindows().contains(window);
    }

}
