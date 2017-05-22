package nl.example.application.datalayer.control;

import javax.enterprise.util.AnnotationLiteral;

public class PersistenceUnitProducerLiteral extends AnnotationLiteral<PersistenceUnitProducer> implements PersistenceUnitProducer{

	String unitName;
	
	public PersistenceUnitProducerLiteral(String unitName){
		this.unitName = unitName;
	}
	@Override
	public String unitName() {
		return unitName;
	}

}
