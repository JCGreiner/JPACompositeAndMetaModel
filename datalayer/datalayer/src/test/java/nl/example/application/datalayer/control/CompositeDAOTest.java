package nl.example.application.datalayer.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import nl.example.application.datalayer.entity.db.EntityA;


public class CompositeDAOTest {

    private CompositeDAO compositeDAO;
    @Rule
    public EntityManagerProvider emProvider2 = EntityManagerProvider.persistenceUnit("CompositeTestPu");
    private final EntityManager em = emProvider2.getEntityManager();

    @Before
    public void setUp() {
        compositeDAO = new CompositeDAO();
        compositeDAO.setEntityManager(em);
    }

    @Test
    public void when_no_app_form_in_db_expect_null() {
        assertNull(compositeDAO.find(EntityA.class, Long.valueOf(123)));
    }

    @Test
    public void verify_can_find_entityA_native(){
    	EntityA entityA = new EntityA();
    	entityA.setDummyValue("foo");
    	compositeDAO.storeEntityA(entityA);
    	
    	List<EntityA> actual = compositeDAO.findEntityANative("foo");
    	assertNotNull(actual);
    	assertEquals(1, actual.size());
    }
    
    @Test
    public void verify_can_find_entityA_criteria(){
    	EntityA entityA = new EntityA();
    	entityA.setDummyValue("bar");
    	compositeDAO.storeEntityA(entityA);
    	
    	List<EntityA> actual = compositeDAO.findEntityACriteria("bar");
    	assertNotNull(actual);
    	assertEquals(1, actual.size());
    }
}
