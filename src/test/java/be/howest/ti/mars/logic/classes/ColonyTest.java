package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.classes.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class ColonyTest {

    private final Resource resource1 = new Resource(1, "Panite", 234.667, 22.000, dateData2);
    private final Resource resource2 = new Resource(2, "Berylium", 22.356, 10.050, dateData2);
    private final Resource resource3 = new Resource(2, "Cobalt", 25.356, 12.150, dateData2);
    private static final Logger LOGGER = Logger.getLogger(ColonyTest.class.getName());


    @Test
    void addCompany() {
        Colony[] colonies = generateColonies();

        assertTrue(colonies[1].getCompanies().contains(company1));
    }

    @Test
    void toJSON() {
        Colony[] colonies = generateColonies();

        JsonObject json = colonies[0].toJSON();

        LOGGER.log(Level.INFO, json.toString());
        for (int i = 0; i < company1.allResourcesToJSONArray().size(); i++){
            assertTrue(json.getJsonArray("resources").contains(company1.allResourcesToJSONArray().getJsonObject(i)));
        }
        for (int i = 0; i < company2.allResourcesToJSONArray().size(); i++) {
            assertTrue(json.getJsonArray("resources").contains(company2.allResourcesToJSONArray().getJsonObject(i)));
        }
    }

    @Test
    void testEquals() {
        Colony[] colonies = generateColonies();
        assertEquals(colonies[0], colonies[1]);
        assertNotEquals(colonies[1], colonies[2]);
    }
}