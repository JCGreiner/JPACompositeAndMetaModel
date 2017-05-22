package nl.example.application.datalayer.control;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.inject.WeldInstance;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class EntityManagerProvider implements TestRule {
    private EntityManager em;
    private EntityTransaction et;

    private EntityManagerProvider(String unitName) {
    	WeldContainer container = new Weld().initialize();
    	WeldInstance<EntityManagerFactoryProducer> select = container.select(EntityManagerFactoryProducer.class, new PersistenceUnitProducerLiteral(unitName));
		EntityManagerFactoryProducer factoryProducer = select.get();
    	EntityManagerFactory entityManagerFactory = factoryProducer.getEntityManagerFactory();
    	em = entityManagerFactory.createEntityManager();
        et = em.getTransaction();
    }

    public static EntityManagerProvider persistenceUnit(String unitName) {
        return new EntityManagerProvider(unitName);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public EntityTransaction getEntityTransaction() {
        return et;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    et.begin();
                    base.evaluate();
                } finally {
                    if (et.isActive()) {
                        et.rollback();
                    }
                    em.clear();
                }
            }
        };
    }
}
