package ru.aisa.companyregister.ui.popup;

import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.*;
import ru.aisa.companyregister.dao.wrapper.enties.GenericDAO;

import java.util.HashMap;
import java.util.List;

public abstract class AbstractPopUpController<T>
{

    GenericDAO<T> genericDAO;
    final BeanItemContainer<T> itemContainer;
    protected final HashMap<BeanItemContainer, GenericDAO> beanContainers = new HashMap();

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

    public void registerBeanItemContainer(BeanItemContainer beanItemContainer, GenericDAO genericDAO)
    {
        beanContainers.put(beanItemContainer, genericDAO);
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
}
