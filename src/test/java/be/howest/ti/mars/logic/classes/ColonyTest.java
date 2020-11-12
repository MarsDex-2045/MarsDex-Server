package be.howest.ti.mars.logic.classes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ColonyTest {

    @Test
    void getId() {
        Location loc1 = new Location(15.55,454.666,4545.3262);
        Colony col1 = new Colony(1,"Narub",loc1);
        assertEquals(1,col1.getId());
    }

    @Test
    void getLocation() {
        Location loc1 = new Location(15.55,454.666,4545.3262);
        Colony col1 = new Colony(1,"Narub",loc1);
       assertEquals(loc1,col1.getLocation());
    }

    @Test
    void getName() {
        Location loc1 = new Location(15.55,454.666,4545.3262);
        Colony col1 = new Colony(1,"Narub",loc1);
        assertEquals(1,col1.getId());
    }

    @Test
    void getResource() {
        Location loc1 = new Location(15.55,454.666,4545.3262);
        Colony col1 = new Colony(1,"Narub",loc1);
        Calendar date1 = new Calendar.Builder().setDate(2052, 11, 13).build();
        //variables
        Resource test = new Resource(1,"iron",5.99,5000,date1);
       // col1.AddResource(1,"iron",5.99,5000,date1);
        assertEquals(test,col1.getResource(1));
    }

    @Test
    void addResource() {
        Location loc1 = new Location(15.55,454.666,4545.3262);
        Colony col1 = new Colony(1,"Narub",loc1);
        Calendar date1 = new Calendar.Builder().setDate(2052, 11, 13).build();
        Resource test = new Resource(2,"iron",5.99,5000,date1);
       // col1.AddResource(2,"iron",5.99,5000,date1);
       // assertEquals(test,col1.getResource(2));
    }

    @Test
    void getAllResources() {
    }

    @Test
    void toJson() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}