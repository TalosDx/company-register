package ru.aisa.companyregister.dao.wrapper.enties;

import ru.aisa.companyregister.dao.wrapper.AbstractTableDAO;
import ru.aisa.companyregister.dao.wrapper.TableDAOImpl;
import ru.aisa.companyregister.dao.entities.User;
import ru.aisa.companyregister.dao.mapper.UserMapperImpl;

import java.util.List;

public class UserGenericDAOImpl implements GenericDAO<User>
{
    private final AbstractTableDAO sqlExecutor = new TableDAOImpl();
    private final String[] columns = new String[]{"id", "username", "password"};
    private final String[] columnsWithoutPassword = new String[]{"id", "username"};
    private final Class<?>[] types = new Class[]{Integer.class, String.class, String.class};
    private final Class<?>[] typesWithoutPassword = new Class[]{Integer.class, String.class};

    private final String[] columnsWithoutId = new String[]{"username", "password"};
    private final Class<?>[] typesWithoutId = new Class[]{String.class, String.class};
    private final String[] columnsWithoutIdPassword = new String[]{"username"};
    private final Class<?>[] typesWithoutIdPassword = new Class[]{String.class};
    private final String tableName = "users";

    @Override
    public int create(User item)
    {
        return sqlExecutor.insertTable(tableName, columnsWithoutId, columnsWithoutId, new Object[]{item.getUsername(), item.getPassword()});
    }

    @Override
    public User read(int id)
    {
        return (User) sqlExecutor.selectColumnsById(tableName, columns, id, new UserMapperImpl());
    }

    @Override
    public int getCount()
    {
        return sqlExecutor.getCountFromTable(tableName);
    }

    @Override
    public List<User> read(String[] conditionColumns, Object[] objects)
    {
        return sqlExecutor.selectColumnsByCondition(tableName, columns, conditionColumns, conditionColumns, objects, new UserMapperImpl());
    }

    @Override
    public User readByCondition(String[] columns, String conditionColumn, Object conditionObjects)
    {
        return (User) sqlExecutor.selectColumnsByColumn(getTableName(), columns, conditionColumn, conditionObjects, new UserMapperImpl());
    }

    @Override
    public List<User> readAll()
    {
        return sqlExecutor.selectAllColumns(tableName, columns, new UserMapperImpl());
    }

    @Override
    public int updateById(User item, int id)
    {
        return sqlExecutor.updateTable(tableName, columnsWithoutId, columnsWithoutId, new Object[]{item.getUsername(), item.getPassword()}, new String[]{"id"}, new String[]{"id"}, new Object[]{id});
    }

    @Override
    public int delete(User item)
    {
        return sqlExecutor.deleteFromTable(tableName, columnsWithoutPassword, columnsWithoutPassword, new Object[]{item.getId(),item.getUsername()});
    }

    @Override
    public int deleteByID(int id)
    {
        return sqlExecutor.deleteFromTable(tableName, new String[]{"id"}, new String[]{"id"}, new Object[]{id});
    }

    @Override
    public int deleteWithoutID(User item)
    {
        return sqlExecutor.deleteFromTable(tableName, columnsWithoutIdPassword, columnsWithoutIdPassword, new Object[]{item.getUsername()});
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
