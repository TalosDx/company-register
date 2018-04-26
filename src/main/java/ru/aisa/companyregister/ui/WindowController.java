package ru.aisa.companyregister.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import ru.aisa.companyregister.utils.LazyUtils;

import java.sql.SQLException;

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
