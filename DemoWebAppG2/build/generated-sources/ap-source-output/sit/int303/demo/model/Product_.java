package sit.int303.demo.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sit.int303.demo.model.OrderDetail;
import sit.int303.demo.model.ProductLine;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-21T14:55:03")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, String> productscale;
    public static volatile SingularAttribute<Product, ProductLine> productline;
    public static volatile ListAttribute<Product, OrderDetail> orderDetailList;
    public static volatile SingularAttribute<Product, String> productcode;
    public static volatile SingularAttribute<Product, String> productvendor;
    public static volatile SingularAttribute<Product, String> productdescription;
    public static volatile SingularAttribute<Product, BigDecimal> buyprice;
    public static volatile SingularAttribute<Product, Short> quantityinstock;
    public static volatile SingularAttribute<Product, BigDecimal> msrp;
    public static volatile SingularAttribute<Product, String> productname;

}