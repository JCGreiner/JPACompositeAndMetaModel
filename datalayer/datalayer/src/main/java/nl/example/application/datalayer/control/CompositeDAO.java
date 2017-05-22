package nl.example.application.datalayer.control;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sessions.Project;

import nl.example.application.datalayer.entity.db.EntityA;
import nl.example.application.datalayer.entity.db.EntityA_;

@Stateless
public class CompositeDAO {
	static Logger logger = Logger.getLogger(CompositeDAO.class.getName());

	@Inject
	PersistenceUnitManager persistenceUnitManager;
	EntityManagerFactory factory;
	private EntityManager em;

	@PostConstruct
	public void initialize() {
		factory = persistenceUnitManager.getEntityManagerFactory();
		em = factory.createEntityManager();

		Metamodel metamodel = getEntityManager().getMetamodel();
		if (metamodel.getManagedTypes().isEmpty()) {
			logger.log(Level.WARNING, "meta model is empty");
		}
	}

	public void persist(Object o) {
		getEntityManager().persist(o);
	}

	public void merge(Object o) {
		getEntityManager().merge(o);
	}

	public Object find(Class<?> aClass, Object o) {
		try {
			return getEntityManager().find(aClass, o);
		} catch (Exception e) {
			logger.log(Level.WARNING, "DataServiceLayerAbstract: find gave exception ", e);
			return null;
		}
	}

	public void clear() {
		getEntityManager().clear();
	}

	public Query createQuery(String s) {
		return getEntityManager().createQuery(s);
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return getEntityManager().getCriteriaBuilder();
	}

	public void remove(Object o) {
		getEntityManager().remove(o);
	}

	public void detach(Object o) {
		getEntityManager().detach(o);
	}

	public boolean isOpen() {
		return getEntityManager().isOpen();
	}

	public EntityManager getEntityManager() {
		return em;
	}

	private void logEntities() {
		Set<EntityType<?>> entities = getEntityManager().getMetamodel().getEntities();
		for (EntityType<?> type : entities) {
			logger.log(Level.FINEST, String.format("name: %s\n", type.getName()));
		}
	}

	public void setEntityManager(EntityManager entityManager) {
		this.em = entityManager;
	}

	public void flushEntityManager() {
		getEntityManager().flush();
	}

	public EntityA storeEntityA(EntityA score) {
		return getEntityManager().merge(score);
	}

	public List<EntityA> findEntityANative(String dummyValue) {
		TypedQuery<EntityA> query = getEntityManager()
				.createQuery("select e from EntityA e where e.dummyValue = :dummyValue", EntityA.class);
		getEntityManager().createQuery("select e from EntityA e");
		EntityA.class.getClassLoader();
		query.setParameter("dummyValue", dummyValue);
		return query.getResultList();
	}

	public List<EntityA> findEntityACriteria(String dummyValue) {
		try {
			EntityManager entityManager = getEntityManager();
			CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<EntityA> cq = cb.createQuery(EntityA.class);
			Root<EntityA> from = cq.from(EntityA.class);
			Predicate dummyPredicate = cb.equal(from.get(EntityA_.dummyValue),
					cb.parameter(String.class, "dummyValue"));
			cq.select(from).where(dummyPredicate);
			EntityA.class.getClassLoader().equals(entityManager.getClass().getClassLoader());
			EntityA.class.getClassLoader().equals(entityManager.getMetamodel().getClass().getClassLoader());
			return entityManager.createQuery(cq).setParameter("dummyValue", dummyValue).getResultList();
		} catch (Exception e) {
			throw new DAOException("Problem execution criteria query", e);
		}
	}

	@PreDestroy
	public void close(){
		if (em != null){
			em.close();
			em = null;
			factory = null;
		}
	}
}
