package nl.example.application.datalayer.control;

import java.util.logging.Level;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import nl.example.application.datalayer.entity.db.EntityA;

@RunWith(MockitoJUnitRunner.class)
public class PersistenceGenerationTest {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(PersistenceGenerationTest.class.getSimpleName());

    @Rule
    public EntityManagerProvider emProvider2 = EntityManagerProvider.persistenceUnit("CompositeTestPu");
    private final EntityManager em = emProvider2.getEntityManager();

    @Before
    public void setUp() {
    }

    @Test
    public void emptyTest() {
    }

    @Test
    public void answerPersistTest() {
        nl.example.application.datalayer.entity.db.EntityA entityA = new EntityA();

        entityA.setId((long) 10);
        entityA.setDummyValue("dummy");
        em.persist(entityA);

        EntityA found = em.find(EntityA.class, (long) 10);
        logger.log(Level.INFO, "answerPersistTest: Object read contains dummyValue ["
                + found.getDummyValue() + "]");

    }
}
