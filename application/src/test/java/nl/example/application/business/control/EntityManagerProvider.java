package nl.example.application.business.control;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EntityManagerProvider implements TestRule {
    private EntityManager em;
    private EntityTransaction et;

    private EntityManagerProvider(String unitName) {
        em = Persistence.createEntityManagerFactory(unitName).createEntityManager();
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