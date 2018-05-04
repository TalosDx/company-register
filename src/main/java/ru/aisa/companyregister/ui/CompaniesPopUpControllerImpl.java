package ru.aisa.companyregister.ui;

import com.vaadin.ui.*;
import ru.aisa.companyregister.CompanyRegister;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.database.dao.entities.Company;
import ru.aisa.companyregister.database.dao.entities.Employee;
import ru.aisa.companyregister.ui.validator.ColumnsValidatorMapper;
import ru.aisa.companyregister.ui.validator.CompaniesValidatorMapperImpl;
import ru.aisa.companyregister.utils.LazyUtils;

import java.util.List;

public class CompaniesPopUpControllerImpl extends AbstractPopUpController<Company>
{
    private final ColumnsValidatorMapper columnValidator = new CompaniesValidatorMapperImpl();
    private GenericDAO employeeDAO;
    CompanyRegister ui;

    public CompaniesPopUpControllerImpl(GenericDAO<Company> companiesDAO, GenericDAO<Employee> employeeDAO, CompanyRegister ui)
    {
        super(companiesDAO, ui.companyBeanItemContainer);
        this.employeeDAO = employeeDAO;
        this.ui = ui;
    }

    @Override
    public void init(Window window)
    {}

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
        ui.updateGrids();
    }

    @Override
    public void displayAddItem(Window window)
    {
        VerticalLayout ownContent = new VerticalLayout();
        ((Layout) window.getContent()).addComponent(ownContent);
        ownContent.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        ownContent.setMargin(true);
        ownContent.setSpacing(true);

        TextField companyField = new TextField();
        companyField.setCaption(LazyUtils.getLangProperties("companyName") + ": ");
        companyField.setWidth("200px");
        companyField.setMaxLength(120);
        companyField.addValidator(columnValidator.getValidatorFromColumn("company_name"));

        TextField innField = new TextField();
        innField.setCaption(LazyUtils.getLangProperties("inn") + ": ");
        innField.setWidth("200px");
        innField.setMaxLength(12);
        innField.addValidator(columnValidator.getValidatorFromColumn("inn"));

        TextField addressField = new TextField();
        addressField.setCaption(LazyUtils.getLangProperties("address") + ": ");
        addressField.setWidth("200px");
        addressField.addValidator(columnValidator.getValidatorFromColumn("address"));
        addressField.setMaxLength(250);

        TextField phoneField = new TextField();
        phoneField.setCaption(LazyUtils.getLangProperties("phone") + ": ");
        phoneField.setWidth("200px");
        phoneField.setMaxLength(15);
        phoneField.addValidator(columnValidator.getValidatorFromColumn("phone"));

        Button saveButton = new Button(LazyUtils.getLangProperties("button.save"));
        Button cancelButton = new Button(LazyUtils.getLangProperties("button.cancel"));
        cancelButton.addClickListener(event1 ->
        {
            clearFields(new TextField[]{companyField, innField, addressField, phoneField});
            window.close();
        });
        saveButton.addClickListener(event1 ->
        {
            if (companyField.isValid() && innField.isValid() && addressField.isValid() && phoneField.isValid())
            {
                Company employee = new Company(companyField.getValue(), Long.valueOf(innField.getValue()), addressField.getValue(), phoneField.getValue());
                int code = this.genericDAO.create(employee);
                updateItemData();
                clearFields(new TextField[]{companyField, innField, addressField, phoneField});
                if (code == 1)
                    Notification.show("Компания: " + employee.getCompanyName() + " успешно добавлена!");
                window.close();
            }
            else showErrorWithFields(new Field[]{companyField, innField, addressField, phoneField});
        });
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponents(cancelButton, saveButton);
        ownContent.addComponents(companyField, innField, addressField, phoneField, buttonLayout);

    }


    @Override
    public void displayEditItem(Window window, Company company)
    {
        VerticalLayout ownContent = new VerticalLayout();
        ((Layout) window.getContent()).addComponent(ownContent);
        ownContent.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        ownContent.setMargin(true);
        ownContent.setSpacing(true);

        Layout layout = (Layout) window.getContent();
        TextField companyField = new TextField();
        companyField.setCaption(LazyUtils.getLangProperties("companyName") + ": ");
        companyField.setValue(company.getCompanyName());
        companyField.setWidth("200px");
        companyField.setMaxLength(120);
        companyField.addValidator(columnValidator.getValidatorFromColumn("company_name"));

        TextField innField = new TextField();
        innField.setCaption(LazyUtils.getLangProperties("inn") + ": ");
        innField.setValue(String.valueOf(company.getInn()));
        innField.setWidth("200px");
        innField.setMaxLength(12);
        innField.addValidator(columnValidator.getValidatorFromColumn("inn"));

        TextField addressField = new TextField();
        addressField.setCaption(LazyUtils.getLangProperties("address") + ": ");
        addressField.setValue(company.getAddress());
        addressField.setWidth("200px");
        addressField.setMaxLength(250);
        addressField.addValidator(columnValidator.getValidatorFromColumn("address"));

        TextField phoneField = new TextField();
        phoneField.setCaption(LazyUtils.getLangProperties("phone") + ": ");
        phoneField.setValue(company.getPhone());
        phoneField.setWidth("200px");
        phoneField.setMaxLength(15);
        phoneField.addValidator(columnValidator.getValidatorFromColumn("phone"));


        Button saveButton = new Button(LazyUtils.getLangProperties("button.save"));
        Button cancelButton = new Button(LazyUtils.getLangProperties("button.cancel"));
        cancelButton.addClickListener(event1 ->
        {
            super.clearAction(layout, new Component[]{companyField, innField, addressField, phoneField, saveButton, cancelButton});
            window.close();
        });
        saveButton.addClickListener(event1 ->
        {
            if (companyField.isValid() && innField.isValid() && addressField.isValid() && phoneField.isValid())
            {
                company.setCompanyName(companyField.getValue());
                company.setInn(Long.parseLong(innField.getValue()));
                company.setAddress(addressField.getValue());
                company.setPhone(phoneField.getValue());
                this.getDAO().updateById(company, company.getId());
                updateItemData();
                super.clearAction(layout, new Component[]{companyField, innField, addressField, phoneField, saveButton, cancelButton});
                window.close();
            }
            else showErrorWithFields(new Field[]{companyField, innField, addressField, phoneField});
        });

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponents(cancelButton, saveButton);
        ownContent.addComponents(companyField, innField, addressField, phoneField, buttonLayout);
    }

    @Override
    public void displayDeleteItem(Window window, Company company)
    {
        VerticalLayout ownContent = new VerticalLayout();
        ((Layout) window.getContent()).addComponent(ownContent);
        ownContent.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        ownContent.setMargin(true);
        ownContent.setSpacing(true);

        TextField companyField = new TextField();
        companyField.setCaption(LazyUtils.getLangProperties("companyName") + ": ");
        companyField.setValue(company.getCompanyName());
        companyField.setReadOnly(true);
        companyField.setWidth("200px");

        TextField innField = new TextField();
        innField.setCaption(LazyUtils.getLangProperties("inn") + ": ");
        innField.setValue(String.valueOf(company.getInn()));
        innField.setReadOnly(true);
        innField.setWidth("200px");


        TextField addressField = new TextField();
        addressField.setCaption(LazyUtils.getLangProperties("address") + ": ");
        addressField.setValue(company.getAddress());
        addressField.setReadOnly(true);
        addressField.setWidth("200px");

        TextField phoneField = new TextField();
        phoneField.setCaption(LazyUtils.getLangProperties("phone") + ": ");
        phoneField.setValue(company.getPhone());
        phoneField.setReadOnly(true);
        phoneField.setWidth("200px");

        Button deleteButton = new Button(LazyUtils.getLangProperties("button.delete"));
        Button cancelButton = new Button(LazyUtils.getLangProperties("button.cancel"));
        cancelButton.addClickListener(event1 ->
        {
            super.clearAction(ownContent, new Component[]{companyField, innField, addressField, phoneField, deleteButton, cancelButton});
            window.close();
        });
        deleteButton.addClickListener(event1 ->
        {
            this.getDAO().deleteByID(company.getId());
            updateItemData();
            super.clearAction(ownContent, new Component[]{companyField, innField, addressField, phoneField, deleteButton, cancelButton});
            window.close();
        });
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponents(cancelButton, deleteButton);
        ownContent.addComponents(companyField, innField, addressField, phoneField, buttonLayout);
    }
}
