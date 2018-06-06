package ex.talosdx.companyregister.ui.pages;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import ex.talosdx.companyregister.dao.entities.Company;
import ex.talosdx.companyregister.dao.entities.Employee;
import ex.talosdx.companyregister.dao.wrapper.enties.CompanyGenericDAOImpl;
import ex.talosdx.companyregister.dao.wrapper.enties.EmployeeGenericDAOImpl;
import ex.talosdx.companyregister.dao.wrapper.enties.GenericDAO;
import ex.talosdx.companyregister.ui.popup.AbstractPopUpController;
import ex.talosdx.companyregister.ui.popup.CompaniesPopUpControllerImpl;
import ex.talosdx.companyregister.ui.popup.EmployeePopUpControllerImpl;
import ex.talosdx.companyregister.utils.LazyUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MainPage extends VerticalLayout implements View
{
    public static final String NAME = "home";
    private final TabSheet contentTabs = new TabSheet();
    private final GenericDAO companiesDAO = new CompanyGenericDAOImpl();
    private final GenericDAO employeeDAO = new EmployeeGenericDAOImpl();
    public ArrayList<Employee> employees;
    public ArrayList<Company> companies;
    public BeanItemContainer<Employee> employeeBeanItemContainer;
    public BeanItemContainer<Company> companyBeanItemContainer;
    public Map<Integer, String> companyNames;

    private Grid employeeGrid;
    private Grid companyGrid;
    private TabSheet.Tab employeeTab;
    private TabSheet.Tab companyTab;
    private Button addItem;
    private Button editItem;
    private Button deleteItem;


    public MainPage()
    {
        init();
    }

    public void init()
    {
        VerticalLayout ownContent = new VerticalLayout();
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
        ownContent.setSpacing(true);
        ownContent.setSizeFull();


        companyTab = contentTabs.addTab(companiesLayout, "Компании");
        employeeTab = contentTabs.addTab(employeeLayout, "Сотрудники");
        contentTabs.addSelectedTabChangeListener(selectedTabChangeEvent ->
        {
            companyGrid.deselectAll();
            employeeGrid.deselectAll();
            editItem.setEnabled(false);
            deleteItem.setEnabled(false);
        });

        employees = (ArrayList<Employee>) employeeDAO.readAll();
        companies = (ArrayList<Company>) companiesDAO.readAll();
        companyNames = companies.stream().collect(Collectors.toMap(Company::getId, Company::getCompanyName));

        companyGrid = createCompaniesGrid();
        companiesLayout.addComponent(companyGrid);
        employeeGrid = createEmployeesGrid();
        employeeLayout.addComponent(employeeGrid);

        addItem.addClickListener(clickEvent -> actionAddItem());
        editItem.addClickListener(clickEvent -> actionEditItem());
        deleteItem.addClickListener(clickEvent -> actionDeleteItem());

        this.addComponent(ownContent);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent)
    {
    }




    public void updateGrids()
    {
        employees = (ArrayList<Employee>) employeeDAO.readAll();
        companies = (ArrayList<Company>) companiesDAO.readAll();
        employeeBeanItemContainer.removeAllItems();
        employeeBeanItemContainer.addAll(employees);
        companyBeanItemContainer.removeAllItems();
        companyBeanItemContainer.addAll(companies);
        companyNames = companies.stream().collect(Collectors.toMap(Company::getId, Company::getCompanyName));
    }

    /**
     * @return возвращает созданный грид для сотрудников
     */
    private Grid createEmployeesGrid()
    {
        employeeBeanItemContainer = new BeanItemContainer<>(Employee.class, employees);
        GeneratedPropertyContainer gContainer = new GeneratedPropertyContainer(employeeBeanItemContainer);

        gContainer.addGeneratedProperty("companyName", new PropertyValueGenerator<String>()
        {
            @Override
            public String getValue(Item item, Object itemId, Object propertyId)
            {
                return companyNames.get(item.getItemProperty("companyId").getValue());
            }

            @Override
            public Class<String> getType()
            {
                return String.class;
            }
        });
        Grid grid = new Grid(gContainer);
        sortRename(grid, Arrays.asList("id", "fullName", "birthday", "email", "companyId", "companyName"));
        checkGridSelectable(grid, employeeTab);
        grid.getColumn("id").setHidden(true);
        grid.getColumn("companyId").setHidden(true);
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
            controller = new CompaniesPopUpControllerImpl(companiesDAO, employeeDAO, this);
            controller.registerBeanItemContainer(employeeBeanItemContainer, employeeDAO);
            window = getActionAddItem(controller, str);
        }
        if (Objects.equals(contentTabs.getTab(contentTabs.getSelectedTab()), employeeTab))
        {
            str = "Добавить сотрудника";
            controller = new EmployeePopUpControllerImpl(employeeDAO, companiesDAO, this);
            controller.registerBeanItemContainer(companyBeanItemContainer, companiesDAO);
            window = getActionAddItem(controller, str);
        }
        if (window != null)
        {
            getUI().addWindow(window);
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
                controller = new CompaniesPopUpControllerImpl(companiesDAO, employeeDAO, this);
                controller.registerBeanItemContainer(employeeBeanItemContainer, employeeDAO);
                window = getActionEditItem(controller, str, companyGrid);
            }
            if (Objects.equals(contentTabs.getTab(contentTabs.getSelectedTab()), employeeTab) && employeeGrid.getSelectedRow() != null)
            {
                str = "Редактировать сотрудника";
                controller = new EmployeePopUpControllerImpl(employeeDAO, companiesDAO, this);
                controller.registerBeanItemContainer(companyBeanItemContainer, companiesDAO);
                window = getActionEditItem(controller, str, employeeGrid);
            }
        }
        if (window != null)
        {
            getUI().addWindow(window);
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
                controller = new CompaniesPopUpControllerImpl(companiesDAO, employeeDAO, this);
                controller.registerBeanItemContainer(employeeBeanItemContainer, employeeDAO);
                window = getActionDeleteItem(controller, str, companyGrid);
            }
            if (Objects.equals(contentTabs.getTab(contentTabs.getSelectedTab()), employeeTab) && employeeGrid.getSelectedRow() != null)
            {
                str = "Удалить сотрудника";
                controller = new EmployeePopUpControllerImpl(employeeDAO, companiesDAO, this);
                controller.registerBeanItemContainer(companyBeanItemContainer, companiesDAO);
                window = getActionDeleteItem(controller, str, employeeGrid);
            }
        }
        if (window != null)
        {
            getUI().addWindow(window);
            window.setModal(true);
        }

    }

    private Window createWindow(String str)
    {
        Window item = new Window(str);
        item.setResizable(false);
        item.setWidth(350, Unit.PIXELS);
        item.setHeight(450, Unit.PIXELS);
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
