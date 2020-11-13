package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private static final Logger LOGGER = Logger.getLogger(LocationTest.class.getName());

    @Test
    void getLongitude() {
        Location[] locations = initLocations();
        Location loc1 = locations[0];
        assertEquals(loc1.getLongitude(),15.666);
    }

    @Test
    void getLatitude() {
        Location[] locations = initLocations();
        Location loc1 = locations[0];
        assertEquals(loc1.getLatitude(),144.444);
    }

    @Test
    void getAltitude() {
        Location[] locations = initLocations();
        Location loc1 = locations[0];
        assertEquals(loc1.getAltitude(),2645.333);
    }

    @Test
    void testEquals() {
        Location[] locations = initLocations();

        assertEquals(locations[2], locations[1]);
        assertNotEquals(locations[0],locations[2]);
    }

    @Test
    void testHashCode() {
        Location[] locations = initLocations();

        assertEquals(locations[2], locations[1]);
        assertEquals(locations[2].hashCode(), locations[1].hashCode());
    }

    @Test
    void testJSON(){
        Location[] locations = initLocations();
        JsonObject json = locations[0].toJson();

        LOGGER.log(Level.INFO, json.toString());
        assertEquals(15.666, json.getDouble("longitude"));
        assertEquals(144.444, json.getDouble("latitude"));
        assertEquals(2645.333, json.getDouble("altitude"));
    }

    private Location[] initLocations(){
        Location[] res = new Location[3];

        res[0] = new Location(15.666, 144.444,2645.333);
        res[1] = new Location(17.666, 144.444,2645.333);
        res[2] = new Location(17.666, 144.444,2645.333);

        return res;
    }
}