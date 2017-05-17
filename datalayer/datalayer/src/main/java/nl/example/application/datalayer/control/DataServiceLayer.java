package nl.example.application.datalayer.control;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public interface DataServiceLayer {

    void persist(Object o); //

    void merge(Object o);

    Object find(Class<?> aClass, Object o);

    void clear();

    Query createQuery(String s);

    CriteriaBuilder getCriteriaBuilder();

    void remove(Object o);

    void detach(Object o);

    boolean isOpen(); //

    void setEntityManager(EntityManager em); //

}