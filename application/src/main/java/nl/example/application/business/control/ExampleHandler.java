package nl.example.application.business.control;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import nl.example.application.datalayer.control.CompositeDAO;
import nl.example.application.datalayer.entity.db.EntityA;

@Stateless
public class ExampleHandler {
    static final String CONSTRAINT_VIOLATIONS = "Constraint violations:\n";
    static final String EXCEPTION_OCCURED_WHEN_STORING_APPLICATION_FORM_S = "Exception occured when storing application form %s";
    Logger logger = Logger.getLogger(ExampleHandler.class.getName());
    @Inject
    CompositeDAO dao;

    static final List<String> EXCLUDED_ATTRIBUTES = Arrays.asList("id");

    void logConstraintViolationsIfAny(Throwable cause) {
        if (cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause)
                    .getConstraintViolations();
            String msg = CONSTRAINT_VIOLATIONS;
            for (ConstraintViolation cv : constraintViolations) {
                msg = msg + cv.getMessage() + ":" + cv.getPropertyPath() + ":" + cv.getRootBeanClass() + "\n";
            }
            logger.log(Level.SEVERE, msg);
        }
    }

    String nullToEmpty(Object object) {
        return object == null ? "" : object.toString();
    }

    public EntityA storeRiskIndicatorScore(EntityA score) {
        return dao.storeRiskIndicatorScore(score);
    }
}
