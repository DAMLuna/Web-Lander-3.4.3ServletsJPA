/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.AndlunRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.AndlunUserGame;

/**
 *
 * @author alfas
 */
public class AndlunUserGameJpaController implements Serializable {

    public AndlunUserGameJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AndlunUserGame andlunUserGame) {
        if (andlunUserGame.getAndlunRegistryList() == null) {
            andlunUserGame.setAndlunRegistryList(new ArrayList<AndlunRegistry>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AndlunRegistry> attachedAndlunRegistryList = new ArrayList<AndlunRegistry>();
            for (AndlunRegistry andlunRegistryListAndlunRegistryToAttach : andlunUserGame.getAndlunRegistryList()) {
                andlunRegistryListAndlunRegistryToAttach = em.getReference(andlunRegistryListAndlunRegistryToAttach.getClass(), andlunRegistryListAndlunRegistryToAttach.getIdPlay());
                attachedAndlunRegistryList.add(andlunRegistryListAndlunRegistryToAttach);
            }
            andlunUserGame.setAndlunRegistryList(attachedAndlunRegistryList);
            em.persist(andlunUserGame);
            for (AndlunRegistry andlunRegistryListAndlunRegistry : andlunUserGame.getAndlunRegistryList()) {
                AndlunUserGame oldIdUserOfAndlunRegistryListAndlunRegistry = andlunRegistryListAndlunRegistry.getIdUser();
                andlunRegistryListAndlunRegistry.setIdUser(andlunUserGame);
                andlunRegistryListAndlunRegistry = em.merge(andlunRegistryListAndlunRegistry);
                if (oldIdUserOfAndlunRegistryListAndlunRegistry != null) {
                    oldIdUserOfAndlunRegistryListAndlunRegistry.getAndlunRegistryList().remove(andlunRegistryListAndlunRegistry);
                    oldIdUserOfAndlunRegistryListAndlunRegistry = em.merge(oldIdUserOfAndlunRegistryListAndlunRegistry);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AndlunUserGame andlunUserGame) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AndlunUserGame persistentAndlunUserGame = em.find(AndlunUserGame.class, andlunUserGame.getIdUser());
            List<AndlunRegistry> andlunRegistryListOld = persistentAndlunUserGame.getAndlunRegistryList();
            List<AndlunRegistry> andlunRegistryListNew = andlunUserGame.getAndlunRegistryList();
            List<String> illegalOrphanMessages = null;
            for (AndlunRegistry andlunRegistryListOldAndlunRegistry : andlunRegistryListOld) {
                if (!andlunRegistryListNew.contains(andlunRegistryListOldAndlunRegistry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AndlunRegistry " + andlunRegistryListOldAndlunRegistry + " since its idUser field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<AndlunRegistry> attachedAndlunRegistryListNew = new ArrayList<AndlunRegistry>();
            for (AndlunRegistry andlunRegistryListNewAndlunRegistryToAttach : andlunRegistryListNew) {
                andlunRegistryListNewAndlunRegistryToAttach = em.getReference(andlunRegistryListNewAndlunRegistryToAttach.getClass(), andlunRegistryListNewAndlunRegistryToAttach.getIdPlay());
                attachedAndlunRegistryListNew.add(andlunRegistryListNewAndlunRegistryToAttach);
            }
            andlunRegistryListNew = attachedAndlunRegistryListNew;
            andlunUserGame.setAndlunRegistryList(andlunRegistryListNew);
            andlunUserGame = em.merge(andlunUserGame);
            for (AndlunRegistry andlunRegistryListNewAndlunRegistry : andlunRegistryListNew) {
                if (!andlunRegistryListOld.contains(andlunRegistryListNewAndlunRegistry)) {
                    AndlunUserGame oldIdUserOfAndlunRegistryListNewAndlunRegistry = andlunRegistryListNewAndlunRegistry.getIdUser();
                    andlunRegistryListNewAndlunRegistry.setIdUser(andlunUserGame);
                    andlunRegistryListNewAndlunRegistry = em.merge(andlunRegistryListNewAndlunRegistry);
                    if (oldIdUserOfAndlunRegistryListNewAndlunRegistry != null && !oldIdUserOfAndlunRegistryListNewAndlunRegistry.equals(andlunUserGame)) {
                        oldIdUserOfAndlunRegistryListNewAndlunRegistry.getAndlunRegistryList().remove(andlunRegistryListNewAndlunRegistry);
                        oldIdUserOfAndlunRegistryListNewAndlunRegistry = em.merge(oldIdUserOfAndlunRegistryListNewAndlunRegistry);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = andlunUserGame.getIdUser();
                if (findAndlunUserGame(id) == null) {
                    throw new NonexistentEntityException("The andlunUserGame with id " + id + " no longer exists.");
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
            AndlunUserGame andlunUserGame;
            try {
                andlunUserGame = em.getReference(AndlunUserGame.class, id);
                andlunUserGame.getIdUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The andlunUserGame with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AndlunRegistry> andlunRegistryListOrphanCheck = andlunUserGame.getAndlunRegistryList();
            for (AndlunRegistry andlunRegistryListOrphanCheckAndlunRegistry : andlunRegistryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AndlunUserGame (" + andlunUserGame + ") cannot be destroyed since the AndlunRegistry " + andlunRegistryListOrphanCheckAndlunRegistry + " in its andlunRegistryList field has a non-nullable idUser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(andlunUserGame);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AndlunUserGame> findAndlunUserGameEntities() {
        return findAndlunUserGameEntities(true, -1, -1);
    }

    public List<AndlunUserGame> findAndlunUserGameEntities(int maxResults, int firstResult) {
        return findAndlunUserGameEntities(false, maxResults, firstResult);
    }

    private List<AndlunUserGame> findAndlunUserGameEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AndlunUserGame.class));
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

    public AndlunUserGame findAndlunUserGame(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AndlunUserGame.class, id);
        } finally {
            em.close();
        }
    }

    public int getAndlunUserGameCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AndlunUserGame> rt = cq.from(AndlunUserGame.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
