package ru.aisa.companyregister.database.dao;

import ru.aisa.companyregister.database.dao.mapper.EmployeeMapperImpl;
import ru.aisa.companyregister.entity.Employee;

import java.sql.Date;
import java.util.List;

public class EmployeeGenericDAOImpl implements GenericDAO<Employee>
{
    AbstractTableDAO sqlExecutor = new TableDAOImpl();
    final String[] employeeColumns = new String[]{"id", "full_name", "birthday", "email", "company_name"};
    final String[] employeeColumnsWithOutId = new String[]{"full_name", "birthday", "email", "company_name"};
    final String tableName = "employee";

    @Override
    public int create(Employee object)
    {
        return sqlExecutor.insertTable(tableName, employeeColumnsWithOutId, employeeColumnsWithOutId, new Object[]{object.getFullName(), object.getBirthday(), object.getEmail(), object.getCompanyName()});
    }

    @Override
    public Employee read(int id)
    {
        return (Employee) sqlExecutor.selectColumnsById(tableName, employeeColumns, id, new EmployeeMapperImpl());
    }

    @Override
    public int getCount()
    {
        return sqlExecutor.getCountFromTable(tableName);
    }

    @Override
    public List<Employee> read(String[] conditionColumns, Object[] objects)
    {
        return sqlExecutor.selectColumnsByCondition(tableName, employeeColumns, conditionColumns, conditionColumns, objects, new EmployeeMapperImpl());
    }

    @Override
    public List<Employee> readAll()
    {
        return sqlExecutor.selectAllColumns(tableName, employeeColumns, new EmployeeMapperImpl());
    }

    @Override
    public int updateById(Employee transientObject, int id)
    {
        return sqlExecutor.updateTable(tableName, employeeColumnsWithOutId, employeeColumnsWithOutId, new Object[]{transientObject.getFullName(), Date.valueOf(transientObject.getBirthday()), transientObject.getEmail(), transientObject.getCompanyName()}, new String[]{"id"}, new String[]{"id"}, new Object[]{id});
    }

    @Override
    public int delete(Employee persistentObject)
    {
        return sqlExecutor.deleteFromTable(tableName, employeeColumns, employeeColumns, new Object[]{persistentObject.getId(), persistentObject.getFullName(), Date.valueOf(persistentObject.getBirthday()), persistentObject.getEmail(), persistentObject.getCompanyName()});
    }

    @Override
    public int deleteByID(int id)
    {
        return sqlExecutor.deleteFromTable(tableName, new String[]{"id"}, new String[]{"id"}, new Object[]{id});
    }

    @Override
    public int deleteWithoutID(Employee persistentObject)
    {
        return sqlExecutor.deleteFromTable(tableName, employeeColumnsWithOutId, employeeColumnsWithOutId, new Object[]{persistentObject.getFullName(), Date.valueOf(persistentObject.getBirthday()), persistentObject.getEmail(), persistentObject.getCompanyName()});
    }

    @Override
    public String[] getTableColumns()
    {
        return employeeColumns;
    }

    @Override
    public String getTableName()
    {
        return tableName;
    }
}
