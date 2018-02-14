/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author INT303
 */
public class Cart {

    private Map<OrderdetailPK, Orderdetail> cart;

    public boolean isEmtpy() {
        return cart == null || this.cart.size() == 0;
    }

    public void addItem(Orderdetail orderdetail) {
        if (this.cart == null) {
            this.cart = new HashMap(32, 0.5f);
        }
        if(orderdetail.getQuantityordered() == 0) {
            cart.remove(orderdetail.getOrderdetailPK());
            return ;
        }
        Orderdetail temp = cart.get(orderdetail.getOrderdetailPK());
        if(temp == null) {
            this.cart.put(orderdetail.getOrderdetailPK(), orderdetail);
        }
        else {
            temp.setQuantityordered(temp.getQuantityordered() + orderdetail.getQuantityordered());
        }
    }

    public Orderdetail getItem(OrderdetailPK orderDetailPK) {
        return this.cart.get(orderDetailPK);
    }

    public int getSize() {
        if(this.cart == null)
            return 0;
        return this.cart.size();
    }

    public double getTotalPrice() {
        Collection<Orderdetail> orderDetails = cart.values();
        double sum = 0;
        for (Orderdetail orderDetail : orderDetails) {
            sum += orderDetail.getQuantityordered()*orderDetail.getPriceeach().doubleValue();
        }
        return sum;
    }

    public void remove(OrderdetailPK orderdetailPK) {
        cart.remove(orderdetailPK);
    }
    
}
