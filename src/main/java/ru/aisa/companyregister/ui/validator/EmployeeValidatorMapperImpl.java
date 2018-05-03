package ru.aisa.companyregister.ui.validator;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.shared.ui.datefield.Resolution;

import java.util.Date;
import java.util.HashMap;

public class EmployeeValidatorMapperImpl implements ColumnsValidatorMapper
{
    static HashMap<String, Validator> validatorMap = new HashMap<>();

    static
    {
        init();
    }

    public static void init()
    {
        validatorMap.put("full_name", new AbstractStringValidator("Любые символы кроме цифр")
        {
            @Override
            protected boolean isValidValue(String value)
            {
                return value.length() > 2 && value.length() < 120+1 && value.matches("[^0-9]+");
            }
        });
        validatorMap.put("birthday", new DateRangeValidator("Not valid date", new Date(1900, 1, 1), new Date(9999, 12, 31), Resolution.DAY));
        validatorMap.put("email", new EmailValidator("Неправильный email, пример: username@gmail.com"));
        validatorMap.put("company_name", new AbstractStringValidator("Любые символы кроме цифр")
        {
            @Override
            protected boolean isValidValue(String value)
            {
                return value.length() > 2 && value.length() < 120+1 && value.matches("[^0-9]+");
            }
        });
    }

    @Override
    public String getTableName()
    {
        return "companies";
    }

    @Override
    public Validator getValidatorFromColumn(String columnName)
    {
        return validatorMap.get(columnName);
    }
}
