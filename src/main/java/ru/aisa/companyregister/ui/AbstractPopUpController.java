package ru.aisa.companyregister.ui;

import com.vaadin.ui.Layout;
import ru.aisa.companyregister.database.dao.GenericDAO;

import java.util.List;

public abstract class AbstractPopUpController<T>
{

    GenericDAO<T> genericDAO;

    /**
     * Обязательный конструктор для указания genericDao с которым работаем
     * @param genericDAO - используется для управлением объектом такому как добавление объекта или его редактирование или удаления
     */
    public AbstractPopUpController(GenericDAO<T> genericDAO)
    {
        this.genericDAO = genericDAO;
    }

    public GenericDAO<T> getDAO()
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

    public abstract void init(Layout layout);

    /**
     * Окно добавления объекта в таблицу
     * @param layout - лейаут используется для отображения объектов
     */
    public abstract void displayAddItem(Layout layout);

    /**
     * Окно редактирования объекта
     * Требует указания поля id
     * @param layout - лейтаут используется для отображения объектов
     * @param item - объект с которым оперируем
     * @param id - ид редактируемого объекта
     */
    public abstract void displayEditItem(Layout layout, T item, int id);

    /**
     * Окно удаления объекта
     * Требует указания поля id
     * @param layout - лейтаут используется для отображения объектов
     * @param item - объект с которым оперируем
     * @param id - ид редактируемого объекта
     */
    public abstract void displayDeleteItem(Layout layout, T item, int id);
}
