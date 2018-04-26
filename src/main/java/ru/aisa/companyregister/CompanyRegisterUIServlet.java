package ru.aisa.companyregister;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/*", name = "CompanyRegisterUIServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = CompanyRegister.class, productionMode = false)
public class CompanyRegisterUIServlet extends VaadinServlet
{

}