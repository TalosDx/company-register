package ru.aisa.companyregister;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.CompanyGenericDAOImpl;
import ru.aisa.companyregister.database.dao.EmployeeGenericDAOImpl;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.database.dao.entities.Company;
import ru.aisa.companyregister.database.dao.entities.Employee;
import ru.aisa.companyregister.ui.AbstractPopUpController;
import ru.aisa.companyregister.ui.CompaniesPopUpControllerImpl;
import ru.aisa.companyregister.ui.EmployeePopUpControllerImpl;
import ru.aisa.companyregister.utils.LazyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CompanyRegister extends UI
{
    private final VerticalLayout ownContent = new VerticalLayout();
    private final TabSheet contentTabs = new TabSheet();
    private final GenericDAO companiesDAO = new CompanyGenericDAOImpl();
    private final GenericDAO employeeDAO = new EmployeeGenericDAOImpl();
    private BeanItemContainer<Employee> employeeBeanItemContainer;
    private BeanItemContainer<Company> companyBeanItemContainer;
    private Grid employeeGrid;
    private Grid companyGrid;
    private TabSheet.Tab employeeTab;
    private TabSheet.Tab companyTab;
    private Button addItem;
    private Button editItem;
    private Button deleteItem;

    @Override
    protected void init(VaadinRequest request)
    {
        this.getPage().setTitle("Реестр компании и сотрудников");
        setContent(ownContent);
        final HorizontalLayout buttonsLayout = new HorizontalLayout();
        addItem = new Button(LazyUtils.getLangProperties("button.add"));
        editItem = new Button(LazyUtils.getLangProperties("button.edit"));
        editItem.setEnabled(false);
        deleteItem = new Button(LazyUtils.getLangProperties("button.delete"));
        deleteItem.setEnabled(false);
        buttonsLayout.addComponents(addItem, editItem, deleteItem);
        buttonsLayout.setSpacing(true);
        ownContent.addComponent(buttonsLayout);
        ownContent.addComponent(contentTabs);
        final VerticalLayout companiesLayout = new VerticalLayout();
        final VerticalLayout employeeLayout = new VerticalLayout();
        buttonsLayout.setSpacing(true);
        ownContent.setSpacing(true);
        contentTabs.setSizeFull();


        companyTab = contentTabs.addTab(companiesLayout, "Компании");
        employeeTab = contentTabs.addTab(employeeLayout, "Сотрудники");
        contentTabs.addSelectedTabChangeListener(event ->
        {
            companyGrid.deselectAll();
            employeeGrid.deselectAll();
            editItem.setEnabled(false);
            deleteItem.setEnabled(false);
        });

        companyGrid = createCompaniesGrid();
        companiesLayout.addComponent(companyGrid);
        employeeGrid = createEmployeesGrid();
        employeeLayout.addComponent(employeeGrid);

        addItem.addClickListener(event -> actionAddItem());
        editItem.addClickListener(event -> actionEditItem());
        deleteItem.addClickListener(event -> actionDeleteItem());

    }


    public void updateGrids()
    {
        employeeBeanItemContainer.removeAllItems();
        employeeBeanItemContainer.addAll(employeeDAO.readAll());
        companyBeanItemContainer.removeAllItems();
        companyBeanItemContainer.addAll(companiesDAO.readAll());
    }

    /**
     * @return возвращает созданный грид для сотрудников
     */
    private Grid createEmployeesGrid()
    {
        ArrayList<Employee> employees = (ArrayList<Employee>) employeeDAO.readAll();
        employeeBeanItemContainer = new BeanItemContainer<>(Employee.class, employees);
        Grid grid = new Grid(employeeBeanItemContainer);
        sortRename(grid, Arrays.asList("id", "fullName", "birthday", "email", "companyName"));
        checkGridSelectable(grid, employeeTab);
        grid.getColumn("id").setHidden(true);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setSizeFull();
        grid.setImmediate(true);
        return grid;
    }

    private void checkGridSelectable(Grid grid, TabSheet.Tab tab)
    {
        grid.addSelectionListener(event ->
        {
            if (Objects.equals(contentTabs.getTab(contentTabs.getSelectedTab()), tab))
                if (grid.getSelectedRow() != null)
                {
                    editItem.setEnabled(true);
                    deleteItem.setEnabled(true);
                }
                else
                {
                    editItem.setEnabled(false);
                    deleteItem.setEnabled(false);
                }
        });
    }

    private void sortRename(Grid grid, List<String> orderList)
    {
        for (int i = orderList.size() - 1; i > 0; i--)
        {
            grid.setColumnOrder(orderList.get(i));
            grid.getColumn(orderList.get(i)).setHeaderCaption(LazyUtils.getLangProperties(orderList.get(i)));
        }
    }

    /**
     * @return возвращает созданный грид для компаний
     */
    private Grid createCompaniesGrid()
    {
        ArrayList<Company> companies = (ArrayList<Company>) companiesDAO.readAll();
        companyBeanItemContainer = new BeanItemContainer<>(Company.class, companies);
        Grid grid = new Grid(companyBeanItemContainer);
        checkGridSelectable(grid, companyTab);
        sortRename(grid, Arrays.asList("id", "companyName", "inn", "address", "phone"));
        grid.getColumn("id").setHidden(true);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setSizeFull();
        grid.setImmediate(true);
        return grid;
    }

    private Window actionAddItem()
    {
        AbstractPopUpController controller;
        String str;
        Window window = null;

        if (Objects.equals(contentTabs.getTab(contentTabs.getSelectedTab()), companyTab))
        {
            str = "Добавить компанию";
            controller = new CompaniesPopUpControllerImpl(companiesDAO, companyBeanItemContainer, employeeDAO);
            window = getActionAddItem(controller, str);
        }
        if (Objects.equals(contentTabs.getTab(contentTabs.getSelectedTab()), employeeTab))
        {
            str = "Добавить сотрудника";
            controller = new EmployeePopUpControllerImpl(employeeDAO, employeeBeanItemContainer, companiesDAO);
            window = getActionAddItem(controller, str);
        }
        if (window != null)
        {
            this.addWindow(window);
            window.setModal(true);
        }
        return window;
    }

    private void actionEditItem()
    {
        AbstractPopUpController controller;
        String str;
        Window window = null;

        if (contentTabs.getTab(contentTabs.getSelectedTab()) != null)
        {
            if (Objects.equals(contentTabs.getTab(contentTabs.getSelectedTab()), companyTab) && companyGrid.getSelectedRow() != null)
            {
                str = "Редактировать компанию";
                controller = new CompaniesPopUpControllerImpl(companiesDAO, companyBeanItemContainer, employeeDAO);
                window = getActionEditItem(controller, str, companyGrid);
            }
            if (Objects.equals(contentTabs.getTab(contentTabs.getSelectedTab()), employeeTab) && employeeGrid.getSelectedRow() != null)
            {
                str = "Редактировать сотрудника";
                controller = new EmployeePopUpControllerImpl(employeeDAO, employeeBeanItemContainer, companiesDAO);
                window = getActionEditItem(controller, str, employeeGrid);
            }
        }
        if (window != null)
        {
            this.addWindow(window);
            window.setModal(true);
        }

    }

    private void actionDeleteItem()
    {
        AbstractPopUpController controller;
        String str;
        Window window = null;

        if (contentTabs.getTab(contentTabs.getSelectedTab()) != null)
        {
            if (Objects.equals(contentTabs.getTab(contentTabs.getSelectedTab()), companyTab) && companyGrid.getSelectedRow() != null)
            {
                str = "Удалить компанию";
                controller = new CompaniesPopUpControllerImpl(companiesDAO, companyBeanItemContainer, employeeDAO);
                window = getActionDeleteItem(controller, str, companyGrid);
            }
            if (Objects.equals(contentTabs.getTab(contentTabs.getSelectedTab()), employeeTab) && employeeGrid.getSelectedRow() != null)
            {
                str = "Удалить сотрудника";
                controller = new EmployeePopUpControllerImpl(employeeDAO, employeeBeanItemContainer, companiesDAO);
                window = getActionDeleteItem(controller, str, employeeGrid);
            }
        }
        if (window != null)
        {
            this.addWindow(window);
            window.setModal(true);
        }

    }

    private Window createWindow(String str)
    {
        Window item = new Window(str);
        item.setResizable(false);
        item.setWidth(250, Unit.PIXELS);
        item.setHeight(300, Unit.PIXELS);
        item.setImmediate(true);
        return item;
    }

    private Window getActionAddItem(AbstractPopUpController controller, String str)
    {
        Window window;
        window = createWindow(str);
        VerticalLayout verticalLayout = new VerticalLayout();
        window.setContent(verticalLayout);
        controller.init(window);
        controller.displayAddItem(window);
        return window;
    }

    private Window getActionEditItem(AbstractPopUpController controller, String str, Grid grid)
    {
        Window window;
        window = createWindow(str);
        VerticalLayout verticalLayout = new VerticalLayout();
        window.setContent(verticalLayout);
        controller.init(window);
        controller.displayEditItem(window, grid.getSelectedRow());
        grid.deselectAll();
        return window;
    }

    private Window getActionDeleteItem(AbstractPopUpController controller, String str, Grid grid)
    {
        Window window = createWindow(str);
        VerticalLayout verticalLayout = new VerticalLayout();
        window.setContent(verticalLayout);
        controller.init(window);
        controller.displayDeleteItem(window, grid.getSelectedRow());
        grid.deselectAll();
        return window;
    }
}
