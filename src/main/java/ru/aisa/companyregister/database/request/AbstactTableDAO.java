package ru.aisa.companyregister.database.request;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public interface AbstactTableDAO
{
    /**
     * Формирует sql запрос в формате NPJT
     * @param table - имя таблицы
     * @param nameColumns - название колонок базы данных
     * @param nameVariables - названия внутренних переменных для объектов (например имя колонки first_name, имя переменной :first_name или :FIRST_NAME)
     * @return - возвращает sql запрос типа INSERT INTO
     */
    String getInsertRequest(String table, String[] nameColumns, String[] nameVariables);

    /**
     * Вставка в базу данных в выбранную таблицу записей
     * @param tableName - имя таблицы
     * @param nameColumns - названием колонок базы данных
     * @param nameVariables - названия внутренних переменных для объектов (например имя колонки first_name, имя переменной :first_name или :FIRST_NAME)
     * @param objects - сами значения для колонок в таблице
     * @return - возвращает код операции с бд
     */
    int insertTable(String tableName, String[] nameColumns, String[] nameVariables, Object[] objects);

    /**
     * Формирует sql запрос через в формате NPJT
     * @param table - имя таблицы
     * @param nameColumns - название колонок базы данных
     * @return возвращает sql запрос типа SELECT
     */
    String getSelectRequest(String table, String[] nameColumns);

    /**
     * Формирует sql запрос через в формате NPJT
     * @param tableName - имя таблицы
     * @param nameColumns - название колонок базы данных
     * @param id  - ид записи в таблицы
     * @return возвращает sql запрос типа SELECT
     */
    String getSelectRequestById(String tableName, String[] nameColumns, int id);


    /**
     * Получаем информацию в колонках выбранной таблицы
     * @param table - имя таблицы
     * @param nameColumns - имя колонок
     * @param mapper - принимает маппер сущностей
     * @return возвращает List с сущностями
     */
    <T extends RowMapper> List<T> selectAllColumns(String table, String[] nameColumns, T mapper);

    /**
     * Получаем информацию в колонках выбранной таблицы для отдельного объекта
     * @param table - имя таблицы
     * @param nameColumns - имя колонок
     * @param mapper - принимает маппер сущностей
     * @param id - фильтрует по id
     * @return возвращает сущность
     */
    <T extends RowMapper> Object selectColumnsById(String table, String[] nameColumns, int id, T mapper);
}
