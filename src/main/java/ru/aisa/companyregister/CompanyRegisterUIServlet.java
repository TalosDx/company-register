package ru.aisa.companyregister;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import ru.aisa.companyregister.dao.DBConnector;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/*", name = "CompanyRegisterUIServlet", asyncSupported = true, displayName = "CompanyRegister")
@VaadinServletConfiguration(ui = CompanyRegister.class, productionMode = false)
public class CompanyRegisterUIServlet extends VaadinServlet
{
    static
    {
        DBConnector.createTable();
    }
}