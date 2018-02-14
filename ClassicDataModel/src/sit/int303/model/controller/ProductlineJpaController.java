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
import sit.int303.model.Product;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sit.int303.model.Productline;
import sit.int303.model.controller.exceptions.IllegalOrphanException;
import sit.int303.model.controller.exceptions.NonexistentEntityException;
import sit.int303.model.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author INT303
 */
public class ProductlineJpaController implements Serializable {

    public ProductlineJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productline productline) throws PreexistingEntityException, Exception {
        if (productline.getProductCollection() == null) {
            productline.setProductCollection(new ArrayList<Product>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Product> attachedProductCollection = new ArrayList<Product>();
            for (Product productCollectionProductToAttach : productline.getProductCollection()) {
                productCollectionProductToAttach = em.getReference(productCollectionProductToAttach.getClass(), productCollectionProductToAttach.getProductcode());
                attachedProductCollection.add(productCollectionProductToAttach);
            }
            productline.setProductCollection(attachedProductCollection);
            em.persist(productline);
            for (Product productCollectionProduct : productline.getProductCollection()) {
                Productline oldProductlineOfProductCollectionProduct = productCollectionProduct.getProductline();
                productCollectionProduct.setProductline(productline);
                productCollectionProduct = em.merge(productCollectionProduct);
                if (oldProductlineOfProductCollectionProduct != null) {
                    oldProductlineOfProductCollectionProduct.getProductCollection().remove(productCollectionProduct);
                    oldProductlineOfProductCollectionProduct = em.merge(oldProductlineOfProductCollectionProduct);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductline(productline.getProductline()) != null) {
                throw new PreexistingEntityException("Productline " + productline + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productline productline) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productline persistentProductline = em.find(Productline.class, productline.getProductline());
            Collection<Product> productCollectionOld = persistentProductline.getProductCollection();
            Collection<Product> productCollectionNew = productline.getProductCollection();
            List<String> illegalOrphanMessages = null;
            for (Product productCollectionOldProduct : productCollectionOld) {
                if (!productCollectionNew.contains(productCollectionOldProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Product " + productCollectionOldProduct + " since its productline field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Product> attachedProductCollectionNew = new ArrayList<Product>();
            for (Product productCollectionNewProductToAttach : productCollectionNew) {
                productCollectionNewProductToAttach = em.getReference(productCollectionNewProductToAttach.getClass(), productCollectionNewProductToAttach.getProductcode());
                attachedProductCollectionNew.add(productCollectionNewProductToAttach);
            }
            productCollectionNew = attachedProductCollectionNew;
            productline.setProductCollection(productCollectionNew);
            productline = em.merge(productline);
            for (Product productCollectionNewProduct : productCollectionNew) {
                if (!productCollectionOld.contains(productCollectionNewProduct)) {
                    Productline oldProductlineOfProductCollectionNewProduct = productCollectionNewProduct.getProductline();
                    productCollectionNewProduct.setProductline(productline);
                    productCollectionNewProduct = em.merge(productCollectionNewProduct);
                    if (oldProductlineOfProductCollectionNewProduct != null && !oldProductlineOfProductCollectionNewProduct.equals(productline)) {
                        oldProductlineOfProductCollectionNewProduct.getProductCollection().remove(productCollectionNewProduct);
                        oldProductlineOfProductCollectionNewProduct = em.merge(oldProductlineOfProductCollectionNewProduct);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = productline.getProductline();
                if (findProductline(id) == null) {
                    throw new NonexistentEntityException("The productline with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productline productline;
            try {
                productline = em.getReference(Productline.class, id);
                productline.getProductline();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productline with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Product> productCollectionOrphanCheck = productline.getProductCollection();
            for (Product productCollectionOrphanCheckProduct : productCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productline (" + productline + ") cannot be destroyed since the Product " + productCollectionOrphanCheckProduct + " in its productCollection field has a non-nullable productline field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(productline);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productline> findProductlineEntities() {
        return findProductlineEntities(true, -1, -1);
    }

    public List<Productline> findProductlineEntities(int maxResults, int firstResult) {
        return findProductlineEntities(false, maxResults, firstResult);
    }

    private List<Productline> findProductlineEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productline.class));
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

    public Productline findProductline(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productline.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductlineCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productline> rt = cq.from(Productline.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
