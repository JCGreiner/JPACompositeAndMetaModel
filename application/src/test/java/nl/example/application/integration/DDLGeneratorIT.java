package nl.example.application.integration;

import nl.example.application.business.control.EntityManagerProvider;
import org.junit.Rule;
import org.junit.Test;

public class DDLGeneratorIT {
    @Rule
    public EntityManagerProvider emProvider = EntityManagerProvider.persistenceUnit("exampleIntegrationTest");

    @Test
    public void testToGenerateDDL() {
        // Empty method on purpose
    }
}
