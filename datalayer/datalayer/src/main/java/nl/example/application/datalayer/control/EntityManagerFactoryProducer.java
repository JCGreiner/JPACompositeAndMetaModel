package nl.example.application.datalayer.control;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Dependent
@PersistenceUnitProducer
public class EntityManagerFactoryProducer {
	static Logger logger = Logger.getLogger(EntityManagerFactoryProducer.class.getName());

	String unitName;
	EntityManagerFactory entityManagerFactory;

	@Inject
	public EntityManagerFactoryProducer(InjectionPoint ip) {
		logger.info("inject persistence unit");
		if (ip != null) {
			this.unitName = extractUnitName(ip);
			logger.info("Valid injection point: " + unitName);
			entityManagerFactory = create(unitName);
		}
	}

	String extractUnitName(InjectionPoint ip) {
		for (Annotation annotation : ip.getQualifiers()) {
			if (annotation.annotationType().equals(PersistenceUnitProducer.class))
				return ((PersistenceUnitProducer) annotation).unitName();
		}
		throw new IllegalStateException("No @PersistenceUnitProducer on InjectionPoint");
	}

	EntityManagerFactory create(String unitName) {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("eclipselink.classloader", this.getClass().getClassLoader());
		hashMap.put("eclipselink.composite-unit", "true");
		hashMap.put("eclipselink.deploy-on-startup", "true");
		return Persistence.createEntityManagerFactory(unitName, hashMap);
	}

	EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void shutdown() {
		logger.log(Level.INFO, "Cleaning up EntityManagerFactoryProducer");
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
			entityManagerFactory = null;
		}
	}

}
