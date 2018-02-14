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
import sit.int303.model.Customer;
import sit.int303.model.Orderdetail;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sit.int303.model.Orders;
import sit.int303.model.controller.exceptions.IllegalOrphanException;
import sit.int303.model.controller.exceptions.NonexistentEntityException;
import sit.int303.model.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author INT303
 */
public class OrdersJpaController implements Serializable {

    public OrdersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orders orders) throws PreexistingEntityException, Exception {
        if (orders.getOrderdetailCollection() == null) {
            orders.setOrderdetailCollection(new ArrayList<Orderdetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customernumber = orders.getCustomernumber();
            if (customernumber != null) {
                customernumber = em.getReference(customernumber.getClass(), customernumber.getCustomernumber());
                orders.setCustomernumber(customernumber);
            }
            Collection<Orderdetail> attachedOrderdetailCollection = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailCollectionOrderdetailToAttach : orders.getOrderdetailCollection()) {
                orderdetailCollectionOrderdetailToAttach = em.getReference(orderdetailCollectionOrderdetailToAttach.getClass(), orderdetailCollectionOrderdetailToAttach.getOrderdetailPK());
                attachedOrderdetailCollection.add(orderdetailCollectionOrderdetailToAttach);
            }
            orders.setOrderdetailCollection(attachedOrderdetailCollection);
            em.persist(orders);
            if (customernumber != null) {
                customernumber.getOrdersCollection().add(orders);
                customernumber = em.merge(customernumber);
            }
            for (Orderdetail orderdetailCollectionOrderdetail : orders.getOrderdetailCollection()) {
                Orders oldOrdersOfOrderdetailCollectionOrderdetail = orderdetailCollectionOrderdetail.getOrders();
                orderdetailCollectionOrderdetail.setOrders(orders);
                orderdetailCollectionOrderdetail = em.merge(orderdetailCollectionOrderdetail);
                if (oldOrdersOfOrderdetailCollectionOrderdetail != null) {
                    oldOrdersOfOrderdetailCollectionOrderdetail.getOrderdetailCollection().remove(orderdetailCollectionOrderdetail);
                    oldOrdersOfOrderdetailCollectionOrderdetail = em.merge(oldOrdersOfOrderdetailCollectionOrderdetail);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrders(orders.getOrdernumber()) != null) {
                throw new PreexistingEntityException("Orders " + orders + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orders orders) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders persistentOrders = em.find(Orders.class, orders.getOrdernumber());
            Customer customernumberOld = persistentOrders.getCustomernumber();
            Customer customernumberNew = orders.getCustomernumber();
            Collection<Orderdetail> orderdetailCollectionOld = persistentOrders.getOrderdetailCollection();
            Collection<Orderdetail> orderdetailCollectionNew = orders.getOrderdetailCollection();
            List<String> illegalOrphanMessages = null;
            for (Orderdetail orderdetailCollectionOldOrderdetail : orderdetailCollectionOld) {
                if (!orderdetailCollectionNew.contains(orderdetailCollectionOldOrderdetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderdetail " + orderdetailCollectionOldOrderdetail + " since its orders field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (customernumberNew != null) {
                customernumberNew = em.getReference(customernumberNew.getClass(), customernumberNew.getCustomernumber());
                orders.setCustomernumber(customernumberNew);
            }
            Collection<Orderdetail> attachedOrderdetailCollectionNew = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailCollectionNewOrderdetailToAttach : orderdetailCollectionNew) {
                orderdetailCollectionNewOrderdetailToAttach = em.getReference(orderdetailCollectionNewOrderdetailToAttach.getClass(), orderdetailCollectionNewOrderdetailToAttach.getOrderdetailPK());
                attachedOrderdetailCollectionNew.add(orderdetailCollectionNewOrderdetailToAttach);
            }
            orderdetailCollectionNew = attachedOrderdetailCollectionNew;
            orders.setOrderdetailCollection(orderdetailCollectionNew);
            orders = em.merge(orders);
            if (customernumberOld != null && !customernumberOld.equals(customernumberNew)) {
                customernumberOld.getOrdersCollection().remove(orders);
                customernumberOld = em.merge(customernumberOld);
            }
            if (customernumberNew != null && !customernumberNew.equals(customernumberOld)) {
                customernumberNew.getOrdersCollection().add(orders);
                customernumberNew = em.merge(customernumberNew);
            }
            for (Orderdetail orderdetailCollectionNewOrderdetail : orderdetailCollectionNew) {
                if (!orderdetailCollectionOld.contains(orderdetailCollectionNewOrderdetail)) {
                    Orders oldOrdersOfOrderdetailCollectionNewOrderdetail = orderdetailCollectionNewOrderdetail.getOrders();
                    orderdetailCollectionNewOrderdetail.setOrders(orders);
                    orderdetailCollectionNewOrderdetail = em.merge(orderdetailCollectionNewOrderdetail);
                    if (oldOrdersOfOrderdetailCollectionNewOrderdetail != null && !oldOrdersOfOrderdetailCollectionNewOrderdetail.equals(orders)) {
                        oldOrdersOfOrderdetailCollectionNewOrderdetail.getOrderdetailCollection().remove(orderdetailCollectionNewOrderdetail);
                        oldOrdersOfOrderdetailCollectionNewOrderdetail = em.merge(oldOrdersOfOrderdetailCollectionNewOrderdetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orders.getOrdernumber();
                if (findOrders(id) == null) {
                    throw new NonexistentEntityException("The orders with id " + id + " no longer exists.");
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
            Orders orders;
            try {
                orders = em.getReference(Orders.class, id);
                orders.getOrdernumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orders with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Orderdetail> orderdetailCollectionOrphanCheck = orders.getOrderdetailCollection();
            for (Orderdetail orderdetailCollectionOrphanCheckOrderdetail : orderdetailCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the Orderdetail " + orderdetailCollectionOrphanCheckOrderdetail + " in its orderdetailCollection field has a non-nullable orders field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Customer customernumber = orders.getCustomernumber();
            if (customernumber != null) {
                customernumber.getOrdersCollection().remove(orders);
                customernumber = em.merge(customernumber);
            }
            em.remove(orders);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orders.class));
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

    public Orders findOrders(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orders.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orders> rt = cq.from(Orders.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
