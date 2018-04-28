package ru.aisa.companyregister;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.EmployeeGenericDAOImpl;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.entity.Company;
import ru.aisa.companyregister.entity.Employee;
import ru.aisa.companyregister.ui.AbstractPopUpController;
import ru.aisa.companyregister.ui.CompaniesPopUpControllerImpl;
import ru.aisa.companyregister.ui.EmployeePopUpControllerImpl;

import java.util.List;

public class CompanyRegister extends UI
{
    AbstractPopUpController controller;
    AbstractPopUpController<Company> companiesController = new CompaniesPopUpControllerImpl();
    AbstractPopUpController<Employee> employeeController = new EmployeePopUpControllerImpl();


    final VerticalLayout ownContent = new VerticalLayout();
    GenericDAO<Employee> employeeSql = new EmployeeGenericDAOImpl();

    private Grid gridEmployee = new Grid();
    TabSheet contentTabs = new TabSheet();

    @Override
    protected void init(VaadinRequest request)
    {
        this.getPage().setTitle("Реестр компании и сотрудников");
        setContent(ownContent);
        final HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button addItem = new Button();
        addItem.addClickListener(event -> actionAddItem(controller));
        Button editItem = new Button();
        editItem.addClickListener(event -> actionEditItem(controller));
        Button deleteItem =new Button();
        deleteItem.addClickListener(event -> actionDeleteItem(controller));

        buttonsLayout.addComponents(addItem, editItem, deleteItem);
        ownContent.addComponent(buttonsLayout);
        ownContent.addComponent(contentTabs);
        final VerticalLayout employeeLayout = new VerticalLayout();
        final VerticalLayout companiesLayout = new VerticalLayout();
        buttonsLayout.setSpacing(true);

        contentTabs.setHeight("100%");

        contentTabs.addTab(companiesLayout, "Компании");
        companiesController.init(companiesLayout);

        contentTabs.addTab(employeeLayout, "Сотрудники");
        employeeController.init(employeeLayout);



    }


    private Window actionAddItem(AbstractPopUpController controller)
    {
        String str = "";
        if(contentTabs.getSelectedTab().getCaption().equals("Компании"))
        {
            str = "компании";
        }
        if(contentTabs.getSelectedTab().getCaption().equals("Сотрудники"))
        {
            str = "сотрудника";
        }
        Window addItem = new Window("Добавление " + str);
        addItem.setResizable(false);
        addItem.setImmediate(true);
        VerticalLayout tabItemAddVertical = new VerticalLayout();
        addItem.setContent(tabItemAddVertical);
        //ui.addWindow(addCompany);

        return addItem;
    }

    private void actionEditItem(AbstractPopUpController controller)
    {
        Window editCompany = new Window("Добавление компании");
        editCompany.setResizable(false);
        editCompany.setImmediate(true);
        //editCompany.setContent(tabCompanyEditVertical);
        //ui.addWindow(editCompany);
    }

    private void actionDeleteItem(AbstractPopUpController controller)
    {
        Window removeCompany = new Window("Удаление компании");
        removeCompany.setResizable(false);
        removeCompany.setImmediate(true);
        //ui.addWindow(removeCompany);
        //createRemoveCompanyButton(tabCompanyRemoveVertical);
    }


    //Уровень реализации
    public Employee getEmployeeFromName(String employeeName, List<Employee> employees)
    {
        for(Employee employee : employees)
        {
            if(employee.getCompanyName().equals(employeeName))
                return employee;
        }
        return null;
    }
}
