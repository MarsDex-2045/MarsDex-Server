package be.howest.ti.mars.logic.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void getLongitude() {
        Location loc1 = new Location(15.6,14.68787,47.65454);
        assertEquals(loc1.getLongitude(),15.6);
    }

    @Test
    void getLatitude() {
        Location loc1 = new Location(15.6,14.68787,47.65454);
        assertEquals(loc1.getLatitude(),14.68787);
    }

    @Test
    void getAltitude() {
        Location loc1 = new Location(15.6,14.68787,47.65454);
        assertEquals(loc1.getAltitude(),47.65454);
    }
}