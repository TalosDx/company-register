package ru.aisa.companyregister;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import ru.aisa.companyregister.database.DBConnector;
import ru.aisa.companyregister.ui.WindowController;
import ru.aisa.companyregister.ui.WindowAddCompany;

import java.sql.SQLException;

public class CompanyRegister extends UI
{
    final WindowController window = new WindowAddCompany();


    @Override
    protected void init(VaadinRequest request)
    {
        final VerticalLayout layout = new VerticalLayout();
        DBConnector.ConnectToDb();
        setContent(layout);
        try
        {
            window.init(this);
            window.drawWindow();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        createButton(layout);
    }

    private void createButton(VerticalLayout layout)
    {
        Button button = new Button();
        layout.addComponent(button);
        layout.setComponentAlignment(button, Alignment.TOP_CENTER);
        button.addClickListener(event ->
        {
            if(!this.getWindows().contains(window.getWindow()))
            this.addWindow(window.getWindow());
        });
    }
}
