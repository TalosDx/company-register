package ex.talosdx.companyregister;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import ex.talosdx.companyregister.dao.DBConnector;
import ex.talosdx.companyregister.dao.entities.User;
import ex.talosdx.companyregister.dao.wrapper.enties.UserGenericDAOImpl;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/*", name = "CompanyRegisterUIServlet", asyncSupported = true, displayName = "CompanyRegister")
@VaadinServletConfiguration(ui = CompanyRegister.class, productionMode = false)
public class CompanyRegisterUIServlet extends VaadinServlet
{
    static int count;
    static
    {
        DBConnector.createTable();
        UserGenericDAOImpl sqlExecutor = new UserGenericDAOImpl();
        count = sqlExecutor.getCount();
        if(count >= 0 && count < 1)
        {
            sqlExecutor.create(new User("user", "password"));
        }
    
    }
}