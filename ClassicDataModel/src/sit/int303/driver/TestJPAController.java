/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.driver;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sit.int303.model.Customer;
import sit.int303.model.controller.CustomerJpaController;

/**
 *
 * @author INT303
 */
public class TestJPAController {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClassicDataModelPU");
        CustomerJpaController customerJpaCtrl = new CustomerJpaController(emf);
        
        List<Customer> customers = customerJpaCtrl.findCustomerEntities(50, 100);
        for (Customer customer : customers) {
            System.out.println(customer.getCustomernumber() + " : " + customer.getContactfirstname() + " " + customer.getContactlastname());
        }
    }
}
