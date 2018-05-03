package ru.aisa.companyregister.ui;

import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.database.dao.entities.Company;
import ru.aisa.companyregister.database.dao.entities.Employee;

import java.util.ArrayList;
import java.util.List;

public class CompaniesPopUpControllerImpl extends AbstractPopUpController<Company>
{
    private ArrayList<Company> companies;
    private ComboBox boxEditCompanies = new ComboBox();
    private ComboBox boxDeleteCompanies = new ComboBox();
    private ColumnsValidatorMapper columnValidator = new CompaniesValidatorMapperImpl();
    private GenericDAO employeeDAO;
    private Button buttonAdd, buttonEdit, buttonDelete, buttonCancelAdd, buttonCancelEdit, buttonCancelDelete;

    public CompaniesPopUpControllerImpl(GenericDAO<Company> companiesDAO, GenericDAO<Employee> employeeDAO)
    {
        super(companiesDAO);
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void init(Layout layout)
    {
        boxEditCompanies.setImmediate(true);
        boxDeleteCompanies.setImmediate(true);
        buttonAdd = new Button("Добавить");
        buttonEdit = new Button("Редактировать");
        buttonDelete = new Button("Удалить");
        buttonCancelAdd = new Button("Отмена");
        buttonCancelEdit = new Button("Отмена");
        buttonCancelDelete = new Button("Отмена");
    }

    @Override
    public Company getItemFromName(String name, List<Company> list)
    {
        for (Company company : list)
        {
            if (company.getCompanyName().equals(name))
                return company;
        }
        return null;
    }

    @Override
    public void updateItemData()
    {
        this.companies = (ArrayList<Company>) this.getDAO().readAll();
        boxEditCompanies.removeAllItems();
        boxDeleteCompanies.removeAllItems();
        for (int i = 0; i < companies.size(); i++)
        {
            boxEditCompanies.addItem(companies.get(i).getCompanyName());
            boxDeleteCompanies.addItem(companies.get(i).getCompanyName());
        }
    }

    @Override
    public void displayAddItem(Layout layout)
    {
        TextField companyField = new TextField();
        companyField.setCaption("Имя компании:");
        companyField.setWidth("200px");
        companyField.setMaxLength(120);
        companyField.addValidator(columnValidator.getValidatorFromColumn("company_name"));

        TextField innField = new TextField();
        innField.setCaption("ИНН");
        innField.setWidth("200px");
        innField.setMaxLength(12);
        innField.addValidator(columnValidator.getValidatorFromColumn("inn"));

        TextField addressField = new TextField();
        addressField.setCaption("Адрес");
        addressField.setWidth("200px");
        addressField.addValidator(columnValidator.getValidatorFromColumn("address"));
        addressField.setMaxLength(250);

        TextField phoneField = new TextField();
        phoneField.setCaption("Номер телефона:");
        phoneField.setWidth("200px");
        phoneField.setMaxLength(15);
        phoneField.addValidator(columnValidator.getValidatorFromColumn("phone"));

        Button saveButton = new Button("Сохранить");
        Button cancelButton = new Button("Отменить");
        cancelButton.addClickListener(event1 ->
        {
            companyField.clear();
            innField.clear();
            addressField.clear();
            phoneField.clear();
        });
        saveButton.addClickListener(event1 ->
        {
            if (companyField.isValid() && innField.isValid() && addressField.isValid() && phoneField.isValid())
            {
                Company employee = new Company(companyField.getValue(), Long.valueOf(innField.getValue()), addressField.getValue(), phoneField.getValue());
                int code = this.genericDAO.create(employee);
                updateItemData();
                companyField.clear();
                innField.clear();
                addressField.clear();
                phoneField.clear();
                if (code == 1)
                    Notification.show("Компания: " + employee.getCompanyName() + " успешно добавлена!");
            }
        });
        layout.addComponents(companyField, innField, addressField, phoneField, cancelButton, saveButton);

    }

    @Override
    public void displayEditItem(Layout layout, Company item, int id)
    {
        layout.addComponent(boxEditCompanies);
        layout.addComponent(buttonEdit);
        buttonEdit.addClickListener(event ->
        {
            Company company = getItemFromName(boxEditCompanies.getValue().toString(), companies);
            if (company != null && !boxEditCompanies.isReadOnly())
            {
                boxEditCompanies.setReadOnly(true);
                buttonEdit.setEnabled(false);
                TextField companyField = new TextField();
                companyField.setCaption("Имя компании:");
                companyField.setValue(company.getCompanyName());
                companyField.setWidth("200px");
                companyField.setMaxLength(120);
                companyField.addValidator(columnValidator.getValidatorFromColumn("company_name"));

                TextField innField = new TextField();
                innField.setCaption("ИНН");
                innField.setValue(String.valueOf(company.getInn()));
                innField.setWidth("200px");
                innField.setMaxLength(12);
                innField.addValidator(columnValidator.getValidatorFromColumn("inn"));

                TextField addressField = new TextField();
                addressField.setCaption("Адрес");
                addressField.setValue(company.getAddress());
                addressField.setWidth("200px");
                addressField.setMaxLength(250);
                addressField.addValidator(columnValidator.getValidatorFromColumn("address"));

                TextField phoneField = new TextField();
                phoneField.setCaption("Номер телефона:");
                phoneField.setValue(company.getPhone());
                phoneField.setWidth("200px");
                phoneField.setMaxLength(15);
                phoneField.addValidator(columnValidator.getValidatorFromColumn("phone"));


                Button saveButton = new Button("Сохранить");
                Button cancelButton = new Button("Отменить");
                cancelButton.addClickListener(event1 ->
                {
                    boxEditCompanies.setReadOnly(false);
                    buttonEdit.setEnabled(true);
                    layout.removeComponent(companyField);
                    layout.removeComponent(innField);
                    layout.removeComponent(addressField);
                    layout.removeComponent(phoneField);
                    layout.removeComponent(saveButton);
                    layout.removeComponent(cancelButton);
                });
                saveButton.addClickListener(event1 ->
                {
                    if (companyField.isValid() && innField.isValid() && addressField.isValid() && phoneField.isValid())
                    {
                        boxEditCompanies.setReadOnly(false);
                        buttonEdit.setEnabled(true);
                        company.setCompanyName(companyField.getValue());
                        company.setInn(Long.parseLong(innField.getValue()));
                        company.setAddress(addressField.getValue());
                        company.setPhone(phoneField.getValue());
                        this.getDAO().updateById(company, company.getId());
                        updateItemData();
                        layout.removeComponent(companyField);
                        layout.removeComponent(innField);
                        layout.removeComponent(addressField);
                        layout.removeComponent(phoneField);
                        layout.removeComponent(cancelButton);
                        layout.removeComponent(saveButton);
                    }
                });
                layout.addComponents(companyField, innField, addressField, phoneField, cancelButton, saveButton);

            }
        });
    }

    @Override
    public void displayDeleteItem(Layout layout, Company item, int id)
    {
        layout.addComponent(boxDeleteCompanies);
        Button buttonChoose = new Button("Выбрать");
        layout.addComponent(buttonChoose);
        buttonChoose.addClickListener(event ->
        {
            Company company = getItemFromName(boxDeleteCompanies.getValue().toString(), companies);
            if (company != null && !boxDeleteCompanies.isReadOnly())
            {
                boxDeleteCompanies.setReadOnly(true);
                boxDeleteCompanies.setEnabled(false);
                Label companyField = new Label();
                companyField.setCaption("Имя компании:" + company.getCompanyName());
                companyField.setWidth("200px");

                Label innField = new Label();
                innField.setCaption("ИНН: " + String.valueOf(company.getInn()));
                innField.setWidth("200px");


                Label addressField = new Label();
                addressField.setCaption("Адрес: " + company.getAddress());
                addressField.setWidth("200px");

                Label phoneField = new Label();
                phoneField.setCaption("Номер телефона: " + company.getPhone());
                phoneField.setValue(company.getPhone());
                phoneField.setWidth("200px");

                Button deleteButton = new Button("Удалить");
                Button cancelButton = new Button("Отменить");
                cancelButton.addClickListener(event1 ->
                {
                    boxDeleteCompanies.setReadOnly(false);
                    buttonChoose.setEnabled(true);
                    layout.removeComponent(companyField);
                    layout.removeComponent(innField);
                    layout.removeComponent(addressField);
                    layout.removeComponent(phoneField);
                    layout.removeComponent(deleteButton);
                    layout.removeComponent(cancelButton);
                });
                deleteButton.addClickListener(event1 ->
                {
                    boxDeleteCompanies.setReadOnly(false);
                    buttonChoose.setEnabled(true);
                    this.getDAO().deleteByID(company.getId());
                    updateItemData();
                    layout.removeComponent(companyField);
                    layout.removeComponent(innField);
                    layout.removeComponent(addressField);
                    layout.removeComponent(phoneField);
                    layout.removeComponent(cancelButton);
                    layout.removeComponent(deleteButton);
                });
                layout.addComponents(companyField, innField, addressField, phoneField, cancelButton, deleteButton);

            }
        });
    }
}
