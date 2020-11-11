package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {
    private static final Logger LOGGER = Logger.getLogger(ResourceTest.class.getName());

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

        LOGGER.log(Level.INFO, json.toString());
        assertEquals(testResources[0].getName(), json.getString("name"));
        assertEquals(testResources[0].getPrice(), json.getDouble("price"));
        assertEquals(testResources[0].getWeight(), json.getDouble("weight"));
        assertEquals(testResources[0].getAddDate().toString(), json.getString("added"));
    }

    private Resource[] generateResources(){
        Resource[] res = new Resource[3];
        Calendar date1 = new Calendar.Builder().setDate(2052, 11, 13).build();
        Calendar date2 = new Calendar.Builder().setDate(2052, 11, 28).build();
        res[0] = new Resource(1, "Panite", 200.234, 2500, date1);
        res[1] = new Resource(2, "Baurite", 145.666, 243, date2);
        res[2] = new Resource(1, "Panite", 200.234, 2500, date1);
        return res;
    }
}