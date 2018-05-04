package ru.aisa.companyregister.ui;

import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.GenericDAO;

import java.util.List;

public abstract class AbstractPopUpController<T>
{

    GenericDAO<T> genericDAO;
    final BeanItemContainer<T> itemContainer;

    /**
     * Обязательный конструктор для указания genericDao с которым работаем
     * @param genericDAO - используется для управлением объектом такому как добавление объекта или его редактирование или удаления
     */
    AbstractPopUpController(GenericDAO<T> genericDAO, BeanItemContainer<T> itemContainer)
    {
        this.genericDAO = genericDAO;
        this.itemContainer = itemContainer;
    }

    GenericDAO<T> getDAO()
    {
        return genericDAO;
    }

    public void setDAO(GenericDAO<T> genericDAO)
    {
        this.genericDAO = genericDAO;
    }

    /**
     * Получаем объект(айтем) по имени в коллекции
     * @param name - где имя объекта например у компаний это "companyName", у сотрудников "fullName"
     * @param list - коллекция с объектами в которой ищем
     * @return - возвращает объект если найдем, иначе возвращает null
     */
    public abstract T getItemFromName(String name, List<T> list);

    /**
     * Обновляет данные в полях
     */
    public abstract void updateItemData();

    public abstract void init(Window windowZ);

    /**
     * Окно добавления объекта в таблицу
     * @param window - используется для отображения объектов
     */
    public abstract void displayAddItem(Window window);

    /**
     * Окно редактирования объекта
     * @param window - используется для отображения объектов
     * @param item - объект с которым оперируем
     */
    public abstract void displayEditItem(Window window, T item);

    /**
     * Окно удаления объекта
     * @param window - используется для отображения объектов
     * @param item - объект с которым оперируем
     */
    public abstract void displayDeleteItem(Window window, T item);

    protected void showErrorWithFields(Field[] fields)
    {
        for(int i=0; i<fields.length; i++)
            if (!fields[i].isValid())
            {
                showErrorFromValidators(fields[i]);
            }
    }

    protected void showErrorFromValidators(Field field)
    {
        for (Validator validator : field.getValidators())
        {
            AbstractValidator abstractValidator = ((AbstractValidator) validator);
            if (!abstractValidator.isValid(field.getValue()))
                Notification.show(((AbstractValidator) validator).getErrorMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    void clearAction(Layout layout, Component[] components)
    {
        for (Component component : components)
            layout.removeComponent(component);
    }


    void clearFields(Field[] fields)
    {
        for(Field field : fields)
            field.clear();
    }
}
