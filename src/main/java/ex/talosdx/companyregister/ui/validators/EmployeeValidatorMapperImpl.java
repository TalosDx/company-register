package ex.talosdx.companyregister.ui.validators;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.shared.ui.datefield.Resolution;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class EmployeeValidatorMapperImpl implements ColumnsValidatorMapper
{
    private static final HashMap<String, Validator> validatorMap = new HashMap<>();

    static
    {
        init();
    }

    private static void init()
    {
        validatorMap.put("full_name", new AbstractStringValidator("Имя не может быть пустым или содержать цифры")
        {
            @Override
            protected boolean isValidValue(String value)
            {
                return value != null && value.length() > 2 && value.length() < 120+1 && value.matches("[^0-9]+");
            }
        });
        Calendar firstDate = new GregorianCalendar();
        firstDate.set(1800,1,1);
        Calendar secondDate = GregorianCalendar.getInstance();
        secondDate.add(Calendar.YEAR, -18);
        validatorMap.put("birthday", new DateRangeValidator("Возраст сотрудника не может быть ниже 18 и выше 200+ лет", firstDate.getTime(), secondDate.getTime(), Resolution.DAY));
        validatorMap.put("email", new EmailValidator("Неправильный email, используйте формат: username@mail.com"));
        validatorMap.put("company_name", new IntegerRangeValidator("Компания не выбрана, выберите компавнию", 0, Integer.MAX_VALUE));
    }

    @Override
    public String getTableName()
    {
        return "companies";
    }

    /**
     * Возвращает валидатор для имени колонки в базе данных
     * @param columnName - имя колонки
     * @return
     */
    @Override
    public Validator getValidatorFromColumn(String columnName)
    {
        return validatorMap.get(columnName);
    }
}
