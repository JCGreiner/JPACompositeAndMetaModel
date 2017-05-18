package nl.example.application.business.boundary;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;
import nl.example.application.business.control.Settings;

@ApplicationPath("v1")
@ManagedBean
public class JAXRSConfiguration extends Application {

    private static final Logger LOGGER = Logger.getLogger(JAXRSConfiguration.class.getName());
    @Resource(lookup = "configuration/serverUrl")
    public String serverUrl;
    final BeanConfig beanConfig;

    public JAXRSConfiguration() {
        InitialContext ctx;
        String lookup = null;
        try {
            ctx = new InitialContext();
            lookup = (String) ctx.lookup("configuration/serverUrl");
            String msg = String.format("Running at server url: %s", lookup);
            LOGGER.log(Level.INFO, msg);
        } catch (NamingException e) {
            LOGGER.log(Level.WARNING, "Error while retrieving server url", e);
        }
        beanConfig = new BeanConfig();
        beanConfig.setTitle("example");
        beanConfig.setVersion(Settings.getVersion());

        beanConfig.setSchemes(new String[] { "http" });
        beanConfig.setHost(lookup);
        beanConfig.setBasePath("/example/v1");
        beanConfig.setResourcePackage(JAXRSConfiguration.class.getPackage().getName());
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);
    }

    @PostConstruct
    public void updateBeanConfig() {
        beanConfig.setHost(serverUrl);

    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        resources.add(nl.example.application.datalayer.control.DAOExceptionMapper.class);
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(nl.example.application.business.boundary.UtilsResources.class);
        resources.add(
        		nl.example.application.business.boundary.ExampleResource.class);
        resources.add(nl.example.application.business.boundary.ExampleResource.class);
    }
}
