package ru.aisa.companyregister.ui;

import com.vaadin.data.util.BeanItemContainer;
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
    private final GenericDAO<Company> companyDAO;
    private final ColumnsValidatorMapper validatorMapper = new EmployeeValidatorMapperImpl();

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
    public void init(Window window)
    {
        this.updateItemData();
    }

    /**
     * Размещает компоненты в Window, и управляет их логикой
     *
     * @param window - используется для отображения объектов
     */
    @Override
    public void displayAddItem(Window window)
    {
        VerticalLayout ownContent = new VerticalLayout();
        ((Layout) window.getContent()).addComponent(ownContent);
        ownContent.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        ownContent.setMargin(true);
        ownContent.setSpacing(true);
        TextField employeeField = new TextField();
        employeeField.setCaption(LazyUtils.getLangProperties("fullName") + ": ");
        employeeField.setWidth("200px");
        employeeField.setMaxLength(120);
        employeeField.addValidator(validatorMapper.getValidatorFromColumn("full_name"));

        DateField dateField = new DateField();
        dateField.setCaption(LazyUtils.getLangProperties("birthday") + ": ");
        dateField.addValidator(this.validatorMapper.getValidatorFromColumn("birthday"));
        dateField.setWidth("200px");

        TextField emailField = new TextField();
        emailField.setCaption(LazyUtils.getLangProperties("email") + ": ");
        emailField.setWidth("200px");
        emailField.setMaxLength(250);
        emailField.addValidator(validatorMapper.getValidatorFromColumn("email"));

        ComboBox companyBox = new ComboBox(LazyUtils.getLangProperties("companyName") + ": ");
        companyBox.setWidth("200px");
        companyBox.setNullSelectionAllowed(false);

        companies.stream().map(Company::getCompanyName).forEach(companyBox::addItem);


        Button saveButton = new Button(LazyUtils.getLangProperties("button.add"));
        Button cancelButton = new Button(LazyUtils.getLangProperties("button.cancel"));
        cancelButton.addClickListener(event1 ->
        {
            clearFields(new Field[]{employeeField, dateField, emailField, companyBox});
            window.close();
        });

        saveButton.addClickListener(event1 ->
        {
            if (employeeField.isValid() && dateField.isValid() && emailField.isValid() && companyBox.isValid())
            {
                Employee employee = new Employee(employeeField.getValue(), toLocalDate(dateField.getValue()), emailField.getValue(), (String) companyBox.getValue());
                int code = this.getDAO().create(employee);
                updateEmployeesTable();
                clearFields(new Field[]{employeeField, dateField, emailField, companyBox});
                if (code == 1)
                {
                    Notification.show("Сотрудник: " + employee.getFullName() + " успешно добавлен!");
                }
                window.close();
            }
            else
            {
                showErrorWithFields(new Field[]{employeeField, dateField, emailField, companyBox});
            }
        });

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponents(cancelButton, saveButton);
        ownContent.addComponents(employeeField, dateField, emailField, companyBox, buttonLayout);

    }

    @Override
    public void displayEditItem(Window window, Employee employee)
    {
        VerticalLayout ownContent = new VerticalLayout();
        ((Layout) window.getContent()).addComponent(ownContent);
        ownContent.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        ownContent.setMargin(true);
        ownContent.setSpacing(true);
        TextField employeeField = new TextField();
        employeeField.setCaption(LazyUtils.getLangProperties("fullName") + ": ");
        employeeField.setValue(employee.getFullName());
        employeeField.setWidth("200px");
        employeeField.setMaxLength(120);
        employeeField.addValidator(validatorMapper.getValidatorFromColumn("full_name"));

        DateField dateField = new DateField();
        dateField.setCaption(LazyUtils.getLangProperties("birthday") + ": ");
        dateField.setValue(Date.valueOf(employee.getBirthday()));
        dateField.addValidator(this.validatorMapper.getValidatorFromColumn("birthday"));
        dateField.setWidth("200px");

        TextField emailField = new TextField();
        emailField.setCaption(LazyUtils.getLangProperties("email") + ": ");
        emailField.setValue(employee.getEmail());
        emailField.setWidth("200px");
        emailField.setMaxLength(250);
        emailField.addValidator(validatorMapper.getValidatorFromColumn("email"));

        ComboBox companyBox = new ComboBox(LazyUtils.getLangProperties("companyName") + ": ");
        companyBox.setWidth("200px");
        companyBox.setNullSelectionAllowed(false);
        companyBox.addValidator(validatorMapper.getValidatorFromColumn("company_name"));
        companies.stream().map(Company::getCompanyName).forEach(companyBox::addItem);
        companyBox.select(employee.getCompanyName());


        Button saveButton = new Button(LazyUtils.getLangProperties("button.save"));
        Button cancelButton = new Button(LazyUtils.getLangProperties("button.cancel"));
        cancelButton.addClickListener(event1 ->
        {
            clearAction(ownContent, new Component[]{employeeField, dateField, emailField, companyBox, saveButton, cancelButton});
            window.close();
        });
        saveButton.addClickListener(event1 ->
        {
            if (employeeField.isValid() && dateField.isValid() && emailField.isValid() && companyBox.isValid())
            {
                employee.setCompanyName(employeeField.getValue());
                employee.setBirthday(toLocalDate(dateField.getValue()));
                employee.setEmail(emailField.getValue());
                employee.setCompanyName((String) companyBox.getValue());
                this.getDAO().updateById(employee, employee.getId());
                updateEmployeesTable();
                clearAction(ownContent, new Component[]{employeeField, dateField, emailField, companyBox, saveButton, cancelButton});
                window.close();
            }
            else showErrorWithFields(new Field[]{employeeField, dateField, emailField, companyBox});

        });

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponents(cancelButton, saveButton);
        ownContent.addComponents(employeeField, dateField, emailField, companyBox, buttonLayout);
    }

    @Override
    public void displayDeleteItem(Window window, Employee employee)
    {
        VerticalLayout ownContent = new VerticalLayout();
        ((Layout) window.getContent()).addComponent(ownContent);
        ownContent.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        ownContent.setMargin(true);
        ownContent.setSpacing(true);

        TextField employeeField = new TextField();
        employeeField.setCaption(LazyUtils.getLangProperties("fullName") + ": ");
        employeeField.setValue(employee.getFullName());
        employeeField.setReadOnly(true);
        employeeField.setWidth("200px");

        DateField dateField = new DateField();
        dateField.setCaption(LazyUtils.getLangProperties("birthday") + ": ");
        dateField.setValue(Date.valueOf(employee.getBirthday()));
        dateField.setReadOnly(true);
        dateField.setWidth("200px");

        TextField emailField = new TextField();
        emailField.setCaption(LazyUtils.getLangProperties("email") + ": ");
        emailField.setValue(employee.getEmail());
        emailField.setReadOnly(true);
        emailField.setWidth("200px");

        TextField companyBox = new TextField();
        companyBox.setCaption(LazyUtils.getLangProperties("companyName") + ": ");
        companyBox.setValue(employee.getCompanyName());
        companyBox.setReadOnly(true);
        companyBox.setWidth("200px");

        Button deleteButton = new Button(LazyUtils.getLangProperties("button.delete"));
        Button cancelButton = new Button(LazyUtils.getLangProperties("button.cancel"));
        cancelButton.addClickListener(event1 ->
        {

            clearAction(ownContent, new Component[]{employeeField, dateField, emailField, companyBox, deleteButton, cancelButton});
            window.close();
        });
        deleteButton.addClickListener(event1 ->
        {
            this.getDAO().deleteByID(employee.getId());
            updateEmployeesTable();
            clearAction(ownContent, new Component[]{employeeField, dateField, emailField, companyBox, deleteButton, cancelButton});
            window.close();

        });
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponents(cancelButton, deleteButton);
        ownContent.addComponents(employeeField, dateField, emailField, companyBox, buttonLayout);

    }


    /**
     * Обновляет данные в таблице, и остальных зависимых компонентах
     */
    private void updateEmployeesTable()
    {
        super.itemContainer.removeAllItems();
        updateEmployeesCollection();
        super.itemContainer.addAll(this.employees);
    }

    /**
     * Обновляет только коллекцию с сотрудниками
     */
    private void updateEmployeesCollection()
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
