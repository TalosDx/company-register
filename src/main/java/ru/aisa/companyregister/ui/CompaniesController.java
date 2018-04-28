package ru.aisa.companyregister.ui;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.CompanyGenericDAOImpl;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.entity.Company;

import java.util.List;

public class CompaniesController
{
    private GenericDAO<Company> companySql = new CompanyGenericDAOImpl();
    private List<Company> companies;
    private Table tableCompanies = new Table();
    private ComboBox boxEditCompanies = new ComboBox();
    private ComboBox boxDeleteCompanies = new ComboBox();

    Window windowCompanies;


    public void init()
    {
        windowCompanies = this.createWindowCompanies();
        FormLayout formCompanies = new FormLayout();
        windowCompanies.setContent(formCompanies);
        TabSheet tabsCompany = new TabSheet();
        tabsCompany.setHeight("100%");
        formCompanies.addComponent(tabsCompany);

        this.updateCompaniesCollection();
        this.createCompanyTable();

        final VerticalLayout tabCompanyList = new VerticalLayout();
        final HorizontalLayout tabCompanyEditHorizontal = new HorizontalLayout();
        final VerticalLayout tabCompanyEditVertical = new VerticalLayout();
        final Layout tabCompanyAddVertical = new VerticalLayout();
        final Layout tabCompanyRemoveVertical = new VerticalLayout();
        boxEditCompanies.setImmediate(true);
        boxDeleteCompanies.setImmediate(true);

        tabCompanyList.setMargin(true);
        tabsCompany.addTab(tabCompanyList, "Список компаний");

        tabsCompany.addTab(tabCompanyEditVertical, "Редактирование компаний");


        tabsCompany.addTab(tabCompanyAddVertical, "Добавить компанию");
        createAddCompanyButton(tabCompanyAddVertical);


        tabsCompany.addTab(tabCompanyRemoveVertical, "Удалить компанию");
        createRemoveCompanyButton(tabCompanyRemoveVertical);


        tabsCompany.setSizeFull();
        this.getBoxEditCompanies().setNullSelectionAllowed(false);
        tabCompanyList.addComponent(this.getTableCompanies());
        this.getTableCompanies().setHeight("100%");
        tabCompanyEditVertical.addComponent(tabCompanyEditHorizontal);
        tabCompanyEditHorizontal.addComponent(this.getBoxEditCompanies());
        Button buttonEdit = new Button("Редактировать");
        tabCompanyEditHorizontal.addComponent(buttonEdit);

        this.addClickCompanyEditButton(buttonEdit, tabCompanyEditVertical);

    }


    public Window getWindowCompanies()
    {
        if (windowCompanies == null)
            throw new IllegalStateException("CompaniesController not initialized!");
        return windowCompanies;
    }

    public Window createWindowCompanies()
    {
        Window windowCompanies = new Window("Компании");
        windowCompanies.setWidth(800, Sizeable.Unit.PIXELS);
        windowCompanies.setHeight(400, Sizeable.Unit.PIXELS);
        windowCompanies.setClosable(true);
        windowCompanies.setResizable(false);
        return windowCompanies;
    }

    public Table createCompanyTable()
    {
        tableCompanies.setSelectable(false);
        tableCompanies.setImmediate(true);

        tableCompanies.addContainerProperty("Имя Компании", String.class, null);
        tableCompanies.addContainerProperty("ИНН", Long.class, null);
        tableCompanies.addContainerProperty("Адрес", String.class, null);
        tableCompanies.addContainerProperty("Телефон", String.class, null);

        updateCompaniesTable();
        tableCompanies.setPageLength(tableCompanies.size());
        return tableCompanies;
    }

    /**
     * Обновляет только коллекцию с компаниями
     */
    public void updateCompaniesCollection()
    {
        this.companies = companySql.readAll();
    }

    /**
     * Обновляет данные в таблице, и остальных зависимых компонентах
     */
    public void updateCompaniesTable()
    {
        this.companies = companySql.readAll();
        tableCompanies.removeAllItems();
        for (int i = 0; i < companies.size(); i++)
        {
            Company company = companies.get(i);
            tableCompanies.addItem(new Object[]{company.getCompanyName(), company.getInn(), company.getAddress(), company.getPhone()}, i);
        }
        boxEditCompanies.removeAllItems();
        for (int i = 0; i < companies.size(); i++)
            boxEditCompanies.addItem(companies.get(i).getCompanyName());

        boxDeleteCompanies.removeAllItems();
        for (int i = 0; i < companies.size(); i++)
            boxDeleteCompanies.addItem(companies.get(i).getCompanyName());
    }

    public void createRemoveCompanyButton(Layout layout)
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
                    updateCompaniesTable();
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


    public void createAddCompanyButton(Layout layout)
    {
        {
            TextField companyField = new TextField();
            companyField.setCaption("Имя компании:");
            companyField.setWidth("200px");
            companyField.setMaxLength(120);
            companyField.addValidator(new AbstractStringValidator("Любые символы кроме цифр")
            {
                @Override
                protected boolean isValidValue(String value)
                {
                    return value.matches("[^0-9]+");
                }
            });

            TextField innField = new TextField();
            innField.setCaption("ИНН");
            innField.setWidth("200px");
            innField.setMaxLength(12);
            innField.addValidator(new AbstractStringValidator("Только цифры")
            {
                @Override
                protected boolean isValidValue(String value)
                {
                    return value.matches("[0-9]+");
                }
            });

            TextField addressField = new TextField();
            addressField.setCaption("Адрес");
            addressField.setWidth("200px");
            addressField.setMaxLength(250);

            TextField phoneField = new TextField();
            phoneField.setCaption("Номер телефона:");
            phoneField.setWidth("200px");
            phoneField.setMaxLength(15);
            phoneField.addValidator(new AbstractStringValidator("Формат ввода телефона: +7952-124-14-15")
            {
                @Override
                protected boolean isValidValue(String value)
                {
                    return value.matches("\\+[7-8][8-9][0-9]{2}\\-[0-9]{3}\\-[0-9]{2}\\-[0-9]{2}");
                }
            });

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
                    updateCompaniesTable();
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
    }


    public Button addClickCompanyEditButton(Button buttonEdit, VerticalLayout layout)
    {
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
                companyField.addValidator(new AbstractStringValidator("Любые символы кроме цифр")
                {
                    @Override
                    protected boolean isValidValue(String value)
                    {
                        return value.matches("[^0-9]+");
                    }
                });

                TextField innField = new TextField();
                innField.setCaption("ИНН");
                innField.setValue(String.valueOf(company.getInn()));
                innField.setWidth("200px");
                innField.setMaxLength(12);
                innField.addValidator(new AbstractStringValidator("Только цифры")
                {
                    @Override
                    protected boolean isValidValue(String value)
                    {
                        return value.matches("[0-9]+");
                    }
                });

                TextField addressField = new TextField();
                addressField.setCaption("Адрес");
                addressField.setValue(company.getAddress());
                addressField.setWidth("200px");
                addressField.setMaxLength(250);

                TextField phoneField = new TextField();
                phoneField.setCaption("Номер телефона:");
                phoneField.setValue(company.getPhone());
                phoneField.setWidth("200px");
                phoneField.setMaxLength(15);
                phoneField.addValidator(new AbstractStringValidator("Формат ввода телефона: +7952-124-14-15")
                {
                    @Override
                    protected boolean isValidValue(String value)
                    {
                        return value.matches("\\+[7-8][8-9][0-9]{2}\\-[0-9]{3}\\-[0-9]{2}\\-[0-9]{2}");
                    }
                });

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
                        updateCompaniesTable();
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
        return buttonEdit;
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

    public Table getTableCompanies()
    {
        return tableCompanies;
    }

    public void setTableCompanies(Table tableCompanies)
    {
        this.tableCompanies = tableCompanies;
    }

    public ComboBox getBoxEditCompanies()
    {
        return boxEditCompanies;
    }

    public void setBoxEditCompanies(ComboBox boxEditCompanies)
    {
        this.boxEditCompanies = boxEditCompanies;
    }

}
