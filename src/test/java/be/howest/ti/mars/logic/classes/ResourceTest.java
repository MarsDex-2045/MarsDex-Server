package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.classes.TestData.generateResources;
import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {
    @Test
    void testEquals() {
        Resource[] testResources = generateResources();
        assertEquals(testResources[2], testResources[0]);
        assertNotEquals(testResources[1], testResources[0]);
    }

    @Test
    void testHashCode() {
        Resource[] testResources = generateResources();
        assertEquals(testResources[2].hashCode(), testResources[0].hashCode());
        assertNotEquals(testResources[1].hashCode(), testResources[0].hashCode());
    }

    @Test
    void toJSON() {
        Resource[] testResources = generateResources();

        JsonObject json = testResources[0].toJSON();

        assertEquals("Panite", json.getString("name"));
        assertEquals(200.234, json.getDouble("price"));
        assertEquals("2052-10-12", json.getString("added"));
        assertEquals(200.234, json.getDouble("price"));
    }

    @Test
    void setWeight(){
        Resource resource = generateResources()[0];

        resource.setWeight(20);

        assertEquals(20, resource.getWeight());
    }

    @Test
    void getWeight(){
        Resource resource = generateResources()[0];

        assertEquals(2500, resource.getWeight());
    }
}