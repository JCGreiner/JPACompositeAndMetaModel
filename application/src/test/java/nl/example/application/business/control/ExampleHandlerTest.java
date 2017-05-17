package nl.example.application.business.control;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import nl.example.application.datalayer.control.CompositeDAO;

@RunWith(MockitoJUnitRunner.class)
public class ExampleHandlerTest {
    private ExampleHandler handler;
    @Mock
    Logger logger;
    @Mock
    CompositeDAO dao;
    @Mock
    ConstraintViolationException constraintViolationException;
    Set<ConstraintViolation<?>> set;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.handler = new ExampleHandler();
        handler.dao = dao;
        handler.logger = logger;
    }


    @Test
    public void when_cause_is_not_constraint_violation_nothing_happens() {
        RuntimeException exception = new RuntimeException("foo");
        handler.logConstraintViolationsIfAny(exception);
        verify(logger, Mockito.never()).log(Mockito.any(Level.class), Mockito.anyString());
    }

    @Test
    public void when_cause_is_constraint_violation_it_is_logged() {
        handler.logConstraintViolationsIfAny(constraintViolationException);
        verify(logger).log(Mockito.any(Level.class), Mockito.anyString());
    }

    @Test
    @Ignore
    public void when_cause_is_constraint_violation_it_is_logged_with_content() {
        when(constraintViolationException.getConstraintViolations()).thenReturn(set);
        handler.logConstraintViolationsIfAny(constraintViolationException);
        verify(logger).log(Mockito.any(Level.class), Mockito.anyString());
    }

}
