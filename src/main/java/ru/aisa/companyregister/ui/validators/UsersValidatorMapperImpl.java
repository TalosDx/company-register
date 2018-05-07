package ru.aisa.companyregister.ui.validators;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.AbstractStringValidator;

import java.util.HashMap;

public class UsersValidatorMapperImpl implements ColumnsValidatorMapper
{
    private static final HashMap<String, Validator> validatorMap = new HashMap<>();

    static
    {
        init();
    }

    private static void init()
    {
        validatorMap.put("username", new AbstractStringValidator("Имя не может быть пустым, содержать цифры и быть меньше 3 символов и больше 15")
        {
            @Override
            protected boolean isValidValue(String value)
            {
                return value != null && value.length() > 3 && value.length() < 15+1 && value.matches("^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{0,19}$");
            }
        });
        validatorMap.put("password", new AbstractStringValidator("Пароль не может быть пустым, быть меньше 5 символов и больше 30")
        {
            @Override
            protected boolean isValidValue(String value)
            {
                return value != null && value.length() > 5 && value.length() < 30+1;
            }
        });
    }


    @Override
    public String getTableName()
    {
        return "users";
    }

    @Override
    public Validator getValidatorFromColumn(String columnName)
    {
        return validatorMap.get(columnName);
    }
}
