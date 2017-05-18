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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import nl.example.application.datalayer.entity.db.EntityA;
import nl.example.application.datalayer.entity.db.EntityA_;

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

	public void flushEntityManager() {
		getEntityManager().flush();
	}

	public EntityA storeEntityA(EntityA score) {
		return getEntityManager().merge(score);
	}

	public List<EntityA> findEntityANative(String dummyValue) {
		TypedQuery<EntityA> query = em.createQuery("select e from EntityA e where e.dummyValue = :dummyValue",
				EntityA.class);
		query.setParameter("dummyValue", dummyValue);
		return query.getResultList();
	}

	public List<EntityA> findEntityACriteria(String dummyValue) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<EntityA> cq = cb.createQuery(EntityA.class);
			Root<EntityA> from = cq.from(EntityA.class);
			Predicate dummyPredicate = cb.equal(from.get(EntityA_.dummyValue),
					cb.parameter(String.class, "dummyValue"));
			cq.select(from).where(dummyPredicate);
			return em.createQuery(cq).setParameter("dummyValue", dummyValue).getResultList();
		} catch (Exception e) {
			throw new DAOException("Problem execution criteria query", e);
		}
	}

}
