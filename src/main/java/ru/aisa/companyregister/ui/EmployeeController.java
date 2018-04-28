package ru.aisa.companyregister.ui;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.CompanyGenericDAOImpl;
import ru.aisa.companyregister.database.dao.EmployeeGenericDAOImpl;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.entity.Company;
import ru.aisa.companyregister.entity.Employee;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EmployeeController
{
    private GenericDAO<Employee> employeeSql = new EmployeeGenericDAOImpl();
    private List<Employee> employees;
    private Table tableEmployee = new Table();
    private ComboBox boxEditEmployee = new ComboBox();
    private ComboBox boxDeleteEmployee = new ComboBox();
    Window windowEmployee;
    GenericDAO<Company> companySql = new CompanyGenericDAOImpl();
    private List<Company> companies;


    public void init()
    {
        updateCompanyTables();
        windowEmployee = this.createWindowCompanies();
        FormLayout formCompanies = new FormLayout();
        windowEmployee.setContent(formCompanies);
        TabSheet tabsEmployee = new TabSheet();
        tabsEmployee.setHeight("100%");
        formCompanies.addComponent(tabsEmployee);

        this.updateEmployeesCollection();
        this.createCompanyTable();
        final VerticalLayout tabCompanyList = new VerticalLayout();
        final HorizontalLayout tabCompanyEditHorizontal = new HorizontalLayout();
        final VerticalLayout tabCompanyEditVertical = new VerticalLayout();
        final VerticalLayout tabCompanyAddVertical = new VerticalLayout();
        final VerticalLayout tabCompanyDeleteVertical = new VerticalLayout();
        tabCompanyList.setMargin(true);
        boxEditEmployee.setImmediate(true);
        boxDeleteEmployee.setImmediate(true);

        tabsEmployee.addTab(tabCompanyList, "Список сотрудников");
        
        tabsEmployee.addTab(tabCompanyEditVertical, "Редактирование сотрудников");


        tabsEmployee.addTab(tabCompanyAddVertical, "Добавить сотрудника");
        createAddEmployeeButton(tabCompanyAddVertical);

        tabsEmployee.addTab(tabCompanyDeleteVertical, "Удалить сотрудника");
        createDeleteEmployeeButton(tabCompanyDeleteVertical);
        
        tabsEmployee.setSizeFull();
        this.getBoxEditEmployee().setNullSelectionAllowed(false);
        tabCompanyList.addComponent(this.getTableEmployee());
        tabCompanyEditVertical.addComponent(tabCompanyEditHorizontal);
        tabCompanyEditHorizontal.addComponent(this.getBoxEditEmployee());
        Button buttonEdit = new Button("Редактировать");
        tabCompanyEditHorizontal.addComponent(buttonEdit);

        this.addClickEmployeeEditButton(buttonEdit, tabCompanyEditVertical);

    }

    public void createDeleteEmployeeButton(Layout layout)
    {
        layout.addComponent(boxDeleteEmployee);
        Button buttonChoose = new Button("Выбрать");
        layout.addComponent(buttonChoose);
        buttonChoose.addClickListener(event ->
        {
            if (getEmployeeFromName(boxDeleteEmployee.getValue().toString(), employees) != null && !boxDeleteEmployee.isReadOnly())
            {
                boxDeleteEmployee.setReadOnly(true);
                buttonChoose.setEnabled(false);
                Employee employee = getEmployeeFromName(boxDeleteEmployee.getValue().toString(), employees);
                Label employeeField = new Label();
                employeeField.setCaption("ФИО: " + employee.getFullName());
                employeeField.setWidth("200px");

                Label dateField = new Label();
                dateField.setCaption("Дата Рождения: " + employee.getBirthday());
                dateField.setWidth("200px");

                Label emailField = new Label();
                emailField.setCaption("email: " + employee.getEmail());
                emailField.setWidth("200px");

                Label companyBox = new Label();
                companyBox.setCaption("Имя компании: " + employee.getCompanyName());


                Button deleteButton = new Button("Удалить");
                Button cancelButton = new Button("Отменить");
                cancelButton.addClickListener(event1 ->
                {
                    boxDeleteEmployee.setReadOnly(false);
                    buttonChoose.setEnabled(true);
                    layout.removeComponent(employeeField);
                    layout.removeComponent(dateField);
                    layout.removeComponent(emailField);
                    layout.removeComponent(companyBox);
                    layout.removeComponent(employeeField);
                    layout.removeComponent(deleteButton);
                    layout.removeComponent(cancelButton);
                });
                deleteButton.addClickListener(event1 ->
                {

                        boxDeleteEmployee.setReadOnly(false);
                        buttonChoose.setEnabled(true);
                        employeeSql.deleteByID(employee.getId());
                        updateEmployeesTable();
                        layout.removeComponent(employeeField);
                        layout.removeComponent(dateField);
                        layout.removeComponent(emailField);
                        layout.removeComponent(companyBox);
                        layout.removeComponent(companyBox);
                        layout.removeComponent(cancelButton);
                        layout.removeComponent(deleteButton);
                });
                layout.addComponents(employeeField, dateField, emailField, companyBox, cancelButton, deleteButton);

            }
        });
    }

    public void createAddEmployeeButton(Layout layout)
    {
        {
            TextField employeeField = new TextField();
            employeeField.setCaption("ФИО:");
            employeeField.setWidth("200px");
            employeeField.setMaxLength(120);
            employeeField.addValidator(new AbstractStringValidator("Любые символы кроме цифр")
            {
                @Override
                protected boolean isValidValue(String value)
                {
                    return value.matches("[^0-9]+");
                }
            });

            DateField dateField = new DateField();
            dateField.setCaption("Дата Рождения");
            dateField.setWidth("200px");

            TextField emailField = new TextField();
            emailField.setCaption("email");
            emailField.setWidth("200px");
            emailField.setMaxLength(250);
            emailField.addValidator(new EmailValidator("Некорректный email, используйте формат name@mail.ru"));

            ComboBox companyBox = new ComboBox();
            companyBox.setNullSelectionAllowed(false);
            for (int i = 0; i < companies.size(); i++)
                companyBox.addItem(companies.get(i).getCompanyName());


            Button saveButton = new Button("Сохранить");
            Button cancelButton = new Button("Отменить");
            cancelButton.addClickListener(event1 ->
            {
                employeeField.clear();
                dateField.clear();
                emailField.clear();
                companyBox.clear();
            });
            saveButton.addClickListener(event1 ->
            {
                if (employeeField.isValid() && dateField.isValid() && emailField.isValid() && companyBox.getValue() != null)
                {
                    Employee employee = new Employee(employeeField.getValue(), toLocalDate(dateField.getValue()), emailField.getValue(), (String) companyBox.getValue());
                    int code = employeeSql.create(employee);
                    updateEmployeesTable();
                    employeeField.clear();
                    dateField.clear();
                    emailField.clear();
                    companyBox.clear();
                    if(code == 1)
                        Notification.show("Сотрудник: " + employee.getFullName() + " успешно добавлен!");
                }
            });
            layout.addComponents(employeeField, dateField, emailField, companyBox, cancelButton, saveButton);

        }
    }


    public Window getWindowEmployees()
    {
        if (windowEmployee == null)
            throw new IllegalStateException("CompaniesController not initialized!");
        return windowEmployee;
    }

    public Window createWindowCompanies()
    {
        Window windowCompanies = new Window("Сотрудники");
        windowCompanies.setWidth(800, Sizeable.Unit.PIXELS);
        windowCompanies.setHeight(400, Sizeable.Unit.PIXELS);
        windowCompanies.setClosable(true);
        windowCompanies.setResizable(false);
        return windowCompanies;
    }

    public Table createCompanyTable()
    {
        tableEmployee.setSelectable(false);
        tableEmployee.setImmediate(true);

        tableEmployee.addContainerProperty("ФИО", String.class, null);
        tableEmployee.addContainerProperty("Дата Рождения", LocalDate.class, null);
        tableEmployee.addContainerProperty("Email", String.class, null);
        tableEmployee.addContainerProperty("Имя Компании", String.class, null);

        updateEmployeesTable();
        tableEmployee.setPageLength(tableEmployee.size());
        return tableEmployee;
    }

    /**
     * Обновляет только коллекцию с сотрудниками
     */
    public void updateEmployeesCollection()
    {
        this.employees = employeeSql.readAll();
    }

    /**
     * Обновляет данные в таблице, и остальных зависимых компонентах
     */
    public void updateEmployeesTable()
    {
        this.employees = employeeSql.readAll();
        tableEmployee.removeAllItems();
        for (int i = 0; i < employees.size(); i++)
        {
            Employee employee = employees.get(i);
            tableEmployee.addItem(new Object[]{employee.getFullName(), employee.getBirthday(), employee.getEmail(), employee.getCompanyName()}, i);
        }
        boxEditEmployee.removeAllItems();
        for (int i = 0; i < employees.size(); i++)
            boxEditEmployee.addItem(employees.get(i).getFullName());

        boxDeleteEmployee.removeAllItems();
        for (int i = 0; i < employees.size(); i++)
            boxDeleteEmployee.addItem(employees.get(i).getFullName());
    }

    public Button addClickEmployeeEditButton(Button buttonEdit, VerticalLayout layout)
    {
        buttonEdit.addClickListener(event ->
        {
            if (getEmployeeFromName(boxEditEmployee.getValue().toString(), employees) != null && !boxEditEmployee.isReadOnly())
            {
                boxEditEmployee.setReadOnly(true);
                buttonEdit.setEnabled(false);
                Employee employee = getEmployeeFromName(boxEditEmployee.getValue().toString(), employees);
                TextField employeeField = new TextField();
                employeeField.setCaption("ФИО:");
                employeeField.setValue(employee.getFullName());
                employeeField.setWidth("200px");
                employeeField.setMaxLength(120);
                employeeField.addValidator(new AbstractStringValidator("Любые символы кроме цифр")
                {
                    @Override
                    protected boolean isValidValue(String value)
                    {
                        return value.matches("[^0-9]+");
                    }
                });

                DateField dateField = new DateField();
                dateField.setCaption("Дата Рождения");
                dateField.setValue(Date.valueOf(employee.getBirthday()));
                dateField.setWidth("200px");

                TextField emailField = new TextField();
                emailField.setCaption("email");
                emailField.setValue(employee.getEmail());
                emailField.setWidth("200px");
                emailField.setMaxLength(250);
                emailField.addValidator(new EmailValidator("Некорректный email, используйте формат name@mail.ru"));

                ComboBox companyBox = new ComboBox();
                companyBox.setNullSelectionAllowed(false);
                for (int i = 0; i < companies.size(); i++)
                    companyBox.addItem(companies.get(i).getCompanyName());


                Button saveButton = new Button("Сохранить");
                Button cancelButton = new Button("Отменить");
                cancelButton.addClickListener(event1 ->
                {
                    boxEditEmployee.setReadOnly(false);
                    buttonEdit.setEnabled(true);
                    layout.removeComponent(employeeField);
                    layout.removeComponent(dateField);
                    layout.removeComponent(emailField);
                    layout.removeComponent(companyBox);
                    layout.removeComponent(employeeField);
                    layout.removeComponent(saveButton);
                    layout.removeComponent(cancelButton);
                });
                saveButton.addClickListener(event1 ->
                {
                    if (employeeField.isValid() && dateField.isValid() && emailField.isValid() && companyBox.getValue() != null)
                    {
                        boxEditEmployee.setReadOnly(false);
                        buttonEdit.setEnabled(true);
                        employee.setCompanyName(employeeField.getValue());
                        employee.setBirthday(toLocalDate(dateField.getValue()));
                        employee.setEmail(emailField.getValue());
                        employee.setCompanyName((String) companyBox.getValue());
                        employeeSql.updateById(employee, (int) employee.getId());
                        updateEmployeesTable();
                        layout.removeComponent(employeeField);
                        layout.removeComponent(dateField);
                        layout.removeComponent(emailField);
                        layout.removeComponent(companyBox);
                        layout.removeComponent(companyBox);
                        layout.removeComponent(cancelButton);
                        layout.removeComponent(saveButton);
                    }
                });
                layout.addComponents(employeeField, dateField, emailField, companyBox, cancelButton, saveButton);

            }
        });
        return buttonEdit;
    }

    public void updateCompanyTables()
    {
        this.companies = companySql.readAll();
    }

    public LocalDate toLocalDate(java.util.Date date)
    {
        return LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
    }

    //Уровень реализации
    public Employee getEmployeeFromName(String employeeFullName, List<Employee> employees)
    {
        for (Employee employee : employees)
        {
            if (employee.getFullName().equals(employeeFullName))
                return employee;
        }
        return null;
    }

    public GenericDAO<Employee> getEmployeeSql()
    {
        return employeeSql;
    }

    public void setEmployeeSql(GenericDAO<Employee> employeeSql)
    {
        this.employeeSql = employeeSql;
    }

    public List<Employee> getEmployees()
    {
        return employees;
    }

    public void setEmployees(List<Employee> employees)
    {
        this.employees = employees;
    }

    public Table getTableEmployee()
    {
        return tableEmployee;
    }

    public void setTableEmployee(Table tableEmployee)
    {
        this.tableEmployee = tableEmployee;
    }

    public ComboBox getBoxEditEmployee()
    {
        return boxEditEmployee;
    }

    public void setBoxEditEmployee(ComboBox boxEditEmployee)
    {
        this.boxEditEmployee = boxEditEmployee;
    }

}
