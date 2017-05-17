package nl.example.application.datalayer.control;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import nl.example.application.datalayer.entity.db.EntityA;

@Stateless
public class CompositeDAO {
    static Logger LOGGER = Logger.getLogger(CompositeDAO.class.getName());


    @PersistenceContext(unitName = "CompositePu")
    protected EntityManager em;

    public void persist(Object o) {
        em.persist(o);
    }

    public void merge(Object o) {
        em.merge(o);
    }

    public Object find(Class<?> aClass, Object o) {
        try {
            return em.find(aClass, o);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "DataServiceLayerAbstract: find gave exception ", e);
            return null;
        }
    }

    public void clear() {
        em.clear();
    }

    public Query createQuery(String s) {
        return em.createQuery(s);
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return em.getCriteriaBuilder();
    }

    public void remove(Object o) {
        em.remove(o);
    }

    public void detach(Object o) {
        em.detach(o);
    }

    public boolean isOpen() {
        return em.isOpen();
    }

    public EntityManager getEntityManager() {
        if (null != em) {
            logEntities();
            return em;
        } else {
            em = Persistence.createEntityManagerFactory("CompositePu").createEntityManager();
            LOGGER.log(Level.WARNING, "===== Persistence unit was null - had to be created [",
                    Thread.currentThread().getStackTrace());
            logEntities();
            return em;
        }
    }

    private void logEntities() {
        Set<EntityType<?>> entities = em.getMetamodel().getEntities();
        for (EntityType type : entities) {
            LOGGER.log(Level.FINEST, String.format("name: %s\n", type.getName()));
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        em = entityManager;
    }


    public void storeDummy(EntityA entityA) {
        getEntityManager().persist(entityA);
    }

    public void flushEntityManager() {
        getEntityManager().flush();
    }


    public EntityA storeEntityA(EntityA score) {
        return getEntityManager().merge(score);
    }

}
