package ex.talosdx.companyregister.ui.popup;

import com.vaadin.ui.*;
import ex.talosdx.companyregister.dao.entities.Company;
import ex.talosdx.companyregister.dao.wrapper.enties.GenericDAO;
import ex.talosdx.companyregister.ui.validators.ColumnsValidatorMapper;
import ex.talosdx.companyregister.ui.validators.CompaniesValidatorMapperImpl;
import ex.talosdx.companyregister.utils.LazyUtils;
import ex.talosdx.companyregister.dao.entities.Employee;
import ex.talosdx.companyregister.ui.pages.MainPage;

import java.util.List;

public class CompaniesPopUpControllerImpl extends AbstractPopUpController<Company>
{
    private final ColumnsValidatorMapper columnValidator = new CompaniesValidatorMapperImpl();
    private GenericDAO employeeDAO;
    MainPage ui;

    public CompaniesPopUpControllerImpl(GenericDAO<Company> companiesDAO, GenericDAO<Employee> employeeDAO, MainPage ui)
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
            LazyUtils.clearFields(new TextField[]{companyField, innField, addressField, phoneField});
            window.close();
        });
        saveButton.addClickListener(event1 ->
        {
            if (companyField.isValid() && innField.isValid() && addressField.isValid() && phoneField.isValid())
            {
                Company employee = new Company(companyField.getValue(), Long.valueOf(innField.getValue()), addressField.getValue(), phoneField.getValue());
                int code = this.genericDAO.create(employee);
                updateItemData();
                LazyUtils.clearFields(new TextField[]{companyField, innField, addressField, phoneField});
                if (code == 1)
                    Notification.show("Компания: " + employee.getCompanyName() + " успешно добавлена!");
                window.close();
            }
            else LazyUtils.showErrorWithFields(new Field[]{companyField, innField, addressField, phoneField});
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
            LazyUtils.clearAction(layout, new Component[]{companyField, innField, addressField, phoneField, saveButton, cancelButton});
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
                LazyUtils.clearAction(layout, new Component[]{companyField, innField, addressField, phoneField, saveButton, cancelButton});
                window.close();
            }
            else LazyUtils.showErrorWithFields(new Field[]{companyField, innField, addressField, phoneField});
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
            LazyUtils.clearAction(ownContent, new Component[]{companyField, innField, addressField, phoneField, deleteButton, cancelButton});
            window.close();
        });
        deleteButton.addClickListener(event1 ->
        {
            this.getDAO().deleteByID(company.getId());
            updateItemData();
            LazyUtils.clearAction(ownContent, new Component[]{companyField, innField, addressField, phoneField, deleteButton, cancelButton});
            window.close();
        });
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponents(cancelButton, deleteButton);
        ownContent.addComponents(companyField, innField, addressField, phoneField, buttonLayout);
    }
}
