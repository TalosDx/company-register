package ru.aisa.companyregister.ui;

import com.vaadin.ui.*;
import ru.aisa.companyregister.CompanyRegister;
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
    private final GenericDAO<Company> companyDAO;
    private final ColumnsValidatorMapper validatorMapper = new EmployeeValidatorMapperImpl();
    CompanyRegister ui;
    /**
     * Обязательный конструктор для указания genericDao с которым работаем
     *
     * @param employeeDAO - используется для управлением объектом такому как добавление объекта или его редактирование или удаления
     */
    public EmployeePopUpControllerImpl(GenericDAO<Employee> employeeDAO, GenericDAO<Company> companyDAO, CompanyRegister ui)
    {
        super(employeeDAO, ui.employeeBeanItemContainer);
        this.companyDAO = companyDAO;
        this.ui = ui;
    }


    @Override
    public void updateItemData()
    {
         ui.updateGrids();
    }

    @Override
    public void init(Window window)
    {
        updateItemData();
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

        for (Company company : ui.companies)
        {
            int companyId = company.getId();
            companyBox.addItem(companyId);
            companyBox.setItemCaption(companyId, company.getCompanyName());
        }


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
                Employee employee = new Employee(employeeField.getValue(), toLocalDate(dateField.getValue()), emailField.getValue(), (Integer) companyBox.getValue());
                int code = this.getDAO().create(employee);
                updateItemData();
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
        ui.companies.forEach(company ->
        {
            Integer id = company.getId();
            companyBox.addItem(id);
            companyBox.setItemCaption(id, company.getCompanyName());
        });
        companyBox.select(employee.getCompanyId());


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
                employee.setFullName(employeeField.getValue());
                employee.setBirthday(toLocalDate(dateField.getValue()));
                employee.setEmail(emailField.getValue());
                employee.setCompanyId((Integer) companyBox.getValue());
                this.getDAO().updateById(employee, employee.getId());
                updateItemData();
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
        String company = ui.companyNames.get(employee.getCompanyId());
        if(company != null)
        companyBox.setValue(company);
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
            updateItemData();
            clearAction(ownContent, new Component[]{employeeField, dateField, emailField, companyBox, deleteButton, cancelButton});
            window.close();

        });
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponents(cancelButton, deleteButton);
        ownContent.addComponents(employeeField, dateField, emailField, companyBox, buttonLayout);

    }

    protected Company getCompanyFromId(int id)
    {
        for(Company company : ui.companies)
            if(company.getId() == id)
                return company;
        return null;
    }


    @Override
    public Employee getItemFromName(String name, List<Employee> list)
    {
        for (Employee employee : ui.employees)
        {
            if (employee.getFullName().equals(name))
                return employee;
        }
        return null;
    }
}
