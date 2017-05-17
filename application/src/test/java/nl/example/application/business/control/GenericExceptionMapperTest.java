package nl.example.application.business.control;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.AccessLocalException;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GenericExceptionMapperTest {
    private GenericExceptionMapper mapper;
    @Mock
    Path path;
    @Mock
    Logger logger;

    Set<ConstraintViolation<Object>> set;

    @Before
    public void setup() {
        WeldContainer container = new Weld().initialize();
        mapper = container.instance().select(GenericExceptionMapper.class).get();
        mapper.logger = logger;
        set = new HashSet<>();
        when(path.toString()).thenReturn("test");
    }

    @Test
    public void testAccessLocalException() {
        AccessLocalException exception = new AccessLocalException("test");
        Response response = mapper.toResponse(exception);
        Response.status(UNAUTHORIZED).entity("").type(MediaType.TEXT_PLAIN).build();
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals("", response.getEntity().toString());
    }

    @Test
    public void testNonSpecificException() {
        RuntimeException exception = new RuntimeException("test");
        Response response = mapper.toResponse(exception);
        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("test", response.getEntity().toString());
    }

    @Test
    public void testEJBExceptionWithRandomCause() {
        EJBException ejbException = new EJBException("foo", new RuntimeException("bar"));
        Response response = mapper.toResponse(ejbException);
        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("foo", response.getEntity().toString());
    }

}
