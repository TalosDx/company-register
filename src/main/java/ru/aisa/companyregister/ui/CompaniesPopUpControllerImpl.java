package ru.aisa.companyregister.ui;

import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.CompanyGenericDAOImpl;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.entity.Company;

import java.util.List;

public class CompaniesPopUpControllerImpl implements AbstractPopUpController<Company>
{
    private GenericDAO<Company> companySql = new CompanyGenericDAOImpl();
    private List<Company> companies;
    private ComboBox boxEditCompanies = new ComboBox();
    private ComboBox boxDeleteCompanies = new ComboBox();
    ColumnsValidatorMapper columnValidator = new CompaniesValidatorMapperImpl();
    Button buttonAdd, buttonEdit, buttonDelete, buttonCancelAdd, buttonCancelEdit, buttonCancelDelete;

    @Override
    public void init(Layout layout)
    {
        this.updateCompaniesCollection();
        boxEditCompanies.setImmediate(true);
        boxDeleteCompanies.setImmediate(true);
        buttonAdd = new Button("Добавить");
        buttonEdit = new Button("Редактировать");
        buttonDelete = new Button("Удалить");
        buttonCancelAdd = new Button("Отмена");
        buttonCancelEdit = new Button("Отмена");
        buttonCancelDelete = new Button("Отмена");
    }

    /**
     * Обновляет только коллекцию с компаниями
     */
    public void updateCompaniesCollection()
    {
        this.companies = companySql.readAll();
    }

    public Company getCompanyFromName(String companyName, List<Company> companies)
    {
        for (Company company : companies)
        {
            if (company.getCompanyName().equals(companyName))
                return company;
        }
        return null;
    }

    public GenericDAO<Company> getCompanySql()
    {
        return companySql;
    }

    public void setCompanySql(GenericDAO<Company> companySql)
    {
        this.companySql = companySql;
    }

    public List<Company> getCompanies()
    {
        return companies;
    }

    public void setCompanies(List<Company> companies)
    {
        this.companies = companies;
    }


    public ComboBox getBoxEditCompanies()
    {
        return boxEditCompanies;
    }

    public void setBoxEditCompanies(ComboBox boxEditCompanies)
    {
        this.boxEditCompanies = boxEditCompanies;
    }

    @Override
    public void updateItemData()
    {
        this.companies = companySql.readAll();
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
                int code = companySql.create(employee);
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
    public void displayEditItem(Layout layout, Company item)
    {
        layout.addComponent(boxEditCompanies);
        layout.addComponent(buttonEdit);
        buttonEdit.addClickListener(event ->
        {
            if (getCompanyFromName(boxEditCompanies.getValue().toString(), companies) != null && !boxEditCompanies.isReadOnly())
            {
                boxEditCompanies.setReadOnly(true);
                buttonEdit.setEnabled(false);
                Company company = getCompanyFromName(boxEditCompanies.getValue().toString(), companies);
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
                        companySql.updateById(company, company.getId());
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
    public void displayDeleteItem(Layout layout, Company item)
    {
        layout.addComponent(boxDeleteCompanies);
        Button buttonChoose = new Button("Выбрать");
        layout.addComponent(buttonChoose);
        buttonChoose.addClickListener(event ->
        {
            if (getCompanyFromName(boxDeleteCompanies.getValue().toString(), companies) != null && !boxDeleteCompanies.isReadOnly())
            {
                boxDeleteCompanies.setReadOnly(true);
                boxDeleteCompanies.setEnabled(false);
                Company company = getCompanyFromName(boxDeleteCompanies.getValue().toString(), companies);
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
                    companySql.deleteByID(company.getId());
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
