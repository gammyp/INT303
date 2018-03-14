/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author INT303
 */
public class Cart {

    private Map<OrderDetailPK, OrderDetail> cart;
    
    public Collection<OrderDetail> getOrders() {
        return cart.values();
    }

    public boolean isEmtpy() {
        return cart == null || this.cart.size() == 0;
    }

    public void addItem(OrderDetail orderdetail) {
        if (this.cart == null) {
            this.cart = new HashMap(32, 0.5f);
        }
        if(orderdetail.getQuantityordered() == 0) {
            cart.remove(orderdetail.getOrderDetailPK());
            return ;
        }
        OrderDetail temp = cart.get(orderdetail.getOrderDetailPK());
        if(temp == null) {
            this.cart.put(orderdetail.getOrderDetailPK(), orderdetail);
        }
        else {
            temp.setQuantityordered(temp.getQuantityordered() + orderdetail.getQuantityordered());
        }
    }

    public OrderDetail getItem(OrderDetailPK orderDetailPK) {
        return this.cart.get(orderDetailPK);
    }

    public int getSize() {
        if(this.cart == null)
            return 0;
        return this.cart.size();
    }

    public double getTotalPrice() {
        Collection<OrderDetail> orderDetails = cart.values();
        double sum = 0;
        for (OrderDetail orderDetail : orderDetails) {
            sum += orderDetail.getQuantityordered()*orderDetail.getPriceeach().doubleValue();
        }
        return sum;
    }

    public void remove(OrderDetailPK orderdetailPK) {
        cart.remove(orderdetailPK);
    }
    
}
