package sit.int303.demo.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sit.int303.demo.model.OrderDetailPK;
import sit.int303.demo.model.Orders;
import sit.int303.demo.model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-14T14:14:04")
@StaticMetamodel(OrderDetail.class)
public class OrderDetail_ { 

    public static volatile SingularAttribute<OrderDetail, Product> product;
    public static volatile SingularAttribute<OrderDetail, BigDecimal> priceeach;
    public static volatile SingularAttribute<OrderDetail, Orders> orders;
    public static volatile SingularAttribute<OrderDetail, Short> orderlinenumber;
    public static volatile SingularAttribute<OrderDetail, Integer> quantityordered;
    public static volatile SingularAttribute<OrderDetail, OrderDetailPK> orderDetailPK;

}