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
import sit.int303.model.Payment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sit.int303.model.Customer;
import sit.int303.model.Orders;
import sit.int303.model.controller.exceptions.IllegalOrphanException;
import sit.int303.model.controller.exceptions.NonexistentEntityException;
import sit.int303.model.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author INT303
 */
public class CustomerJpaController implements Serializable {

    public CustomerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customer customer) throws PreexistingEntityException, Exception {
        if (customer.getPaymentCollection() == null) {
            customer.setPaymentCollection(new ArrayList<Payment>());
        }
        if (customer.getOrdersCollection() == null) {
            customer.setOrdersCollection(new ArrayList<Orders>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee salesrepemployeenumber = customer.getSalesrepemployeenumber();
            if (salesrepemployeenumber != null) {
                salesrepemployeenumber = em.getReference(salesrepemployeenumber.getClass(), salesrepemployeenumber.getEmployeenumber());
                customer.setSalesrepemployeenumber(salesrepemployeenumber);
            }
            Collection<Payment> attachedPaymentCollection = new ArrayList<Payment>();
            for (Payment paymentCollectionPaymentToAttach : customer.getPaymentCollection()) {
                paymentCollectionPaymentToAttach = em.getReference(paymentCollectionPaymentToAttach.getClass(), paymentCollectionPaymentToAttach.getPaymentPK());
                attachedPaymentCollection.add(paymentCollectionPaymentToAttach);
            }
            customer.setPaymentCollection(attachedPaymentCollection);
            Collection<Orders> attachedOrdersCollection = new ArrayList<Orders>();
            for (Orders ordersCollectionOrdersToAttach : customer.getOrdersCollection()) {
                ordersCollectionOrdersToAttach = em.getReference(ordersCollectionOrdersToAttach.getClass(), ordersCollectionOrdersToAttach.getOrdernumber());
                attachedOrdersCollection.add(ordersCollectionOrdersToAttach);
            }
            customer.setOrdersCollection(attachedOrdersCollection);
            em.persist(customer);
            if (salesrepemployeenumber != null) {
                salesrepemployeenumber.getCustomerCollection().add(customer);
                salesrepemployeenumber = em.merge(salesrepemployeenumber);
            }
            for (Payment paymentCollectionPayment : customer.getPaymentCollection()) {
                Customer oldCustomerOfPaymentCollectionPayment = paymentCollectionPayment.getCustomer();
                paymentCollectionPayment.setCustomer(customer);
                paymentCollectionPayment = em.merge(paymentCollectionPayment);
                if (oldCustomerOfPaymentCollectionPayment != null) {
                    oldCustomerOfPaymentCollectionPayment.getPaymentCollection().remove(paymentCollectionPayment);
                    oldCustomerOfPaymentCollectionPayment = em.merge(oldCustomerOfPaymentCollectionPayment);
                }
            }
            for (Orders ordersCollectionOrders : customer.getOrdersCollection()) {
                Customer oldCustomernumberOfOrdersCollectionOrders = ordersCollectionOrders.getCustomernumber();
                ordersCollectionOrders.setCustomernumber(customer);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
                if (oldCustomernumberOfOrdersCollectionOrders != null) {
                    oldCustomernumberOfOrdersCollectionOrders.getOrdersCollection().remove(ordersCollectionOrders);
                    oldCustomernumberOfOrdersCollectionOrders = em.merge(oldCustomernumberOfOrdersCollectionOrders);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCustomer(customer.getCustomernumber()) != null) {
                throw new PreexistingEntityException("Customer " + customer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customer customer) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer persistentCustomer = em.find(Customer.class, customer.getCustomernumber());
            Employee salesrepemployeenumberOld = persistentCustomer.getSalesrepemployeenumber();
            Employee salesrepemployeenumberNew = customer.getSalesrepemployeenumber();
            Collection<Payment> paymentCollectionOld = persistentCustomer.getPaymentCollection();
            Collection<Payment> paymentCollectionNew = customer.getPaymentCollection();
            Collection<Orders> ordersCollectionOld = persistentCustomer.getOrdersCollection();
            Collection<Orders> ordersCollectionNew = customer.getOrdersCollection();
            List<String> illegalOrphanMessages = null;
            for (Payment paymentCollectionOldPayment : paymentCollectionOld) {
                if (!paymentCollectionNew.contains(paymentCollectionOldPayment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Payment " + paymentCollectionOldPayment + " since its customer field is not nullable.");
                }
            }
            for (Orders ordersCollectionOldOrders : ordersCollectionOld) {
                if (!ordersCollectionNew.contains(ordersCollectionOldOrders)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orders " + ordersCollectionOldOrders + " since its customernumber field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (salesrepemployeenumberNew != null) {
                salesrepemployeenumberNew = em.getReference(salesrepemployeenumberNew.getClass(), salesrepemployeenumberNew.getEmployeenumber());
                customer.setSalesrepemployeenumber(salesrepemployeenumberNew);
            }
            Collection<Payment> attachedPaymentCollectionNew = new ArrayList<Payment>();
            for (Payment paymentCollectionNewPaymentToAttach : paymentCollectionNew) {
                paymentCollectionNewPaymentToAttach = em.getReference(paymentCollectionNewPaymentToAttach.getClass(), paymentCollectionNewPaymentToAttach.getPaymentPK());
                attachedPaymentCollectionNew.add(paymentCollectionNewPaymentToAttach);
            }
            paymentCollectionNew = attachedPaymentCollectionNew;
            customer.setPaymentCollection(paymentCollectionNew);
            Collection<Orders> attachedOrdersCollectionNew = new ArrayList<Orders>();
            for (Orders ordersCollectionNewOrdersToAttach : ordersCollectionNew) {
                ordersCollectionNewOrdersToAttach = em.getReference(ordersCollectionNewOrdersToAttach.getClass(), ordersCollectionNewOrdersToAttach.getOrdernumber());
                attachedOrdersCollectionNew.add(ordersCollectionNewOrdersToAttach);
            }
            ordersCollectionNew = attachedOrdersCollectionNew;
            customer.setOrdersCollection(ordersCollectionNew);
            customer = em.merge(customer);
            if (salesrepemployeenumberOld != null && !salesrepemployeenumberOld.equals(salesrepemployeenumberNew)) {
                salesrepemployeenumberOld.getCustomerCollection().remove(customer);
                salesrepemployeenumberOld = em.merge(salesrepemployeenumberOld);
            }
            if (salesrepemployeenumberNew != null && !salesrepemployeenumberNew.equals(salesrepemployeenumberOld)) {
                salesrepemployeenumberNew.getCustomerCollection().add(customer);
                salesrepemployeenumberNew = em.merge(salesrepemployeenumberNew);
            }
            for (Payment paymentCollectionNewPayment : paymentCollectionNew) {
                if (!paymentCollectionOld.contains(paymentCollectionNewPayment)) {
                    Customer oldCustomerOfPaymentCollectionNewPayment = paymentCollectionNewPayment.getCustomer();
                    paymentCollectionNewPayment.setCustomer(customer);
                    paymentCollectionNewPayment = em.merge(paymentCollectionNewPayment);
                    if (oldCustomerOfPaymentCollectionNewPayment != null && !oldCustomerOfPaymentCollectionNewPayment.equals(customer)) {
                        oldCustomerOfPaymentCollectionNewPayment.getPaymentCollection().remove(paymentCollectionNewPayment);
                        oldCustomerOfPaymentCollectionNewPayment = em.merge(oldCustomerOfPaymentCollectionNewPayment);
                    }
                }
            }
            for (Orders ordersCollectionNewOrders : ordersCollectionNew) {
                if (!ordersCollectionOld.contains(ordersCollectionNewOrders)) {
                    Customer oldCustomernumberOfOrdersCollectionNewOrders = ordersCollectionNewOrders.getCustomernumber();
                    ordersCollectionNewOrders.setCustomernumber(customer);
                    ordersCollectionNewOrders = em.merge(ordersCollectionNewOrders);
                    if (oldCustomernumberOfOrdersCollectionNewOrders != null && !oldCustomernumberOfOrdersCollectionNewOrders.equals(customer)) {
                        oldCustomernumberOfOrdersCollectionNewOrders.getOrdersCollection().remove(ordersCollectionNewOrders);
                        oldCustomernumberOfOrdersCollectionNewOrders = em.merge(oldCustomernumberOfOrdersCollectionNewOrders);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customer.getCustomernumber();
                if (findCustomer(id) == null) {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getCustomernumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Payment> paymentCollectionOrphanCheck = customer.getPaymentCollection();
            for (Payment paymentCollectionOrphanCheckPayment : paymentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Payment " + paymentCollectionOrphanCheckPayment + " in its paymentCollection field has a non-nullable customer field.");
            }
            Collection<Orders> ordersCollectionOrphanCheck = customer.getOrdersCollection();
            for (Orders ordersCollectionOrphanCheckOrders : ordersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Orders " + ordersCollectionOrphanCheckOrders + " in its ordersCollection field has a non-nullable customernumber field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Employee salesrepemployeenumber = customer.getSalesrepemployeenumber();
            if (salesrepemployeenumber != null) {
                salesrepemployeenumber.getCustomerCollection().remove(customer);
                salesrepemployeenumber = em.merge(salesrepemployeenumber);
            }
            em.remove(customer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
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

    public Customer findCustomer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
