package sit.int303.demo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sit.int303.demo.model.Employee;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-14T14:14:04")
@StaticMetamodel(Office.class)
public class Office_ { 

    public static volatile SingularAttribute<Office, String> country;
    public static volatile ListAttribute<Office, Employee> employeeList;
    public static volatile SingularAttribute<Office, String> city;
    public static volatile SingularAttribute<Office, String> phone;
    public static volatile SingularAttribute<Office, String> postalcode;
    public static volatile SingularAttribute<Office, String> officecode;
    public static volatile SingularAttribute<Office, String> addressline2;
    public static volatile SingularAttribute<Office, String> addressline1;
    public static volatile SingularAttribute<Office, String> state;
    public static volatile SingularAttribute<Office, String> territory;

}