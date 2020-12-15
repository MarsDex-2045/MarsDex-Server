package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.classes.TestData.generateLocations;
import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    @Test
    void getLongitude() {
        Location[] locations = generateLocations();
        Location loc1 = locations[0];
        assertEquals(loc1.getLongitude(),15.666);
    }

    @Test
    void getLatitude() {
        Location[] locations = generateLocations();
        Location loc1 = locations[0];
        assertEquals(loc1.getLatitude(),144.444);
    }

    @Test
    void getAltitude() {
        Location[] locations = generateLocations();
        Location loc1 = locations[0];
        assertEquals(loc1.getAltitude(),2645.333);
    }

    @Test
    void testEquals() {
        Location[] locations = generateLocations();

        assertEquals(locations[2], locations[1]);
        assertNotEquals(locations[0],locations[2]);
    }

    @Test
    void testHashCode() {
        Location[] locations = generateLocations();

        assertEquals(locations[2], locations[1]);
        assertEquals(locations[2].hashCode(), locations[1].hashCode());
    }

    @Test
    void testJSON(){
        Location[] locations = generateLocations();
        JsonObject json = locations[0].toJson();

        assertEquals(15.666, json.getDouble("longitude"));
        assertEquals(144.444, json.getDouble("latitude"));
        assertEquals(2645.333, json.getDouble("altitude"));
    }


}