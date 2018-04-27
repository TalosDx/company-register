package ru.aisa.companyregister;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.CompanyGenericDAOImpl;
import ru.aisa.companyregister.database.dao.EmployeeGenericDAOImpl;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.entity.Company;
import ru.aisa.companyregister.entity.Employee;
import ru.aisa.companyregister.ui.WindowCompanyAdd;
import ru.aisa.companyregister.ui.WindowController;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class CompanyRegister extends UI
{
    final WindowController window = new WindowCompanyAdd();
    GenericDAO companySql = new CompanyGenericDAOImpl();
    GenericDAO employeeSql = new EmployeeGenericDAOImpl();

    @Override
    protected void init(VaadinRequest request)
    {
        this.getPage().setTitle("Реестр компании и сотрудников");
        final VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        Label countCompany = new Label("Общее количество зарегестрированных компаний: " + companySql.getCount());
        Label countEmployee = new Label("Общее количество зарегестрированных сотрудников: " + employeeSql.getCount());
        layout.addComponent(countCompany);
        layout.addComponent(countEmployee);
        layout.setComponentAlignment(countCompany, Alignment.TOP_CENTER);
        layout.setComponentAlignment(countEmployee, Alignment.TOP_CENTER);

        Button companiesButton = new Button("Компании");
        Button employeeButton = new Button("Сотрудники");
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSpacing(true);
        hLayout.addComponents(companiesButton, employeeButton);
        layout.addComponent(hLayout);
        Window windowCompanies = new Window("Компании");
        TabSheet tabSheet = new TabSheet();
        tabSheet.setHeight("100%");
        windowCompanies.setWidth(600, Unit.PIXELS);
        windowCompanies.setHeight(400, Unit.PIXELS);
        windowCompanies.setClosable(true);
        windowCompanies.setResizable(false);
        companiesButton.addClickListener(event -> addWindow(windowCompanies));
        FormLayout formCompanies = new FormLayout();
        windowCompanies.setContent(formCompanies);
        Table tableCompanies = new Table();
        tableCompanies.setSelectable(true);
        tableCompanies.setImmediate(true);

        tableCompanies.addContainerProperty("Имя Компании", String.class, null);
        tableCompanies.addContainerProperty("ИНН", Long.class, null);
        tableCompanies.addContainerProperty("Адрес", String.class, null);
        tableCompanies.addContainerProperty("Телефон", String.class, null);

        List<Company> companies = companySql.readAll();
        for (int i = 0; i < companies.size(); i++)
        {
            Company company = companies.get(i);
            tableCompanies.addItem(new Object[]{company.getCompanyName(), company.getInn(), company.getAddress(), company.getPhone()}, i);
        }
        tableCompanies.setPageLength(tableCompanies.size());
        final VerticalLayout verticalLayout = new VerticalLayout();
        layout.setMargin(true);
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        final VerticalLayout verticalLayout1 = new VerticalLayout();
        verticalLayout.setMargin(true);
        tabSheet.addTab(verticalLayout, "Список компаний");
        tabSheet.addTab(verticalLayout1, "Редактирование компаний");
        tabSheet.setSizeFull();
        ComboBox boxCompanies = new ComboBox();
        for(int i=0; i<companies.size(); i++)
            boxCompanies.addItem(companies.get(i).getCompanyName());

        Button buttonEdit = new Button("Редактировать");
        buttonEdit.addClickListener(event ->
        {
            Company company = getCompanyFromName(boxCompanies.getValue().toString(), companies);
            if(company != null)
            {
                HorizontalLayout companyNameLayout = new HorizontalLayout();
                Label companyName = new Label("Имя компании");
                TextField companyField = new TextField();
                companyField.setValue(company.getCompanyName());
                companyField.setWidth("200px");
                companyNameLayout.addComponents(companyName, companyField);
                companyNameLayout.setWidth("90%");
                companyNameLayout.setComponentAlignment(companyName, Alignment.TOP_LEFT);
                companyNameLayout.setComponentAlignment(companyField, Alignment.TOP_RIGHT);

                HorizontalLayout companyInnLayout = new HorizontalLayout();
                Label inn = new Label("ИНН");
                TextField innField = new TextField();
                innField.setValue(String.valueOf(company.getInn()));
                innField.setWidth("200px");
                companyInnLayout.addComponents(inn, innField);
                companyInnLayout.setWidth("90%");
                companyInnLayout.setComponentAlignment(inn, Alignment.TOP_LEFT);
                companyInnLayout.setComponentAlignment(innField, Alignment.TOP_RIGHT);

                HorizontalLayout companyAddressLayout = new HorizontalLayout();
                Label address = new Label("Адрес");
                TextField addressField = new TextField();
                addressField.setValue(company.getAddress());
                addressField.setWidth("200px");
                companyAddressLayout.addComponents(address, addressField);
                companyAddressLayout.setWidth("90%");
                companyAddressLayout.setComponentAlignment(address, Alignment.TOP_LEFT);
                companyAddressLayout.setComponentAlignment(addressField, Alignment.TOP_RIGHT);

                HorizontalLayout companyPhoneLayout = new HorizontalLayout();
                Label phone = new Label("Телефон");
                TextField phoneField = new TextField();
                phoneField.setValue(company.getPhone());
                phoneField.setWidth("200px");
                phoneField.addValidator(new AbstractStringValidator("Используйте только русские буквы.")
                {
                    @Override
                    protected boolean isValidValue(String value)
                    {
                        return value.length() > 5 && value.length() < 151 && value.matches("[А-ЯЁ]*\\s[а-яё]*$");
                    }
                });
                companyPhoneLayout.addComponents(phone, phoneField);
                companyPhoneLayout.setWidth("90%");
                companyPhoneLayout.setComponentAlignment(phone, Alignment.TOP_LEFT);
                companyPhoneLayout.setComponentAlignment(phoneField, Alignment.TOP_RIGHT);

                verticalLayout1.addComponent(companyNameLayout);
                verticalLayout1.addComponent(companyInnLayout);
                verticalLayout1.addComponent(companyAddressLayout);
                verticalLayout1.addComponent(companyPhoneLayout);
            }
        });
        verticalLayout.addComponent(tableCompanies);
        verticalLayout1.addComponent(horizontalLayout);
        horizontalLayout.addComponent(boxCompanies);
        horizontalLayout.addComponent(buttonEdit);
        formCompanies.addComponent(tabSheet);

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

    //Уровень реализации
    public Company getCompanyFromName(String companyName, List<Company> companies)
    {
        for(Company company : companies)
        {
            if(company.getCompanyName().equals(companyName))
                return company;
        }
        return null;
    }

    //Уровень реализации
    public Employee getEmployeeFromName(String employeeName, List<Employee> employees)
    {
        for(Employee employee : employees)
        {
            if(employee.getCompanyName().equals(employeeName))
                return employee;
        }
        return null;
    }

    private void createButton(VerticalLayout layout)
    {
        Button button = new Button();
        layout.addComponent(button);
        layout.setComponentAlignment(button, Alignment.TOP_CENTER);
        button.addClickListener(event ->
        {
            if (!this.getWindows().contains(window.getWindow()))
                this.addWindow(window.getWindow());
        });
    }
}
