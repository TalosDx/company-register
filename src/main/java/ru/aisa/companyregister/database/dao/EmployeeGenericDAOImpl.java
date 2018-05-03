package ru.aisa.companyregister.database.dao;

import ru.aisa.companyregister.database.dao.mapper.EmployeeMapperImpl;
import ru.aisa.companyregister.database.dao.entities.Employee;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EmployeeGenericDAOImpl implements GenericDAO<Employee>
{
    private final AbstractTableDAO sqlExecutor = new TableDAOImpl();
    private final String[] columns = new String[]{"id", "full_name", "birthday", "email", "company_name"};
    private final String[] columnsWithoutId = new String[]{"full_name", "birthday", "email", "company_name"};
    private final Class<?>[] types = new Class[]{Integer.class, String.class, LocalDate.class, String.class, String.class};
    private final Class<?>[] typesWithoutId = new Class[]{String.class, LocalDate.class, String.class, String.class};
    private final String tableName = "employee";

    @Override
    public int create(Employee object)
    {
        return sqlExecutor.insertTable(tableName, columnsWithoutId, columnsWithoutId, new Object[]{object.getFullName(), object.getBirthday(), object.getEmail(), object.getCompanyName()});
    }

    @Override
    public Employee read(int id)
    {
        return (Employee) sqlExecutor.selectColumnsById(tableName, columns, id, new EmployeeMapperImpl());
    }

    @Override
    public int getCount()
    {
        return sqlExecutor.getCountFromTable(tableName);
    }

    @Override
    public List<Employee> read(String[] conditionColumns, Object[] objects)
    {
        return sqlExecutor.selectColumnsByCondition(tableName, columns, conditionColumns, conditionColumns, objects, new EmployeeMapperImpl());
    }

    @Override
    public List<Employee> readAll()
    {
        return sqlExecutor.selectAllColumns(tableName, columns, new EmployeeMapperImpl());
    }

    @Override
    public int updateById(Employee transientObject, int id)
    {
        return sqlExecutor.updateTable(tableName, columnsWithoutId, columnsWithoutId, new Object[]{transientObject.getFullName(), Date.valueOf(transientObject.getBirthday()), transientObject.getEmail(), transientObject.getCompanyName()}, new String[]{"id"}, new String[]{"id"}, new Object[]{id});
    }

    @Override
    public int delete(Employee persistentObject)
    {
        return sqlExecutor.deleteFromTable(tableName, columns, columns, new Object[]{persistentObject.getId(), persistentObject.getFullName(), Date.valueOf(persistentObject.getBirthday()), persistentObject.getEmail(), persistentObject.getCompanyName()});
    }

    @Override
    public int deleteByID(int id)
    {
        return sqlExecutor.deleteFromTable(tableName, new String[]{"id"}, new String[]{"id"}, new Object[]{id});
    }

    @Override
    public int deleteWithoutID(Employee persistentObject)
    {
        return sqlExecutor.deleteFromTable(tableName, columnsWithoutId, columnsWithoutId, new Object[]{persistentObject.getFullName(), Date.valueOf(persistentObject.getBirthday()), persistentObject.getEmail(), persistentObject.getCompanyName()});
    }

    @Override
    public String[] getTableColumns()
    {
        return columns;
    }

    @Override
    public String[] getTableColumnsWithoutId()
    {
        return columnsWithoutId;
    }

    @Override
    public Class<?>[] getTableTypes()
    {
        return types;
    }

    @Override
    public Class<?>[] getTableTypesWithoutId()
    {
        return typesWithoutId;
    }

    @Override
    public String getTableName()
    {
        return tableName;
    }
}
