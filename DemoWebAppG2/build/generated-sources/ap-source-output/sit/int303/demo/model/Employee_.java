package sit.int303.demo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sit.int303.demo.model.Customer;
import sit.int303.demo.model.Employee;
import sit.int303.demo.model.Office;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-21T14:55:03")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, String> firstname;
    public static volatile SingularAttribute<Employee, String> extension;
    public static volatile SingularAttribute<Employee, Integer> employeenumber;
    public static volatile SingularAttribute<Employee, String> jobtitle;
    public static volatile ListAttribute<Employee, Employee> employeeList;
    public static volatile SingularAttribute<Employee, Office> officecode;
    public static volatile SingularAttribute<Employee, Employee> reportsto;
    public static volatile ListAttribute<Employee, Customer> customerList;
    public static volatile SingularAttribute<Employee, String> email;
    public static volatile SingularAttribute<Employee, String> lastname;

}