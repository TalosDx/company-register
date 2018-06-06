package ex.talosdx.companyregister;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import ex.talosdx.companyregister.ui.pages.LoginPage;
import ex.talosdx.companyregister.ui.pages.MainPage;

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
