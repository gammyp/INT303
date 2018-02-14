/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.model.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sit.int303.model.Orderdetail;
import sit.int303.model.OrderdetailPK;
import sit.int303.model.Orders;
import sit.int303.model.Product;
import sit.int303.model.controller.exceptions.NonexistentEntityException;
import sit.int303.model.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author INT303
 */
public class OrderdetailJpaController implements Serializable {

    public OrderdetailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orderdetail orderdetail) throws PreexistingEntityException, Exception {
        if (orderdetail.getOrderdetailPK() == null) {
            orderdetail.setOrderdetailPK(new OrderdetailPK());
        }
        orderdetail.getOrderdetailPK().setProductcode(orderdetail.getProduct().getProductcode());
        orderdetail.getOrderdetailPK().setOrdernumber(orderdetail.getOrders().getOrdernumber());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders orders = orderdetail.getOrders();
            if (orders != null) {
                orders = em.getReference(orders.getClass(), orders.getOrdernumber());
                orderdetail.setOrders(orders);
            }
            Product product = orderdetail.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getProductcode());
                orderdetail.setProduct(product);
            }
            em.persist(orderdetail);
            if (orders != null) {
                orders.getOrderdetailCollection().add(orderdetail);
                orders = em.merge(orders);
            }
            if (product != null) {
                product.getOrderdetailCollection().add(orderdetail);
                product = em.merge(product);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrderdetail(orderdetail.getOrderdetailPK()) != null) {
                throw new PreexistingEntityException("Orderdetail " + orderdetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orderdetail orderdetail) throws NonexistentEntityException, Exception {
        orderdetail.getOrderdetailPK().setProductcode(orderdetail.getProduct().getProductcode());
        orderdetail.getOrderdetailPK().setOrdernumber(orderdetail.getOrders().getOrdernumber());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orderdetail persistentOrderdetail = em.find(Orderdetail.class, orderdetail.getOrderdetailPK());
            Orders ordersOld = persistentOrderdetail.getOrders();
            Orders ordersNew = orderdetail.getOrders();
            Product productOld = persistentOrderdetail.getProduct();
            Product productNew = orderdetail.getProduct();
            if (ordersNew != null) {
                ordersNew = em.getReference(ordersNew.getClass(), ordersNew.getOrdernumber());
                orderdetail.setOrders(ordersNew);
            }
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getProductcode());
                orderdetail.setProduct(productNew);
            }
            orderdetail = em.merge(orderdetail);
            if (ordersOld != null && !ordersOld.equals(ordersNew)) {
                ordersOld.getOrderdetailCollection().remove(orderdetail);
                ordersOld = em.merge(ordersOld);
            }
            if (ordersNew != null && !ordersNew.equals(ordersOld)) {
                ordersNew.getOrderdetailCollection().add(orderdetail);
                ordersNew = em.merge(ordersNew);
            }
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.getOrderdetailCollection().remove(orderdetail);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.getOrderdetailCollection().add(orderdetail);
                productNew = em.merge(productNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                OrderdetailPK id = orderdetail.getOrderdetailPK();
                if (findOrderdetail(id) == null) {
                    throw new NonexistentEntityException("The orderdetail with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(OrderdetailPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orderdetail orderdetail;
            try {
                orderdetail = em.getReference(Orderdetail.class, id);
                orderdetail.getOrderdetailPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderdetail with id " + id + " no longer exists.", enfe);
            }
            Orders orders = orderdetail.getOrders();
            if (orders != null) {
                orders.getOrderdetailCollection().remove(orderdetail);
                orders = em.merge(orders);
            }
            Product product = orderdetail.getProduct();
            if (product != null) {
                product.getOrderdetailCollection().remove(orderdetail);
                product = em.merge(product);
            }
            em.remove(orderdetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orderdetail> findOrderdetailEntities() {
        return findOrderdetailEntities(true, -1, -1);
    }

    public List<Orderdetail> findOrderdetailEntities(int maxResults, int firstResult) {
        return findOrderdetailEntities(false, maxResults, firstResult);
    }

    private List<Orderdetail> findOrderdetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orderdetail.class));
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

    public Orderdetail findOrderdetail(OrderdetailPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orderdetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderdetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orderdetail> rt = cq.from(Orderdetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
