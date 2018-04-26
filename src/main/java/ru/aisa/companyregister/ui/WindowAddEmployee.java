package ru.aisa.companyregister.ui;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import ru.aisa.companyregister.database.DBConnector;
import ru.aisa.companyregister.utils.LazyUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.vaadin.ui.Alignment.TOP_LEFT;
import static com.vaadin.ui.Alignment.TOP_RIGHT;

public class WindowAddEmployee extends WindowController
{
    final Window window = new Window(LazyUtils.getLangProperties("window.add.coworker"));
    final FormLayout content = new FormLayout();
    HashMap<String, String> fields = new HashMap<>();
    ArrayList<Label> labels = new ArrayList<>();
    ArrayList<TextField> textFields = new ArrayList<>();
    private Window parent;
    AbstractWindowConfirmation windowConfirmation = new WindowConfirmation(getWindow());
    private UI ui;
    private boolean isInit;

    @Override
    public Window getWindow()
    {
        return window;
    }

    @Override
    public void init(UI ui)
    {
        this.ui = ui;
        this.window.center();
        window.setWidth(20, Sizeable.Unit.PERCENTAGE);
        window.setHeight(25, Sizeable.Unit.PERCENTAGE);
        window.setClosable(false);
        window.setResizable(false);
        content.setMargin(true);
        window.setContent(content);

    }

    @Override
    public void drawWindow() throws SQLException
    {
        ResultSet resultSet = null;//DBConnector.selectTable(DBConnector.COMPANIES_TABLE);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        //Начинается с 2, потому что первое поле id и оно ни к чему для заполнения пользователем
        WindowAddCompany.getDataFromTable(resultSetMetaData, fields, labels, this.textFields, content);
        Button buttonCancel = makeButtonWindow("windows.add.coworker.button.cancel", event ->
        {
            try
            {
                windowConfirmation.setCancelOperation(true);
                windowConfirmation.init(ui);
                windowConfirmation.drawWindow();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            ui.addWindow(windowConfirmation.getWindow());
        });
        Button buttonDone = makeButtonWindow("windows.add.coworker.button.add", event ->
        {
            try
            {
                windowConfirmation.init(ui);
                windowConfirmation.drawWindow();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            ui.addWindow(windowConfirmation.getWindow());
        });
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponents(buttonCancel, buttonDone);
        layout.setSpacing(true);
        content.addComponents(layout);
    }

    @Override
    void setParent(Window parent)
    {
        this.parent = parent;
    }

    @Override
    Window getParent()
    {
        return this.parent;
    }

    @Override
    public boolean isInit()
    {
        return isInit;
    }

    public AbstractWindowConfirmation getWindowConfirmation()
    {
        return windowConfirmation;
    }

    public void setWindowConfirmation(AbstractWindowConfirmation windowConfirmation)
    {
        this.windowConfirmation = windowConfirmation;
    }
}