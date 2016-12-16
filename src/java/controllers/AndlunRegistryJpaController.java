/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.AndlunRegistry;
import jpa.AndlunUserGame;

/**
 *
 * @author alfas
 */
public class AndlunRegistryJpaController implements Serializable {

    public AndlunRegistryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AndlunRegistry andlunRegistry) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AndlunUserGame idUser = andlunRegistry.getIdUser();
            if (idUser != null) {
                idUser = em.getReference(idUser.getClass(), idUser.getIdUser());
                andlunRegistry.setIdUser(idUser);
            }
            em.persist(andlunRegistry);
            if (idUser != null) {
                idUser.getAndlunRegistryList().add(andlunRegistry);
                idUser = em.merge(idUser);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AndlunRegistry andlunRegistry) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AndlunRegistry persistentAndlunRegistry = em.find(AndlunRegistry.class, andlunRegistry.getIdPlay());
            AndlunUserGame idUserOld = persistentAndlunRegistry.getIdUser();
            AndlunUserGame idUserNew = andlunRegistry.getIdUser();
            if (idUserNew != null) {
                idUserNew = em.getReference(idUserNew.getClass(), idUserNew.getIdUser());
                andlunRegistry.setIdUser(idUserNew);
            }
            andlunRegistry = em.merge(andlunRegistry);
            if (idUserOld != null && !idUserOld.equals(idUserNew)) {
                idUserOld.getAndlunRegistryList().remove(andlunRegistry);
                idUserOld = em.merge(idUserOld);
            }
            if (idUserNew != null && !idUserNew.equals(idUserOld)) {
                idUserNew.getAndlunRegistryList().add(andlunRegistry);
                idUserNew = em.merge(idUserNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = andlunRegistry.getIdPlay();
                if (findAndlunRegistry(id) == null) {
                    throw new NonexistentEntityException("The andlunRegistry with id " + id + " no longer exists.");
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
            AndlunRegistry andlunRegistry;
            try {
                andlunRegistry = em.getReference(AndlunRegistry.class, id);
                andlunRegistry.getIdPlay();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The andlunRegistry with id " + id + " no longer exists.", enfe);
            }
            AndlunUserGame idUser = andlunRegistry.getIdUser();
            if (idUser != null) {
                idUser.getAndlunRegistryList().remove(andlunRegistry);
                idUser = em.merge(idUser);
            }
            em.remove(andlunRegistry);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AndlunRegistry> findAndlunRegistryEntities() {
        return findAndlunRegistryEntities(true, -1, -1);
    }

    public List<AndlunRegistry> findAndlunRegistryEntities(int maxResults, int firstResult) {
        return findAndlunRegistryEntities(false, maxResults, firstResult);
    }

    private List<AndlunRegistry> findAndlunRegistryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AndlunRegistry.class));
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

    public AndlunRegistry findAndlunRegistry(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AndlunRegistry.class, id);
        } finally {
            em.close();
        }
    }

    public int getAndlunRegistryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AndlunRegistry> rt = cq.from(AndlunRegistry.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
