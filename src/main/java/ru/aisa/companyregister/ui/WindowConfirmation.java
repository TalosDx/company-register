package ru.aisa.companyregister.ui;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import ru.aisa.companyregister.utils.LazyUtils;

import java.sql.SQLException;

//TODO поискать реализацию по лучше, нежели, чем эта(через Window)
public class WindowConfirmation extends AbstractWindowConfirmation
{
    private Window window;
    private Layout content = new VerticalLayout();
    private Window parent;
    HorizontalLayout popLayout = new HorizontalLayout();
    private boolean isInit;
    private boolean isCancelOperation = false;

    public WindowConfirmation(Window parent)
    {
        window = new Window();
        this.parent = parent;
    }

    @Override
    public Window getWindow()
    {
        if(window == null)
        throw new IllegalStateException("May produce null, when method init(UI ui) not run, because window == null");
        return this.window;
    }

    @Override
    public void init(UI ui) throws SQLException
    {
        if (isCancelOperation)
            window.setCaption(LazyUtils.getLangProperties("windows.add.company.confirmation.popup.cancel"));
        else window.setCaption(LazyUtils.getLangProperties("windows.add.company.confirmation.popup"));

        if (!isInit)
        {
            this.getWindow().setModal(true);
            this.window.setContent(content);
            content.addComponent(popLayout);
            window.setWidth(20f, Sizeable.Unit.PERCENTAGE);
            window.setHeight(20f, Sizeable.Unit.PERCENTAGE);
            this.window.setClosable(false);
            this.window.setResizable(false);
            Button buttonPopupCancel = makeButtonWindow("windows.add.company.confirmation.popup.button.cancel", event1 ->
                    window.close());
            popLayout.addComponent(buttonPopupCancel);
            Button buttonPopupEnter = makeButtonWindow("windows.add.company.confirmation.popup.button.enter", event1 ->
            {
                parent.setReadOnly(false);
                this.parent.close();
                window.close();
            });
            popLayout.addComponent(buttonPopupEnter);
            isInit = true;
        }
    }

    @Override
    public void drawWindow() throws SQLException
    {

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
        return this.isInit;
    }

    @Override
    public boolean isCancelOperation()
    {
        return this.isCancelOperation;
    }

    @Override
    public void setCancelOperation(Boolean cancel)
    {
        this.isCancelOperation = cancel;
    }

    @Override
    public boolean setShowData(Table table)
    {
        return false;
    }
}
