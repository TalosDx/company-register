package ru.aisa.companyregister.ui;

import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.database.dao.entities.Employee;

import java.sql.Date;
import java.util.List;

import static ru.aisa.companyregister.utils.LazyUtils.toLocalDate;

public class EmployeePopUpControllerImpl extends AbstractPopUpController<Employee>
{
    private List<Employee> employees;
    private Grid gridEmployee = new Grid();
    private ComboBox boxEdit = new ComboBox();
    private ComboBox boxDelete = new ComboBox();
    private String[] companyNames;
    private ColumnsValidatorMapper validatorMapper = new EmployeeValidatorMapperImpl();

    /**
     * Обязательный конструктор для указания genericDao с которым работаем
     *
     * @param genericDAO - используется для управлением объектом такому как добавление объекта или его редактирование или удаления
     */
    public EmployeePopUpControllerImpl(GenericDAO<Employee> genericDAO, String[] companyNames)
    {
        super(genericDAO);
        this.companyNames = companyNames;
    }


    @Override
    public void updateItemData()
    {
        updateEmployeesTable();
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
        final VerticalLayout tabCompanyAddVertical = new VerticalLayout();
        final VerticalLayout tabCompanyDeleteVertical = new VerticalLayout();
        tabCompanyList.setMargin(true);
        boxEdit.setImmediate(true);
        boxDelete.setImmediate(true);




        createAddEmployeeButton(tabCompanyAddVertical);

        createDeleteEmployeeButton(tabCompanyDeleteVertical);
        
        tabsEmployee.setSizeFull();
        boxEdit.setNullSelectionAllowed(false);
        tabCompanyEditVertical.addComponent(tabCompanyEditHorizontal);
        tabCompanyEditHorizontal.addComponent(boxEdit);
        Button buttonEdit = new Button("Редактировать");
        tabCompanyEditHorizontal.addComponent(buttonEdit);

        this.addClickEmployeeEditButton(buttonEdit, tabCompanyEditVertical);

    }

    @Override
    public void displayAddItem(Layout layout)
    {

    }

    @Override
    public void displayEditItem(Layout layout, Employee item, int id)
    {

    }

    @Override
    public void displayDeleteItem(Layout layout, Employee item, int id)
    {

    }

    public void createDeleteEmployeeButton(Layout layout)
    {
        layout.addComponent(boxDelete);
        Button buttonChoose = new Button("Выбрать");
        layout.addComponent(buttonChoose);
        buttonChoose.addClickListener(event ->
        {
            Employee employee = getItemFromName(boxDelete.getValue().toString(), employees);
            if (employee != null && !boxDelete.isReadOnly())
            {
                boxDelete.setReadOnly(true);
                buttonChoose.setEnabled(false);
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
                    boxDelete.setReadOnly(false);
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

                        boxDelete.setReadOnly(false);
                        buttonChoose.setEnabled(true);
                        this.getDAO().deleteByID(employee.getId());
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
            employeeField.addValidator(validatorMapper.getValidatorFromColumn("full_name"));

            DateField dateField = new DateField();
            dateField.setCaption("Дата Рождения");
            dateField.setWidth("200px");

            TextField emailField = new TextField();
            emailField.setCaption("email");
            emailField.setWidth("200px");
            emailField.setMaxLength(250);
            emailField.addValidator(validatorMapper.getValidatorFromColumn("email"));

            ComboBox companyBox = new ComboBox();
            companyBox.setNullSelectionAllowed(false);
            for (int i = 0; i < companyNames.length; i++)
                companyBox.addItem(companyNames[i]);


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
                    int code = this.getDAO().create(employee);
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


    /**
     * Обновляет данные в таблице, и остальных зависимых компонентах
     */
    public void updateEmployeesTable()
    {
        updateEmployeesCollection();
        boxEdit.removeAllItems();
        boxDelete.removeAllItems();
        for (int i = 0; i < employees.size(); i++)
        {
            Employee employee = employees.get(i);
            gridEmployee.addRow(new Object[]{employee.getFullName(), employee.getBirthday(), employee.getEmail(), employee.getCompanyName()});
            boxEdit.addItem(employees.get(i).getFullName());
            boxDelete.addItem(employees.get(i).getFullName());

        }
    }

    /**
     * Обновляет только коллекцию с сотрудниками
     */
    public void updateEmployeesCollection()
    {
        this.employees = this.genericDAO.readAll();
    }


    public Button addClickEmployeeEditButton(Button buttonEdit, VerticalLayout layout)
    {
        buttonEdit.addClickListener(event ->
        {
            Employee employee = getItemFromName(boxEdit.getValue().toString(), employees);
            if (employee != null && !boxEdit.isReadOnly())
            {
                boxEdit.setReadOnly(true);
                buttonEdit.setEnabled(false);
                TextField employeeField = new TextField();
                employeeField.setCaption("ФИО:");
                employeeField.setValue(employee.getFullName());
                employeeField.setWidth("200px");
                employeeField.setMaxLength(120);
                employeeField.addValidator(validatorMapper.getValidatorFromColumn("full_name"));

                DateField dateField = new DateField();
                dateField.setCaption("Дата Рождения");
                dateField.setValue(Date.valueOf(employee.getBirthday()));
                dateField.setWidth("200px");

                TextField emailField = new TextField();
                emailField.setCaption("email");
                emailField.setValue(employee.getEmail());
                emailField.setWidth("200px");
                emailField.setMaxLength(250);
                emailField.addValidator(validatorMapper.getValidatorFromColumn("email"));

                ComboBox companyBox = new ComboBox();
                companyBox.setNullSelectionAllowed(false);
                for (int i = 0; i < companyNames.length; i++)
                    companyBox.addItem(companyNames[i]);


                Button saveButton = new Button("Сохранить");
                Button cancelButton = new Button("Отменить");
                cancelButton.addClickListener(event1 ->
                {
                    boxEdit.setReadOnly(false);
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
                        boxEdit.setReadOnly(false);
                        buttonEdit.setEnabled(true);
                        employee.setCompanyName(employeeField.getValue());
                        employee.setBirthday(toLocalDate(dateField.getValue()));
                        employee.setEmail(emailField.getValue());
                        employee.setCompanyName((String) companyBox.getValue());
                        this.getDAO().updateById(employee, (int) employee.getId());
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
