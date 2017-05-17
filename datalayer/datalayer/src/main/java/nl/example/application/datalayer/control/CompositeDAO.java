package nl.example.application.datalayer.control;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import nl.example.application.datalayer.entity.db.RiskIndicatorScore;

@Stateless
public class CompositeDAO extends DataServiceLayerAbstract {
    static Logger LOGGER = Logger.getLogger(CompositeDAO.class.getName());


    private Map<String, Map<String, String>> mapDataBeans = null;

    public EntityManager getEntityManager() {
        if (null != super.em) {
            logEntities();
            return super.em;
        } else {
            super.em = Persistence.createEntityManagerFactory("CompositePu").createEntityManager();
            LOGGER.log(Level.WARNING, "===== Persistence unit was null - had to be created [",
                    Thread.currentThread().getStackTrace());
            logEntities();
            return super.em;
        }
    }

    private void logEntities() {
        Set<EntityType<?>> entities = super.em.getMetamodel().getEntities();
        for (EntityType type : entities) {
            LOGGER.log(Level.FINEST, String.format("name: %s\n", type.getName()));
        }
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.em = entityManager;
    }


    private boolean instantiateEntity(final String applicationName, final Class eitityClass) {
        try {
            Object object = null;
            Constructor<?> constructors[] = eitityClass.getConstructors();

            object = constructors[0].newInstance("");
            if (object != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "registerEntityBean: cannot construct entity bean [" + applicationName + ","
                    + eitityClass.getCanonicalName().toString() + "]");
            return false;
        }
    }

    public void storeDummy(RiskIndicatorScore riskIndicatorScore) {
        getEntityManager().persist(riskIndicatorScore);
    }

    public void flushEntityManager() {
        getEntityManager().flush();
    }


    public RiskIndicatorScore storeRiskIndicatorScore(RiskIndicatorScore score) {
        return getEntityManager().merge(score);
    }

}
