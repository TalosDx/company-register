package ru.aisa.companyregister;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.CompanyGenericDAOImpl;
import ru.aisa.companyregister.database.dao.EmployeeGenericDAOImpl;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.database.dao.entities.Company;
import ru.aisa.companyregister.database.dao.entities.Employee;
import ru.aisa.companyregister.ui.AbstractPopUpController;
import ru.aisa.companyregister.ui.CompaniesPopUpControllerImpl;
import ru.aisa.companyregister.utils.LazyUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanyRegister extends UI
{
    private AbstractPopUpController controller;
    private final VerticalLayout ownContent = new VerticalLayout();
    private Grid grid = new Grid();
    private TabSheet contentTabs = new TabSheet();
    private GenericDAO genericDAO;
    private GenericDAO companiesDAO = new CompanyGenericDAOImpl();
    private GenericDAO employeeDAO = new EmployeeGenericDAOImpl();

    @Override
    protected void init(VaadinRequest request)
    {
        this.getPage().setTitle("Реестр компании и сотрудников");
        setContent(ownContent);
        final HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button addItem = new Button();
        Button editItem = new Button();
        Button deleteItem = new Button();
        buttonsLayout.addComponents(addItem, editItem, deleteItem);
        ownContent.addComponent(buttonsLayout);
        ownContent.addComponent(contentTabs);
        final VerticalLayout companiesLayout = new VerticalLayout();
        Grid companyGrid = createCompaniesGrid();
        companiesLayout.addComponent(companyGrid);
        final VerticalLayout employeeLayout = new VerticalLayout();
        Grid employeeGrid = createEmployeesGrid();
        employeeLayout.addComponent(employeeGrid);
        buttonsLayout.setSpacing(true);

        contentTabs.setSizeFull();

        contentTabs.addTab(companiesLayout, "Компании");
        contentTabs.addTab(employeeLayout, "Сотрудники");


        contentTabs.addSelectedTabChangeListener(selectedTabChangeEvent ->
        {
            if (Objects.equals(contentTabs.getSelectedTab().getCaption(), "Компании"))
            {
                genericDAO = new CompanyGenericDAOImpl();
                controller.init(ownContent);
                controller = new CompaniesPopUpControllerImpl(genericDAO, new EmployeeGenericDAOImpl());
                grid = LazyUtils.createGrid(new String[]{"company_name", "inn", "address", "phone"}, new Class<?>[]{String.class, Integer.class, String.class, String.class});
                addItem.addClickListener(event -> actionAddItem(controller));
                editItem.addClickListener(event -> actionEditItem(controller));
                deleteItem.addClickListener(event -> actionDeleteItem(controller));
            }
            if (Objects.equals(contentTabs.getSelectedTab().getCaption(), "Сотрудники"))
            {
                genericDAO = new EmployeeGenericDAOImpl();
                controller.init(ownContent);
                //controller = new EmployeePopUpControllerImpl(genericDAO, (new CompanyGenericDAOImpl).read);
                grid = LazyUtils.createGrid(new String[]{"full_name", "birthday", "email", "company_name"}, new Class<?>[]{String.class, Date.class, String.class, String.class});
                int size = genericDAO.getCount();

                addItem.addClickListener(event -> actionAddItem(controller));
                editItem.addClickListener(event -> actionEditItem(controller));
                deleteItem.addClickListener(event -> actionDeleteItem(controller));
            }
        });
    }


    /**
     * @return возвращает созданный грид для сотрудников
     */
    private Grid createEmployeesGrid()
    {
        Grid grid = createGrid(employeeDAO.getTableColumnsWithoutId(), employeeDAO.getTableTypes());
        ArrayList<Employee> employees = (ArrayList<Employee>) employeeDAO.readAll();
        for (Employee employee : employees)
            injectDataToGrid(grid, new Object[]{employee.getFullName(), employee.getBirthday(), employee.getEmail(), employee.getCompanyName()});
        return grid;
    }

    /**
     * @return возвращает созданный грид для компаний
     */
    private Grid createCompaniesGrid()
    {
        Grid grid = createGrid(companiesDAO.getTableColumnsWithoutId(), companiesDAO.getTableTypes());
        ArrayList<Company> companies = (ArrayList<Company>) companiesDAO.readAll();
        for (Company company : companies)
            injectDataToGrid(grid, new Object[]{company.getCompanyName(), company.getInn(), company.getAddress(), company.getPhone()});
        return grid;
    }

    /**
     * Создаёт грид в зависимости от параметров
     *
     * @param nameColumns - имена для колонок
     * @param types       - типы колонок
     * @return - возвращает созданный и настроенный грид или выкидывает IllegalArgumentException в случае если размеры массивов различаются.
     */
    private Grid createGrid(String[] nameColumns, Class<?>[] types)
    {
        if (nameColumns.length != types.length)
            throw new IllegalArgumentException("createGrid(String[] nameColumns, Class<?>[] types): nameColumns.length != types.length, nameColumns.length: " + nameColumns.length + " types.length: " + types.length);
        else
        {
            Grid grid = new Grid();
            grid.setSelectionMode(Grid.SelectionMode.SINGLE);
            grid.setSizeFull();
            grid.setImmediate(true);
            for (int i = 0; i < nameColumns.length; i++)
                grid.addColumn(LazyUtils.getLangProperties(nameColumns[i]), types[i]);
            return grid;
        }
    }

    /**
     * Используется для наполнения данными грида, абстракный метод, не зависит от реализации(ну почти)
     *
     * @param data - массив, с данными, формирующий грид.
     * @param grid - грид для наполнения данными
     */
    private void injectDataToGrid(Grid grid, Object[] data)
    {
        grid.addRow(data);
    }

    private Window actionAddItem(AbstractPopUpController controller)
    {
        String str = "";

        Window addItem = new Window("Добавление " + str);
        addItem.setResizable(false);
        addItem.setImmediate(true);
        VerticalLayout tabItemAddVertical = new VerticalLayout();
        addItem.setContent(tabItemAddVertical);
        //ui.addWindow(addCompany);
        controller.displayAddItem(tabItemAddVertical);

        return addItem;
    }

    private void actionEditItem(AbstractPopUpController controller)
    {
        Window editCompany = new Window("Добавление компании");
        editCompany.setResizable(false);
        editCompany.setImmediate(true);
        VerticalLayout tabItemEditVertical = new VerticalLayout();
        editCompany.setContent(tabItemEditVertical);
        //NPE maybe
        // controller.displayEditItem(tabItemEditVertical, grid.getSelectedRow());

    }

    private void actionDeleteItem(AbstractPopUpController controller)
    {
        Window removeCompany = new Window("Удаление компании");
        removeCompany.setResizable(false);
        removeCompany.setImmediate(true);
        VerticalLayout tabItemDeleteVertical = new VerticalLayout();
        removeCompany.setContent(tabItemDeleteVertical);
        //NPE maybe
        //controller.displayDeleteItem(tabItemDeleteVertical, grid.getSelectedRow());

    }
}
