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

    protected Button makeButtonWindow(String s, Button.ClickListener buttonClickListener)
    {
        Button buttonCancel = new Button(LazyUtils.getLangProperties(s));
        buttonCancel.addClickListener(buttonClickListener);
        return buttonCancel;
    }
}
