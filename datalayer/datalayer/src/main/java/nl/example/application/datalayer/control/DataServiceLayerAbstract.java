package nl.example.application.datalayer.control;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public abstract class DataServiceLayerAbstract implements DataServiceLayer {

    private static final Logger LOGGER = Logger.getLogger(DataServiceLayerAbstract.class.getName());

    @PersistenceContext(unitName = "CompositePu")
    protected EntityManager em;

    @Override
    public void persist(Object o) {
        em.persist(o);
    }

    @Override
    public void merge(Object o) {
        em.merge(o);
    }

    @Override
    public Object find(Class<?> aClass, Object o) {
        try {
            return em.find(aClass, o);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "DataServiceLayerAbstract: find gave exception ", e);
            return null;
        }
    }

    @Override
    public void clear() {
        em.clear();
    }

    @Override
    public Query createQuery(String s) {
        return em.createQuery(s);
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return em.getCriteriaBuilder();
    }

    @Override
    public void remove(Object o) {
        em.remove(o);
    }

    @Override
    public void detach(Object o) {
        em.detach(o);
    }

    @Override
    public boolean isOpen() {
        return em.isOpen();
    }

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

}