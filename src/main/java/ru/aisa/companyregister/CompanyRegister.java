package ru.aisa.companyregister;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.aisa.companyregister.utils.LazyUtils;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Properties;

public class CompanyRegister extends UI
{
    @Override
    protected void init(VaadinRequest request)
    {
        final VerticalLayout layout = new VerticalLayout();
        Button button = new Button();
        layout.addComponent(button);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "CompanyRegisterUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CompanyRegister.class, productionMode = false)
    public static class CompanyRegisterUIServlet extends VaadinServlet
    {
    }
}
