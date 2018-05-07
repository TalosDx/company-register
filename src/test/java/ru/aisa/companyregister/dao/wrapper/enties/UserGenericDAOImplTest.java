package ru.aisa.companyregister.dao.wrapper.enties;

import org.junit.jupiter.api.Test;
import ru.aisa.companyregister.dao.entities.User;

import java.util.List;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

class UserGenericDAOImplTest
{

    GenericDAO sqlExecutor = new UserGenericDAOImpl();
    private final User user = new User("daomaou1", "asfasawdf124");
    private final User user1 = new User("daomaou1", "awgawgagw");
    private final User user2 = new User("user", "asfasawdf124");
    private final User user3 = new User("daomaou", "asfasawdf124");
    private final User user4 = new User("daomaou4", "asfasawdf12411");

    @Test
    void create()
    {
        sqlExecutor.create(user4);
    }

    @Test
    void read()
    {
        System.out.println(sqlExecutor.read(1).toString());
    }

    @Test
    void getCount()
    {
        System.out.println(sqlExecutor.getCount());
    }

    @Test
    void readAll()
    {
        List list = sqlExecutor.readAll();
        for(Object user : list)
        System.out.println(user.toString());
    }

    @Test
    void updateById()
    {
        sqlExecutor.updateById(user1, 3);
    }

    @Test
    void delete()
    {
        sqlExecutor.delete(user2);
    }

    @Test
    void deleteByID()
    {
        sqlExecutor.deleteByID(5);
    }

    @Test
    void deleteWithoutID()
    {
        sqlExecutor.deleteWithoutID(user3);
    }
}