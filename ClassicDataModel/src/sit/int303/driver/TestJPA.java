/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.driver;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sit.int303.model.Customer;
import sit.int303.model.Employee;

/**
 *
 * @author INT303
 */
public class TestJPA {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClassicDataModelPU");
        EntityManager em = emf.createEntityManager();
//        List<Employee> employeeList = em.createNamedQuery("Employee.findAll").getResultList();
//        for (Employee employee : employeeList) {
//            System.out.println(employee.getFirstname() + " " + employee.getEmail());
//        }
        
        em.getTransaction().begin();
        
        Customer c = em.find(Customer.class, 103);
        Customer newCustomer = new Customer(909);
        System.out.println("Name = " + c.getCustomername());
        
        newCustomer.setCustomername("Pichet Lim");
        newCustomer.setAddressline1(c.getAddressline1());
        newCustomer.setAddressline2(c.getAddressline2());
        newCustomer.setCity(c.getCity());
        newCustomer.setContactfirstname(c.getContactfirstname());
        newCustomer.setContactlastname(c.getContactlastname());
        newCustomer.setCountry(c.getCountry());
        newCustomer.setCreditlimit(c.getCreditlimit());
        newCustomer.setPhone(c.getPhone());
        newCustomer.setPostalcode(c.getPostalcode());
        newCustomer.setSalesrepemployeenumber(c.getSalesrepemployeenumber());
        newCustomer.setState(c.getState());
        newCustomer.setPaymentCollection(c.getPaymentCollection());
        
        em.persist(newCustomer);
        
        em.flush();
        em.getTransaction().commit();
        em.close();
        
    }
}
