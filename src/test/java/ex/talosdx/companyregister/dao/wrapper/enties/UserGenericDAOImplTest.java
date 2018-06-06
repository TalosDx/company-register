package ex.talosdx.companyregister.dao.wrapper.enties;

import ex.talosdx.companyregister.dao.entities.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

class UserGenericDAOImplTest
{

    GenericDAO sqlExecutor = new UserGenericDAOImpl();
    private final User user2 = new User("user", "password");
    private final User user = new User("daomaou1", "asfasawdf124");
    private final User user1 = new User("daomaou1", "awgawgagw");
    private final User user3 = new User("daomaou", "asfasawdf124");
    private final User user4 = new User("daomaou4", "asfasawdf12411");

    @Test
    public void create()
    {
        sqlExecutor.create(user2);
    }

    @Test
    public void read()
    {
        System.out.println(sqlExecutor.read(1).toString());
    }

    @Test
    public void getCount()
    {
        System.out.println(sqlExecutor.getCount());
    }

    @Test
    public void readAll()
    {
        List list = sqlExecutor.readAll();
        for(Object user : list)
        System.out.println(user.toString());
    }

    @Test
    public void updateById()
    {
        sqlExecutor.updateById(user1, 3);
    }

    @Test
    public void delete()
    {
        sqlExecutor.delete(user2);
    }

    @Test
    public void deleteByID()
    {
        sqlExecutor.deleteByID(5);
    }

    @Test
    public void deleteWithoutID()
    {
        sqlExecutor.deleteWithoutID(user3);
    }
}