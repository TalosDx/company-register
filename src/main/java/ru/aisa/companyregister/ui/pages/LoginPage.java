package ru.aisa.companyregister.ui.pages;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.aisa.companyregister.CompanyRegister;
import ru.aisa.companyregister.dao.entities.User;
import ru.aisa.companyregister.dao.wrapper.enties.GenericDAO;
import ru.aisa.companyregister.dao.wrapper.enties.UserGenericDAOImpl;
import ru.aisa.companyregister.ui.validators.UsersValidatorMapperImpl;
import ru.aisa.companyregister.utils.LazyUtils;

import java.util.Objects;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class LoginPage extends VerticalLayout implements View
{

    public static final String NAME = "login";
    GenericDAO sqlExecutor = new UserGenericDAOImpl();
    VerticalLayout ownContent = new VerticalLayout();
    UsersValidatorMapperImpl usersValidatorMapper = new UsersValidatorMapperImpl();

    public LoginPage()
    {
        init();
    }

    public void init()
    {
        Panel panel = new Panel();
        panel.setSizeUndefined();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(panel);

        FormLayout content = new FormLayout();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        panel.setContent(content);


        Label label = new Label("Авторизация");
        content.addComponent(label);

        TextField username = new TextField("Логин");
        username.addValidator(usersValidatorMapper.getValidatorFromColumn("username"));
        content.addComponent(username);


        PasswordField passwordField = new PasswordField("Пароль");
        passwordField.addValidator(usersValidatorMapper.getValidatorFromColumn("password"));
        content.addComponent(passwordField);

        Button loginButton = new Button("Вход");
        content.addComponent(loginButton);
        loginButton.addClickListener(event1 ->
        {
            if (username.isValid() && passwordField.isValid())
            {
                User obj = null;
                try
                {
                    obj = (User) sqlExecutor.readByCondition(sqlExecutor.getTableColumns(), "username", username.getValue());
                }
                catch (EmptyResultDataAccessException e)
                {
                    Notification.show("Неправильный логин или пароль", Notification.Type.ERROR_MESSAGE);
                }
                User user = new User(username.getValue(), passwordField.getValue());
                if (obj != null && user.getUsername().equals(obj.getUsername()) && user.getPassword().equals(obj.getPassword()))
                    {
                        VaadinSession.getCurrent().setAttribute("user", username.getValue());
                        getUI().getNavigator().addView(MainPage.NAME, MainPage.class);
                        Page.getCurrent().setUriFragment("!"+MainPage.NAME);
                    }
                    else
                    {
                        Notification.show("Неправильный логин или пароль", Notification.Type.ERROR_MESSAGE);
                    }
            }
            else LazyUtils.showErrorWithFields(new Field[]{username, passwordField});
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {

    }

}
