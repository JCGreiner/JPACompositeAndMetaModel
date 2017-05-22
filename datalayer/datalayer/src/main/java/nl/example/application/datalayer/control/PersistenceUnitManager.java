package nl.example.application.datalayer.control;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

@Singleton
@Startup
public class PersistenceUnitManager {
	Logger logger = Logger.getLogger(PersistenceUnitManager.class.getName());

	@Inject
	@Any
	Instance<EntityManagerFactoryProducer> providerInstance;

	EntityManagerFactoryProducer factoryProducer;
	EntityManagerFactory entityManagerFactory;

	@PostConstruct
	public void constructPersistenceUnit() {
		Instance<EntityManagerFactoryProducer> selected = providerInstance
				.select(new PersistenceUnitProducerLiteral("CompositePu"));
		factoryProducer = selected.get();
		entityManagerFactory = factoryProducer.getEntityManagerFactory();
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	@PreDestroy
	public void shutDown() {
		logger.log(Level.INFO, "Cleanup persistence unit manager");
		if (factoryProducer != null){
			factoryProducer.shutdown();
		}
		entityManagerFactory = null;
		factoryProducer = null;
		providerInstance = null;
	}
}
