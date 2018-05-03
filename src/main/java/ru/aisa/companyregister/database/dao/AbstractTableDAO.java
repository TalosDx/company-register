package ru.aisa.companyregister.database.dao;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

interface AbstractTableDAO
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
     * @param tableName - имя таблицы
     * @param nameColumns - название колонок базы данных
     * @return возвращает sql запрос типа SELECT
     */
    String getSelectRequest(String tableName, String[] nameColumns);

    /**
     * Формирует sql запрос через в формате NPJT
     * @param tableName - имя таблицы
     * @param nameColumns - название колонок базы данных
     * @return возвращает sql запрос типа SELECT
     */
    String getSelectRequestById(String tableName, String[] nameColumns);


    /**
     * Формирует sql запрос через в формате NPJT
     * @param tableName - имя таблицы
     * @param nameColumns - название колонок базы данных
     * @param conditionColumns - названия колонок для условия
     * @param conditionVariables - имена переменных для условия
     * @return возвращает sql запрос типа SELECT
     */
    String getSelectRequestByCondition(String tableName, String[] nameColumns, String[] conditionColumns, String[] conditionVariables);

    /**
     * Получаем информацию в колонках выбранной таблицы
     * @param tableName - имя таблицы
     * @param nameColumns - имя колонок
     * @param mapper - принимает маппер сущностей
     * @return возвращает List с сущностями
     */
    <T extends RowMapper> List selectAllColumns(String tableName, String[] nameColumns, T mapper);

    /**
     * Получаем информацию в колонках выбранной таблицы для отдельного объекта
     * @param tableName - имя таблицы
     * @param nameColumns - имя колонок
     * @param mapper - принимает маппер сущностей
     * @param id - фильтрует по id
     * @return возвращает сущность
     */
    <T extends RowMapper> Object selectColumnsById(String tableName, String[] nameColumns, int id, T mapper);

    /**
     * Получаем информацию в колонках выбранной таблицы для отдельного объекта
     * @param tableName - имя таблицы
     * @param nameColumns - имя колонок
     * @param conditionColumns - названия колонок для условия
     * @param conditionVariables - имена переменных для условия
     * @param conditionObjects - переменные для условия
     * @param mapper - принимает маппер сущностей
     * @return возвращает сущность
     */
    <T extends RowMapper> List selectColumnsByCondition(String tableName, String[] nameColumns, String[] conditionColumns, String[] conditionVariables, Object[] conditionObjects, T mapper);

    /**
     * Формирует sql запрос через в формате NPJT
     * @param tableName - имя таблицы
     * @param nameColumns - имя колонок
     * @param nameVariables  - названия переменных для объектов (например имя колонки first_name, имя переменной :first_name или :FIRST_NAME)
     * @param conditionColumn - имя колонок для поиска записи в таблице: Where id = :id, - где id это один из элементов conditionColumn
     * @param conditionVariable - название переменных для поиска записи в таблице (:id, :name)
     * @return - возвращает sql запрос типа UPDATE ... WHERE
     */
    String getUpdateRequest(String tableName, String[] nameColumns, String[] nameVariables, String[] conditionColumn, String[] conditionVariable);

    /**
     * Редактирование определенной записи(строки) в таблице.
     * @param tableName - имя таблицы
     * @param nameColumns - имя колонок
     * @param nameVariables - название переменных для объектов (например имя колонки first_name, имя переменной :first_name или :FIRST_NAME)
     * @param objects - объекты для вставки
     * @param conditionColumn - имя колонок для поиска записи в таблице: Where id = :id, - где id это один из элементов conditionColumn
     * @param conditionVariable - название переменных для поиска записи в таблице (:id, :name)
     * @param objectCondition - объекты для поиска записи в таблицы
     * @return - возвращает код выполнения операции
     */
    int updateTable(String tableName, String[] nameColumns, String[] nameVariables, Object[] objects, String[] conditionColumn, String[] conditionVariable, Object[] objectCondition);

    /**
     * Генерирует sql запрос для удаление записи/записей по условию
     * @param tableName - название таблицы
     * @param conditionColumns - название колонок для условия
     * @param conditionVariables - имена переменных для условия
     * @return - возвращает sql запрос типа DELETE ... WHERE
     */
    String getDeleteRequest(String tableName, String[] conditionColumns, String[] conditionVariables);

    /**
     * Удаление записи/записей по условию
     * @param tableName - название таблицы
     * @param conditionColumns - названия колонок для условия
     * @param conditionVariables - имена переменных для условия
     * @param conditionObjects - переменные для условия
     * @return - возвращает код выполнения операции
     */
    int deleteFromTable(String tableName, String[] conditionColumns, String[] conditionVariables, Object[] conditionObjects);

    /**
     * Возвращает количество записей в таблице
     * @param tableName - имя таблицы
     */
    int getCountFromTable(String tableName);
}
