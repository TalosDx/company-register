package ex.talosdx.companyregister.dao.wrapper.enties;

import org.springframework.dao.EmptyResultDataAccessException;

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


    T readByCondition(String[] columns, String conditionColumn, Object conditionObjects) throws EmptyResultDataAccessException;

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
     * @return - возвращает все java-типы колонок для таблицы
     */
    Class<?>[] getTableTypes();

    /**
     * Возвращает типы данных в виде классов для каждой колонки в таблице за исключением id
     * @return - возвращает все java-типы колонок для таблицы, кроме id
     */
    Class<?>[] getTableTypesWithoutId();


    /**
     * @return -Возвращает имя таблицы
     */
    String getTableName();


}
