package nl.example.application.business.control;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import nl.example.application.datalayer.control.CompositeDAO;
import nl.example.application.datalayer.entity.db.EntityA;

@RunWith(MockitoJUnitRunner.class)
public class ExampleHandlerTest {
    private ExampleHandler handler;
    @Mock
    Logger logger;
    @Mock
    CompositeDAO dao;
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.handler = new ExampleHandler();
        handler.dao = dao;
        handler.logger = logger;
    }

    @Test
    public void verify_dao_is_invoked_for_store(){
    	handler.storeEntityA(new EntityA());
    	Mockito.verify(dao).storeEntityA(Mockito.any(EntityA.class));
    }

}
