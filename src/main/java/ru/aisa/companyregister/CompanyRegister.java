package ru.aisa.companyregister;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import ru.aisa.companyregister.database.dao.EmployeeGenericDAOImpl;
import ru.aisa.companyregister.database.dao.GenericDAO;
import ru.aisa.companyregister.entity.Employee;
import ru.aisa.companyregister.ui.CompaniesController;
import ru.aisa.companyregister.ui.EmployeeController;

import java.util.List;

public class CompanyRegister extends UI
{
    CompaniesController companiesController = new CompaniesController();
    EmployeeController employeeController = new EmployeeController();

    GenericDAO<Employee> employeeSql = new EmployeeGenericDAOImpl();

    @Override
    protected void init(VaadinRequest request)
    {
        this.getPage().setTitle("Реестр компании и сотрудников");
        final VerticalLayout ownLayout = new VerticalLayout();
        ownLayout.setMargin(true);
        setContent(ownLayout);
        createCountLabel(ownLayout);
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);
        employeeController.init();
        Button employeeButton = new Button("Сотрудники");
        employeeButton.addClickListener(event -> addWindow(employeeController.getWindowEmployees()));
        ownLayout.addComponent(buttonsLayout);
        companiesController.init();
        Button companiesButton = new Button("Компании");
        companiesButton.addClickListener(event -> addWindow(companiesController.getWindowCompanies()));
        buttonsLayout.addComponents(companiesButton);
        buttonsLayout.addComponent(employeeButton);

    }

    private void createCountLabel(VerticalLayout ownLayout)
    {
        Label countCompany = new Label("Общее количество зарегестрированных компаний: " + companiesController.getCompanySql().getCount());
        Label countEmployee = new Label("Общее количество зарегестрированных сотрудников: " + employeeSql.getCount());
        ownLayout.addComponents(countCompany, countEmployee);
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
