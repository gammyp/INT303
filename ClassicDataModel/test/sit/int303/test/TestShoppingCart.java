/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.test;

import java.math.BigDecimal;
import sit.int303.model.Cart;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sit.int303.model.Orderdetail;
import sit.int303.model.OrderdetailPK;

/**
 *
 * @author INT303
 */
public class TestShoppingCart {
    
    Cart cart = new Cart();
    
    public TestShoppingCart() {
    }
    
    @Test
    public void MustEmtpyWhenNew() {
        assertTrue("BUG Why is not empty", cart.isEmtpy());
    }
    
    @Test
    public void MustNotEmptryWhenAddOne() {
        Orderdetail od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(1);
        cart.addItem(od1);
        assertFalse("BUG Why is empty", cart.isEmtpy());
    }
    
    @Test
    public void addMustExist() {
        Orderdetail od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(1);
        cart.addItem(od1);
        OrderdetailPK orderDetailPK = new OrderdetailPK(10001, "P-001");
        Orderdetail actual = cart.getItem(orderDetailPK);
        assertEquals(od1, actual);
    }
    
    @Test
    public void cartSizeNotGrow() {
        Orderdetail od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(1);
        cart.addItem(od1);
        cart.addItem(od1);
        int actual = cart.getSize();
        assertEquals(1, actual);
    }
    
    @Test
    public void quantityMustEqualNumberAdd() {
        Orderdetail od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(1);
        cart.addItem(od1);
        od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(1);
        cart.addItem(od1);
        od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(1);
        cart.addItem(od1);
        od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(1);
        cart.addItem(od1);
        od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(1);
        cart.addItem(od1);
        OrderdetailPK orderDetailPK = new OrderdetailPK(10001, "P-001");
        Orderdetail actual = cart.getItem(orderDetailPK);
        assertEquals(5, actual.getQuantityordered());
    }
    
    @Test
    public void totalPriceEqualsumQty() {
        
        Orderdetail od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(1);
        od1.setPriceeach(new BigDecimal(100.0));
        cart.addItem(od1);
        
        od1 = new Orderdetail(10002, "P-002");
        od1.setQuantityordered(2);
        od1.setPriceeach(new BigDecimal(200.0));
        cart.addItem(od1);
        
        od1 = new Orderdetail(10003, "P-003");
        od1.setQuantityordered(10);
        od1.setPriceeach(new BigDecimal(300.0));
        cart.addItem(od1);
        
        assertEquals(3500.0, cart.getTotalPrice(), 0.01);
    }
    
    @Test
    public void removeItemInCart() {
        
        Orderdetail od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(1);
        od1.setPriceeach(new BigDecimal(100.0));
        cart.addItem(od1);
        
        od1 = new Orderdetail(10002, "P-002");
        od1.setQuantityordered(2);
        od1.setPriceeach(new BigDecimal(200.0));
        cart.addItem(od1);
        
        od1 = new Orderdetail(10003, "P-003");
        od1.setQuantityordered(10);
        od1.setPriceeach(new BigDecimal(300.0));
        cart.addItem(od1);
        
        cart.remove(new OrderdetailPK(10001, "P-001"));
        
        assertEquals(null, cart.getItem(new OrderdetailPK(10001, "P-001")));
        assertEquals(2, cart.getSize());
    }
    
    @Test
    public void removeItemFromCartWhenQtyZero() {
        
        Orderdetail od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(1);
        od1.setPriceeach(new BigDecimal(100.0));
        cart.addItem(od1);
        
        od1 = new Orderdetail(10002, "P-002");
        od1.setQuantityordered(2);
        od1.setPriceeach(new BigDecimal(200.0));
        cart.addItem(od1);
        
        od1 = new Orderdetail(10003, "P-003");
        od1.setQuantityordered(10);
        od1.setPriceeach(new BigDecimal(300.0));
        cart.addItem(od1);
        
        od1 = new Orderdetail(10001, "P-001");
        od1.setQuantityordered(0);
        cart.addItem(od1);
        
        assertEquals(null, cart.getItem(new OrderdetailPK(10001, "P-001")));
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
}
