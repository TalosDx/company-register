package ru.aisa.companyregister.ui.validator;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.StringLengthValidator;

import java.util.HashMap;

public class CompaniesValidatorMapperImpl implements ColumnsValidatorMapper
{
    static HashMap<String, Validator> validatorMap = new HashMap<>();

    static
    {
        init();
    }

    public static void init()
    {
        validatorMap.put("company_name", new AbstractStringValidator("Любые символы кроме цифр")
        {
            @Override
            protected boolean isValidValue(String value)
            {
                return value.length() > 2 && value.length() < 120+1 && value.matches("[^0-9]+");
            }
        });
        validatorMap.put("inn", new AbstractStringValidator("Только цифры")
        {
            @Override
            protected boolean isValidValue(String value)
            {
                return value.length() < 12+1 && value.matches("[0-9]+");
            }
        });
        validatorMap.put("address", new StringLengthValidator("Адрес больше 250 символов", 1, 251, false));
        validatorMap.put("phone", new AbstractStringValidator("Формат ввода телефона: +7952-124-14-15")
        {
            @Override
            protected boolean isValidValue(String value)
            {
                return value.matches("\\+[7-8][0-9][0-9]{2}\\-[0-9]{3}\\-[0-9]{2}\\-[0-9]{2}");
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
