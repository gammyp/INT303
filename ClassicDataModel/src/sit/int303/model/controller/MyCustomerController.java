/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.model.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sit.int303.model.MyCustomer;

/**
 *
 * @author INT303
 */
public class MyCustomerController {
    private final static String FIND_ALL = "select * from customer c";
    private final static String FIND_ID = "select * from customer c where c.customernumber = ?";
    
    public MyCustomer findById(int customerId) {
        Connection conn = ConnectionFactory.getConnection();
        try {
            PreparedStatement pstm = conn.prepareStatement(FIND_ID);
            pstm.setInt(1, customerId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return map(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }
    
    public List<MyCustomer> findAll() {
        Connection conn = ConnectionFactory.getConnection();
        List<MyCustomer> customerList = new ArrayList();
        try {
            PreparedStatement pstm = conn.prepareStatement(FIND_ALL);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                customerList.add(map(rs));
            }
            return customerList;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }

    private MyCustomer map(ResultSet rs) throws SQLException {
        MyCustomer c = new MyCustomer();
        c.setCustomerNumber(rs.getInt("customernumber"));
        c.setCustomerName(rs.getString("customername"));
        c.setCreditLimit(rs.getDouble("creditlimit"));
        return c;
    }
}
