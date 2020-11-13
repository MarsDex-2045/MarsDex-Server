package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {
    private final Logger LOGGER = Logger.getLogger(CompanyTest.class.getName());
    private JsonArray refArray;
    private final Resource resource1 = new Resource(1, "Panite", 20.552, 2.55, Calendar.getInstance());
    private final Resource resource2 = new Resource(2, "Berylium", 20.552, 2.55, Calendar.getInstance());
    private final Resource resource3 = new Resource(3, "Void Opals", 20.552, 2.55, Calendar.getInstance());
    private final Resource resource4 = new Resource(4, "Low Temperature Diamonds", 20.552, 2.55, Calendar.getInstance());
    private final Resource resource5 = new Resource(5, "Cobalt", 20.552, 2.55, Calendar.getInstance());

    @Test
    void testEquals() {
        Company[] companies = initCompanies();

        assertEquals(companies[0], companies[2]);
        assertNotEquals(companies[1], companies[0]);
    }

    @Test
    void testHashCode() {
        Company[] companies = initCompanies();

        assertEquals(companies[0].hashCode(), companies[2].hashCode());
        assertNotEquals(companies[0].hashCode(), companies[1].hashCode());
    }

    @Test
    void checkPassword() {
        Company[] companies = initCompanies();

        assertTrue(companies[0].checkPassword("BigIr0n"));
        assertFalse(companies[1].checkPassword("Shut-1n"));
    }

    @Test
    void allResourcesToJson() {
        Company[] companies = initCompanies();


        JsonObject json = companies[1].allResourcesToJSONObject();
        JsonArray resourceArray = json.getJsonArray("resources");

        LOGGER.log(Level.INFO, json.toString());
        assertEquals(2, json.getInteger("id"));
        assertTrue(resourceArray.contains(resource1.toJSON()));
        assertTrue(resourceArray.contains(resource2.toJSON()));
        assertTrue(resourceArray.contains(resource3.toJSON()));
        assertTrue(resourceArray.contains(resource4.toJSON()));
        assertTrue(resourceArray.contains(resource5.toJSON()));
    }

    @Test
    void toJSON() {
        Company[] companies = initCompanies();

        JsonObject json = companies[1].toJSON();

        LOGGER.log(Level.INFO, json.toString());
        assertEquals(2, json.getInteger("id"));
        assertEquals("104th Discovery Battalion", json.getString("name"));
        assertEquals("104thdb@mars.com", json.getString("email"));
        assertEquals("+32456162", json.getString("phoneNumber"));
    }

    private Company[] initCompanies(){
        Company[] res = new Company[3];
        res[0] = new Company(1, "MarsDex", "BigIr0n", "marsdex@mars.com", "+324561621");
        res[1] = new Company(2, "104th Discovery Battalion", "Expl0rer", "104thdb@mars.com", "+32456162");
        res[2] = new Company(1, "MarsDex", "BigIr0n", "marsdex@mars.com", "+324561621");
        Calendar dateData = new Calendar.Builder().setDate(2052, 11, 10).build();
        res[1].addResource(resource1);
        res[1].addResource(resource2);
        res[1].addResource(resource3);
        res[1].addResource(resource4);
        res[1].addResource(resource5);
        return res;
    }
}