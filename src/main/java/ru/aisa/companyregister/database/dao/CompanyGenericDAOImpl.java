package ru.aisa.companyregister.database.dao;

import ru.aisa.companyregister.database.dao.mapper.CompanyMapperImpl;
import ru.aisa.companyregister.entity.Company;

import java.util.List;

public class CompanyGenericDAOImpl implements GenericDAO<Company>
{
    AbstractTableDAO sqlExecutor = new TableDAOImpl();
    final String[] companyColumns = new String[]{"id", "company_name", "inn", "address", "phone"};
    final String[] companyColumnsWithID = new String[]{"company_name", "inn", "address", "phone"};
    final String tableName = "companies";

    @Override
    public int create(Company object)
    {
        return sqlExecutor.insertTable(tableName, companyColumnsWithID, companyColumnsWithID, new Object[]{object.getCompanyName(), object.getInn(), object.getAddress(), object.getPhone()});
    }

    @Override
    public Company read(int id)
    {
        return (Company) sqlExecutor.selectColumnsById(tableName, companyColumns, id, new CompanyMapperImpl());
    }

    @Override
    public int getCount()
    {
        return sqlExecutor.getCountFromTable(tableName);
    }

    @Override
    public List<Company> read(String[] conditionColumns, Object[] objects)
    {
        return sqlExecutor.selectColumnsByCondition(tableName, companyColumns, conditionColumns, conditionColumns, objects, new CompanyMapperImpl());
    }

    @Override
    public List<Company> readAll()
    {
        return sqlExecutor.selectAllColumns(tableName, companyColumns, new CompanyMapperImpl());
    }

    @Override
    public int updateById(Company transientObject, int id)
    {
        return sqlExecutor.updateTable(tableName, companyColumnsWithID, companyColumnsWithID, new Object[]{transientObject.getCompanyName(), transientObject.getInn(), transientObject.getAddress(), transientObject.getPhone()}, new String[]{"id"}, new String[]{"id"}, new Object[]{id});
    }

    @Override
    public int delete(Company persistentObject)
    {
        return sqlExecutor.deleteFromTable(tableName, companyColumns, companyColumns, new Object[]{persistentObject.getId(), persistentObject.getCompanyName(), persistentObject.getInn(), persistentObject.getAddress(), persistentObject.getPhone()});
    }

    @Override
    public int deleteByID(int id)
    {
        return sqlExecutor.deleteFromTable(tableName, new String[]{"id"}, new String[]{"id"}, new Object[]{id});
    }

    @Override
    public int deleteWithoutID(Company persistentObject)
    {
        return sqlExecutor.deleteFromTable(tableName, companyColumnsWithID, companyColumnsWithID, new Object[]{persistentObject.getCompanyName(), persistentObject.getInn(), persistentObject.getAddress(), persistentObject.getPhone()});
    }


    @Override
    public String[] getTableColumns()
    {
        return companyColumns;
    }

    @Override
    public String getTableName()
    {
        return tableName;
    }
}
