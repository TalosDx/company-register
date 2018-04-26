package ru.aisa.companyregister.ui;

import com.vaadin.ui.*;
import ru.aisa.companyregister.utils.LazyUtils;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.vaadin.ui.Alignment.TOP_LEFT;
import static com.vaadin.ui.Alignment.TOP_RIGHT;

public abstract class WindowController
{
    public abstract com.vaadin.ui.Window getWindow();

    public abstract void init(UI ui) throws SQLException;

    public abstract void drawWindow() throws SQLException;

    abstract void setParent(Window parent);
    abstract Window getParent();

    public abstract boolean isInit();

    static void getDataFromTable(ResultSetMetaData resultSetMetaData, HashMap<String, String> fields, ArrayList<Label> labels, ArrayList<TextField> textFields, FormLayout content) throws SQLException
    {
        for (int i = 2; i <= resultSetMetaData.getColumnCount(); i++)
        {
            fields.put(resultSetMetaData.getColumnName(i), resultSetMetaData.getColumnTypeName(i));
            Label label = new Label(LazyUtils.getLangProperties(resultSetMetaData.getColumnName(i)));
            TextField textField = new TextField();
            textField.setMaxLength(30); //TODO ДОПИЛИТЬ и получить тип и относительно типа запилить орг. символов, для Char юзать getCount или около того
            HorizontalLayout hLayout = new HorizontalLayout(label, textField); //TODO поправить сиё из цикла вон
            hLayout.setWidth("100%");
            hLayout.setComponentAlignment(label, TOP_LEFT);
            hLayout.setComponentAlignment(textField, TOP_RIGHT);
            labels.add(label);
            textFields.add(textField);
            content.addComponent(hLayout);
        }
    }

    protected Button makeButtonWindow(String s, Button.ClickListener buttonClickListener)
    {
        Button buttonCancel = new Button(LazyUtils.getLangProperties(s));
        buttonCancel.addClickListener(buttonClickListener);
        return buttonCancel;
    }
}
