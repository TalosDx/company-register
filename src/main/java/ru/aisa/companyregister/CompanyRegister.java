package ru.aisa.companyregister;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import ru.aisa.companyregister.dao.wrapper.enties.CompanyGenericDAOImpl;
import ru.aisa.companyregister.dao.wrapper.enties.EmployeeGenericDAOImpl;
import ru.aisa.companyregister.dao.wrapper.enties.GenericDAO;
import ru.aisa.companyregister.dao.entities.Company;
import ru.aisa.companyregister.dao.entities.Employee;
import ru.aisa.companyregister.dao.entities.User;
import ru.aisa.companyregister.ui.pages.LoginPage;
import ru.aisa.companyregister.ui.pages.MainPage;
import ru.aisa.companyregister.ui.popup.AbstractPopUpController;
import ru.aisa.companyregister.ui.popup.CompaniesPopUpControllerImpl;
import ru.aisa.companyregister.ui.popup.EmployeePopUpControllerImpl;
import ru.aisa.companyregister.utils.LazyUtils;

import java.util.*;
import java.util.stream.Collectors;

@Theme("valo")
public class CompanyRegister extends UI
{
    @Override
    protected void init(VaadinRequest request)
    {
        this.getPage().setTitle("Реестр компании и сотрудников");
        new Navigator(this, this);
        getNavigator().addView(LoginPage.NAME, LoginPage.class);
        getNavigator().setErrorView(LoginPage.class);
        Page.getCurrent().addUriFragmentChangedListener((Page.UriFragmentChangedListener) event ->
                router());

        router();
    }

    private void router()
    {
        if (getSession().getAttribute("user") != null)
        {
            getNavigator().addView(MainPage.NAME, MainPage.class);
                getNavigator().navigateTo(MainPage.NAME);
        }
        else
        {
            getNavigator().navigateTo(LoginPage.NAME);
        }
    }

}
