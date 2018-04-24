package com.aisa.companyregister;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;

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
