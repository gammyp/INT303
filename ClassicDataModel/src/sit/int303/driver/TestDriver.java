/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.driver;

import java.util.List;
import java.util.Scanner;
import sit.int303.model.MyCustomer;
import sit.int303.model.controller.MyCustomerController;

/**
 *
 * @author INT303
 */
public class TestDriver {
    public static void main(String[] args) {
        testFindAll();
    }
    
    public static void testFindAll() {
        MyCustomerController customerController = new MyCustomerController();
        List<MyCustomer> customers = customerController.findAll();
        if (customers != null) {
            for (MyCustomer customer : customers) {
                System.out.println(customer.getCustomerName() + " " + customer.getCreditLimit());
            }
        }
    }
    
    public static void testFindById() {
        Scanner sc = new Scanner(System.in);
        MyCustomerController customerController = new MyCustomerController();
        int customerId = 0;
        do {
            System.out.print("Enter customer id to search (0 to exit): ");
            customerId = sc.nextInt();
            if (customerId > 0) {
                MyCustomer c = customerController.findById(customerId);
                if(c != null) {
                    System.out.println("Customer number: " + c.getCustomerNumber());
                    System.out.println("Customer name: " + c.getCustomerNumber());
                    System.out.println("Credit Limit: " + c.getCreditLimit());
                }
                else {
                    System.out.println("Customer number: " + customerId + " does not exit");
                }
            }
        } while (customerId > 0);
    }
}
