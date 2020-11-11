package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {


    @Test
    void getLongitude() {
        Location loc1 = new Location(15.666, 144.444,2645.333);
        assertEquals(loc1.getLongitude(),15.666);
    }

    @Test
    void getLatitude() {
        Location loc1 = new Location(15.666, 144.444,2645.333);
        assertEquals(loc1.getLatitude(),144.444);
    }

    @Test
    void getAltitude() {
        Location loc1 = new Location(15.666, 144.444,2645.333);
        assertEquals(loc1.getAltitude(),2645.333);
    }

    @Test
    void testEquals() {
        Location loc1 = new Location(15.666, 144.444,2645.333);
        Location loc2 = new Location(15.667, 144.444,2645.333);
        Location loc3 = new Location(17.666, 144.444,2645.333);
        Location loc4 = new Location(17.666, 144.444,2645.333);
        assertEquals(loc3, loc4);
        assertNotEquals(loc1,loc2);
    }

    @Test
    void testHashCode() {
        Location loc1 = new Location(15.666, 144.444,2645.333);
        Location loc2 = new Location(15.666, 144.444,2645.333);
        assertEquals(loc1, loc2);
        assertEquals(loc2.hashCode(), loc1.hashCode());
    }
    @Test
    void testJSON(){
        Location loc1 = new Location(15.666, 144.444,2645.333);
        JsonObject LocationJSON2 = new JsonObject();
        LocationJSON2.put("longitude", loc1.getLongitude());
        LocationJSON2.put("latitude", loc1.getLatitude());
        LocationJSON2.put("altitude", loc1.getAltitude());
        assertEquals(loc1.toJson(),LocationJSON2);
    }
}