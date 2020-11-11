package be.howest.ti.mars.logic.classes;

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

}