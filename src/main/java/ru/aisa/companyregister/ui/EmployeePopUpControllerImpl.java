package ru.aisa.companyregister.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.database.dao.entities.Company;
import ru.aisa.companyregister.database.dao.entities.Employee;
import ru.aisa.companyregister.ui.validator.ColumnsValidatorMapper;
import ru.aisa.companyregister.ui.validator.EmployeeValidatorMapperImpl;
import ru.aisa.companyregister.utils.LazyUtils;

import java.sql.Date;
import java.util.List;

import static ru.aisa.companyregister.utils.LazyUtils.toLocalDate;

public class EmployeePopUpControllerImpl extends AbstractPopUpController<Employee>
{
    private List<Employee> employees;
    private List<Company> companies;
    private GenericDAO<Company> companyDAO;
    private ColumnsValidatorMapper validatorMapper = new EmployeeValidatorMapperImpl();

    /**
     * Обязательный конструктор для указания genericDao с которым работаем
     *
     * @param employeeDAO - используется для управлением объектом такому как добавление объекта или его редактирование или удаления
     */
    public EmployeePopUpControllerImpl(GenericDAO<Employee> employeeDAO, BeanItemContainer<Employee> itemContainer, GenericDAO<Company> companyDAO)
    {
        super(employeeDAO, itemContainer);
        this.companyDAO = companyDAO;
    }


    @Override
    public void updateItemData()
    {
        updateEmployeesTable();
        companies = companyDAO.readAll();
    }

    @Override
    public void init(Layout layout)
    {
        TabSheet tabsEmployee = new TabSheet();
        tabsEmployee.setHeight("100%");
        layout.addComponent(tabsEmployee);

        this.updateItemData();
        final VerticalLayout tabCompanyList = new VerticalLayout();
        final HorizontalLayout tabCompanyEditHorizontal = new HorizontalLayout();
        final VerticalLayout tabCompanyEditVertical = new VerticalLayout();
        tabCompanyList.setMargin(true);


        tabsEmployee.setSizeFull();
        tabCompanyEditVertical.addComponent(tabCompanyEditHorizontal);
    }

    /**
     * Размещает компоненты в Window, и управляет их логикой
     *
     * @param layout - лейаут используется для отображения объектов
     */
    @Override
    public void displayAddItem(Layout layout)
    {
        TextField employeeField = new TextField();
        employeeField.setCaption(LazyUtils.getLangProperties("fullName" + ": "));
        employeeField.setWidth("200px");
        employeeField.setMaxLength(120);
        employeeField.addValidator(validatorMapper.getValidatorFromColumn("full_name"));

        DateField dateField = new DateField();
        dateField.setCaption(LazyUtils.getLangProperties("birthday" + ": "));
        dateField.setWidth("200px");

        TextField emailField = new TextField();
        emailField.setCaption(LazyUtils.getLangProperties("email" + ": "));
        emailField.setWidth("200px");
        emailField.setMaxLength(250);
        emailField.addValidator(validatorMapper.getValidatorFromColumn("email"));

        ComboBox companyBox = new ComboBox();
        companyBox.setWidth(300, Sizeable.Unit.PIXELS);
        companyBox.setNullSelectionAllowed(false);

        companies.stream().map(Company::getCompanyName).forEach(companyBox::addItem);


        Button saveButton = new Button(LazyUtils.getLangProperties("button.save"));
        Button cancelButton = new Button(LazyUtils.getLangProperties("button.cancel"));
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
                int code = this.getDAO().create(employee);
                updateEmployeesTable();
                clearFields(new Field[]{employeeField, dateField, emailField, companyBox});
                if (code == 1)
                    Notification.show("Сотрудник: " + employee.getFullName() + " успешно добавлен!");
            }
        });
        layout.addComponents(employeeField, dateField, emailField, companyBox, cancelButton, saveButton);

    }

    @Override
    public void displayEditItem(Layout layout, Employee employee)
    {
        TextField employeeField = new TextField();
        employeeField.setCaption(LazyUtils.getLangProperties("fullName") + ": ");
        employeeField.setValue(employee.getFullName());
        employeeField.setWidth("200px");
        employeeField.setMaxLength(120);
        employeeField.addValidator(validatorMapper.getValidatorFromColumn("full_name"));

        DateField dateField = new DateField();
        dateField.setCaption(LazyUtils.getLangProperties("birthday") + ": ");
        dateField.setValue(Date.valueOf(employee.getBirthday()));
        dateField.setWidth("200px");

        TextField emailField = new TextField();
        emailField.setCaption(LazyUtils.getLangProperties("email") + ": ");
        emailField.setValue(employee.getEmail());
        emailField.setWidth("200px");
        emailField.setMaxLength(250);
        emailField.addValidator(validatorMapper.getValidatorFromColumn("email"));

        ComboBox companyBox = new ComboBox();
        companyBox.setWidth("200px");
        companyBox.setNullSelectionAllowed(false);


        companies.stream().map(Company::getCompanyName).forEach(companyBox::addItem);


        Button saveButton = new Button(LazyUtils.getLangProperties("button.save"));
        Button cancelButton = new Button(LazyUtils.getLangProperties("button.cancel"));
        cancelButton.addClickListener(event1 ->
                clearAction(layout, new Component[]{employeeField, dateField, emailField, companyBox, saveButton, cancelButton}));
        saveButton.addClickListener(event1 ->
        {
            if (employeeField.isValid() && dateField.isValid() && emailField.isValid() && companyBox.getValue() != null)
            {
                employee.setCompanyName(employeeField.getValue());
                employee.setBirthday(toLocalDate(dateField.getValue()));
                employee.setEmail(emailField.getValue());
                employee.setCompanyName((String) companyBox.getValue());
                this.getDAO().updateById(employee, (int) employee.getId());
                updateEmployeesTable();
                clearAction(layout, new Component[]{employeeField, dateField, emailField, companyBox, saveButton, cancelButton});
            }
        });
        layout.addComponents(employeeField, dateField, emailField, companyBox, cancelButton, saveButton);

    }

    @Override
    public void displayDeleteItem(Layout layout, Employee employee)
    {
        Label employeeField = new Label();
        employeeField.setCaption(LazyUtils.getLangProperties("fullName") + ": " + employee.getFullName());
        employeeField.setWidth("200px");

        Label dateField = new Label();
        dateField.setCaption(LazyUtils.getLangProperties("birthday") + ": " + employee.getBirthday());
        dateField.setWidth("200px");

        Label emailField = new Label();
        emailField.setCaption(LazyUtils.getLangProperties("email") + ": " + employee.getEmail());
        emailField.setWidth("200px");

        Label companyBox = new Label();
        companyBox.setCaption(LazyUtils.getLangProperties("companyName") + ": " + employee.getCompanyName());


        Button deleteButton = new Button(LazyUtils.getLangProperties("button.delete"));
        Button cancelButton = new Button(LazyUtils.getLangProperties("button.cancel"));
        cancelButton.addClickListener(event1 ->
        {

            clearAction(layout, new Component[]{employeeField, dateField, emailField, companyBox, deleteButton, cancelButton});
        });
        deleteButton.addClickListener(event1 ->
        {

            this.getDAO().deleteByID(employee.getId());
            updateEmployeesTable();
            clearAction(layout, new Component[]{employeeField, dateField, emailField, companyBox, deleteButton, cancelButton});

        });
        layout.addComponents(employeeField, dateField, emailField, companyBox, cancelButton, deleteButton);

    }


    /**
     * Обновляет данные в таблице, и остальных зависимых компонентах
     */
    public void updateEmployeesTable()
    {
        super.itemContainer.removeAllItems();
        updateEmployeesCollection();
        super.itemContainer.addAll(this.employees);
    }

    /**
     * Обновляет только коллекцию с сотрудниками
     */
    public void updateEmployeesCollection()
    {
        this.employees = this.genericDAO.readAll();
    }


    @Override
    public Employee getItemFromName(String name, List<Employee> list)
    {
        for (Employee employee : employees)
        {
            if (employee.getFullName().equals(name))
                return employee;
        }
        return null;
    }
}
