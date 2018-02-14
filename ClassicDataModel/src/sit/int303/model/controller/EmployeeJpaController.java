/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.model.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sit.int303.model.Employee;
import sit.int303.model.Office;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sit.int303.model.Customer;
import sit.int303.model.controller.exceptions.NonexistentEntityException;
import sit.int303.model.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author INT303
 */
public class EmployeeJpaController implements Serializable {

    public EmployeeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employee employee) throws PreexistingEntityException, Exception {
        if (employee.getEmployeeCollection() == null) {
            employee.setEmployeeCollection(new ArrayList<Employee>());
        }
        if (employee.getCustomerCollection() == null) {
            employee.setCustomerCollection(new ArrayList<Customer>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee reportsto = employee.getReportsto();
            if (reportsto != null) {
                reportsto = em.getReference(reportsto.getClass(), reportsto.getEmployeenumber());
                employee.setReportsto(reportsto);
            }
            Office officecode = employee.getOfficecode();
            if (officecode != null) {
                officecode = em.getReference(officecode.getClass(), officecode.getOfficecode());
                employee.setOfficecode(officecode);
            }
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : employee.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getEmployeenumber());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            employee.setEmployeeCollection(attachedEmployeeCollection);
            Collection<Customer> attachedCustomerCollection = new ArrayList<Customer>();
            for (Customer customerCollectionCustomerToAttach : employee.getCustomerCollection()) {
                customerCollectionCustomerToAttach = em.getReference(customerCollectionCustomerToAttach.getClass(), customerCollectionCustomerToAttach.getCustomernumber());
                attachedCustomerCollection.add(customerCollectionCustomerToAttach);
            }
            employee.setCustomerCollection(attachedCustomerCollection);
            em.persist(employee);
            if (reportsto != null) {
                reportsto.getEmployeeCollection().add(employee);
                reportsto = em.merge(reportsto);
            }
            if (officecode != null) {
                officecode.getEmployeeCollection().add(employee);
                officecode = em.merge(officecode);
            }
            for (Employee employeeCollectionEmployee : employee.getEmployeeCollection()) {
                Employee oldReportstoOfEmployeeCollectionEmployee = employeeCollectionEmployee.getReportsto();
                employeeCollectionEmployee.setReportsto(employee);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldReportstoOfEmployeeCollectionEmployee != null) {
                    oldReportstoOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldReportstoOfEmployeeCollectionEmployee = em.merge(oldReportstoOfEmployeeCollectionEmployee);
                }
            }
            for (Customer customerCollectionCustomer : employee.getCustomerCollection()) {
                Employee oldSalesrepemployeenumberOfCustomerCollectionCustomer = customerCollectionCustomer.getSalesrepemployeenumber();
                customerCollectionCustomer.setSalesrepemployeenumber(employee);
                customerCollectionCustomer = em.merge(customerCollectionCustomer);
                if (oldSalesrepemployeenumberOfCustomerCollectionCustomer != null) {
                    oldSalesrepemployeenumberOfCustomerCollectionCustomer.getCustomerCollection().remove(customerCollectionCustomer);
                    oldSalesrepemployeenumberOfCustomerCollectionCustomer = em.merge(oldSalesrepemployeenumberOfCustomerCollectionCustomer);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmployee(employee.getEmployeenumber()) != null) {
                throw new PreexistingEntityException("Employee " + employee + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employee employee) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee persistentEmployee = em.find(Employee.class, employee.getEmployeenumber());
            Employee reportstoOld = persistentEmployee.getReportsto();
            Employee reportstoNew = employee.getReportsto();
            Office officecodeOld = persistentEmployee.getOfficecode();
            Office officecodeNew = employee.getOfficecode();
            Collection<Employee> employeeCollectionOld = persistentEmployee.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = employee.getEmployeeCollection();
            Collection<Customer> customerCollectionOld = persistentEmployee.getCustomerCollection();
            Collection<Customer> customerCollectionNew = employee.getCustomerCollection();
            if (reportstoNew != null) {
                reportstoNew = em.getReference(reportstoNew.getClass(), reportstoNew.getEmployeenumber());
                employee.setReportsto(reportstoNew);
            }
            if (officecodeNew != null) {
                officecodeNew = em.getReference(officecodeNew.getClass(), officecodeNew.getOfficecode());
                employee.setOfficecode(officecodeNew);
            }
            Collection<Employee> attachedEmployeeCollectionNew = new ArrayList<Employee>();
            for (Employee employeeCollectionNewEmployeeToAttach : employeeCollectionNew) {
                employeeCollectionNewEmployeeToAttach = em.getReference(employeeCollectionNewEmployeeToAttach.getClass(), employeeCollectionNewEmployeeToAttach.getEmployeenumber());
                attachedEmployeeCollectionNew.add(employeeCollectionNewEmployeeToAttach);
            }
            employeeCollectionNew = attachedEmployeeCollectionNew;
            employee.setEmployeeCollection(employeeCollectionNew);
            Collection<Customer> attachedCustomerCollectionNew = new ArrayList<Customer>();
            for (Customer customerCollectionNewCustomerToAttach : customerCollectionNew) {
                customerCollectionNewCustomerToAttach = em.getReference(customerCollectionNewCustomerToAttach.getClass(), customerCollectionNewCustomerToAttach.getCustomernumber());
                attachedCustomerCollectionNew.add(customerCollectionNewCustomerToAttach);
            }
            customerCollectionNew = attachedCustomerCollectionNew;
            employee.setCustomerCollection(customerCollectionNew);
            employee = em.merge(employee);
            if (reportstoOld != null && !reportstoOld.equals(reportstoNew)) {
                reportstoOld.getEmployeeCollection().remove(employee);
                reportstoOld = em.merge(reportstoOld);
            }
            if (reportstoNew != null && !reportstoNew.equals(reportstoOld)) {
                reportstoNew.getEmployeeCollection().add(employee);
                reportstoNew = em.merge(reportstoNew);
            }
            if (officecodeOld != null && !officecodeOld.equals(officecodeNew)) {
                officecodeOld.getEmployeeCollection().remove(employee);
                officecodeOld = em.merge(officecodeOld);
            }
            if (officecodeNew != null && !officecodeNew.equals(officecodeOld)) {
                officecodeNew.getEmployeeCollection().add(employee);
                officecodeNew = em.merge(officecodeNew);
            }
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    employeeCollectionOldEmployee.setReportsto(null);
                    employeeCollectionOldEmployee = em.merge(employeeCollectionOldEmployee);
                }
            }
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    Employee oldReportstoOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getReportsto();
                    employeeCollectionNewEmployee.setReportsto(employee);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldReportstoOfEmployeeCollectionNewEmployee != null && !oldReportstoOfEmployeeCollectionNewEmployee.equals(employee)) {
                        oldReportstoOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldReportstoOfEmployeeCollectionNewEmployee = em.merge(oldReportstoOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            for (Customer customerCollectionOldCustomer : customerCollectionOld) {
                if (!customerCollectionNew.contains(customerCollectionOldCustomer)) {
                    customerCollectionOldCustomer.setSalesrepemployeenumber(null);
                    customerCollectionOldCustomer = em.merge(customerCollectionOldCustomer);
                }
            }
            for (Customer customerCollectionNewCustomer : customerCollectionNew) {
                if (!customerCollectionOld.contains(customerCollectionNewCustomer)) {
                    Employee oldSalesrepemployeenumberOfCustomerCollectionNewCustomer = customerCollectionNewCustomer.getSalesrepemployeenumber();
                    customerCollectionNewCustomer.setSalesrepemployeenumber(employee);
                    customerCollectionNewCustomer = em.merge(customerCollectionNewCustomer);
                    if (oldSalesrepemployeenumberOfCustomerCollectionNewCustomer != null && !oldSalesrepemployeenumberOfCustomerCollectionNewCustomer.equals(employee)) {
                        oldSalesrepemployeenumberOfCustomerCollectionNewCustomer.getCustomerCollection().remove(customerCollectionNewCustomer);
                        oldSalesrepemployeenumberOfCustomerCollectionNewCustomer = em.merge(oldSalesrepemployeenumberOfCustomerCollectionNewCustomer);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = employee.getEmployeenumber();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee employee;
            try {
                employee = em.getReference(Employee.class, id);
                employee.getEmployeenumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            Employee reportsto = employee.getReportsto();
            if (reportsto != null) {
                reportsto.getEmployeeCollection().remove(employee);
                reportsto = em.merge(reportsto);
            }
            Office officecode = employee.getOfficecode();
            if (officecode != null) {
                officecode.getEmployeeCollection().remove(employee);
                officecode = em.merge(officecode);
            }
            Collection<Employee> employeeCollection = employee.getEmployeeCollection();
            for (Employee employeeCollectionEmployee : employeeCollection) {
                employeeCollectionEmployee.setReportsto(null);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
            }
            Collection<Customer> customerCollection = employee.getCustomerCollection();
            for (Customer customerCollectionCustomer : customerCollection) {
                customerCollectionCustomer.setSalesrepemployeenumber(null);
                customerCollectionCustomer = em.merge(customerCollectionCustomer);
            }
            em.remove(employee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employee> findEmployeeEntities() {
        return findEmployeeEntities(true, -1, -1);
    }

    public List<Employee> findEmployeeEntities(int maxResults, int firstResult) {
        return findEmployeeEntities(false, maxResults, firstResult);
    }

    private List<Employee> findEmployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Employee findEmployee(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
