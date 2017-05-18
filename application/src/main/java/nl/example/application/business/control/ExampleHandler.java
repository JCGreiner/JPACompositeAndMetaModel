package nl.example.application.business.control;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nl.example.application.datalayer.control.CompositeDAO;
import nl.example.application.datalayer.entity.db.EntityA;

@Stateless
public class ExampleHandler {
	Logger logger = Logger.getLogger(ExampleHandler.class.getName());
	@Inject
	CompositeDAO dao;

	public EntityA storeEntityA(EntityA score) {
		return dao.storeEntityA(score);
	}

	public List<EntityA> findEntityANative(String dummyValue) {
		return dao.findEntityANative(dummyValue);
	}

	public List<EntityA> findEntityACriteria(String dummyValue) {
		return dao.findEntityACriteria(dummyValue);
	}
}
