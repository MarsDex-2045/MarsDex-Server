package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.classes.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {
    @Test
    void testEquals() {
        Company[] companies = generateCompanies();

        assertEquals(companies[0], companies[2]);
        assertNotEquals(companies[1], companies[0]);
    }

    @Test
    void testHashCode() {
        Company[] companies = generateCompanies();

        assertEquals(companies[0].hashCode(), companies[2].hashCode());
        assertNotEquals(companies[0].hashCode(), companies[1].hashCode());
    }

    @Test
    void checkPassword() {
        Company[] companies = generateCompanies();

        assertTrue(companies[0].checkPassword("BigIr0n"));
        assertFalse(companies[1].checkPassword("Shut-1n"));
    }

    @Test
    void allResourcesToJson() {
        Company[] companies = generateCompanies();


        JsonObject json = companies[1].allResourcesToJSONObject();
        JsonArray resourceArray = json.getJsonArray("resources");

        assertEquals(2, json.getInteger("id"));
        assertTrue(resourceArray.contains(resource1.toJSON()));
        assertTrue(resourceArray.contains(resource2.toJSON()));
        assertTrue(resourceArray.contains(resource3.toJSON()));
        assertTrue(resourceArray.contains(resource4.toJSON()));
        assertTrue(resourceArray.contains(resource5.toJSON()));
    }

    @Test
    void toJSON() {
        Company[] companies = generateCompanies();

        JsonObject json = companies[1].toJSON();

        assertEquals(2, json.getInteger("id"));
        assertEquals("104th Discovery Battalion", json.getString("name"));
        assertEquals("104thdb@mars.com", json.getString("email"));
        assertEquals("+32456162", json.getString("phoneNumber"));
    }

    @Test
    void getResources() {
        Company test = generateCompanies()[1];

        Set<Resource> resources = test.getResources();

        assertEquals(5, resources.size());
        assertTrue(resources.contains(resource1));
        assertTrue(resources.contains(resource2));
        assertTrue(resources.contains(resource3));
        assertTrue(resources.contains(resource4));
        assertTrue(resources.contains(resource5));
    }
}