package ru.aisa.companyregister.utils;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Properties;

public class LazyUtils
{

    /**
     * Проверяет валидаторы на соотвествие и выводит ошибку с помощью Notification type error
     * @param fields - передаваемые поля
     */
    public static void showErrorWithFields(Field[] fields)
    {
        for(int i=0; i<fields.length; i++)
            if (!fields[i].isValid())
            {
                showErrorFromValidators(fields[i]);
            }
    }

    public static void showErrorFromValidators(Field field)
    {
        for (Validator validator : field.getValidators())
        {
            AbstractValidator abstractValidator = ((AbstractValidator) validator);
            if (!abstractValidator.isValid(field.getValue()))
                Notification.show(((AbstractValidator) validator).getErrorMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    public static void clearAction(Layout layout, Component[] components)
    {
        for (Component component : components)
            layout.removeComponent(component);
    }


    public static void clearFields(Field[] fields)
    {
        for(Field field : fields)
            field.clear();
    }


    public static LocalDate toLocalDate(java.util.Date date)
    {
        return LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
    }
    public static Grid createGrid(String[] nameColumns, Class<?>[] types)
    {
        Grid grid = new Grid();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setImmediate(true);
        int size;
        if(nameColumns.length == types.length) size = nameColumns.length;
        else throw new IllegalArgumentException("createGrid: nameColumns.length != types.length");
        for(int i=0; i<size; i++)
            grid.addColumn(nameColumns[i], types[i]);
        return grid;
    }

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
