package ru.aisa.companyregister.database.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Обертка сущности
 * @param <T> - сущность
 */
public interface GenericDAO<T>
{

    /**
     * Сохранить объект newInstance в базе данных
     */
    int create(T newInstance);

    /**
     * Извлечь объект, предварительно сохраненный в базе данных, используя
     * указанный id в качестве первичного ключа
     */
    T read(int id);

    /**
     * Возвращает количество записей в таблице сущности
     */
    int getCount();

    /**
     * Извлечь объекты, предварительно сохраненные в базе данных, используя
     * указанные имена колонок и их значения
     */
    List<T> read(String[] conditionColumns, Object[] conditionObjects);

    /**
     * Извлечь всю таблицу
     */
    List<T> readAll();

    /**
     * Сохранить изменения, сделанные в объекте.
     */
    int updateById(T transientObject, int id);

    /**
     * Удалить объект из базы данных
     */
    int delete(T persistentObject);

    /**
     * Удалить объект из базы данных по иду
     */
    int deleteByID(int id);

    /**
     * Удалить объект из базы данных по всему кроме ида
     */
    int deleteWithoutID(T persistentObject);
    /**
     * @return - возвращает массив с именами колонок
     */
    String[] getTableColumns();

    /**
     * @return - возвращает массив с именами колонок без id
     */
    String[] getTableColumnsWithoutId();


    /**
     * Возвращает типы данных в виде классов для каждой колонки в таблице
     * @return
     */
    Class<?>[] getTableTypes();

    /**
     * Возвращает типы данных в виде классов для каждой колонки в таблице за исключением id
     * @return
     */
    Class<?>[] getTableTypesWithoutId();


    /**
     * @return -Возвращает имя таблицы
     */
    String getTableName();


}
