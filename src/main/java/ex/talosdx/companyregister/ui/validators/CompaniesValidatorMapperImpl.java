package ex.talosdx.companyregister.ui.validators;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.StringLengthValidator;

import java.util.HashMap;

public class CompaniesValidatorMapperImpl implements ColumnsValidatorMapper
{
    private static final HashMap<String, Validator> validatorMap = new HashMap<>();

    static
    {
        init();
    }

    private static void init()
    {
        validatorMap.put("company_name", new AbstractStringValidator("Имя компании не может быть пустым или содержать цифры")
        {
            @Override
            protected boolean isValidValue(String value)
            {
                return value.length() > 2 && value.length() < 120+1 && value.matches("[^0-9]+");
            }
        });
        validatorMap.put("inn", new AbstractStringValidator("ИНН не может быть больше 12 и может содержать только цифры")
        {
            @Override
            protected boolean isValidValue(String value)
            {
                return value.length() < 12+1 && value.matches("[0-9]+");
            }
        });
        validatorMap.put("address", new StringLengthValidator("Адрес не может быть пустым и быть больше 250 символов", 1, 251, false));
        validatorMap.put("phone", new AbstractStringValidator("Неправильный номер телефона, используйте формат: +7952-124-14-15")
        {
            @Override
            protected boolean isValidValue(String value)
            {
                return value.matches("\\+[7-8][0-9][0-9]{2}-[0-9]{3}-[0-9]{2}-[0-9]{2}");
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
