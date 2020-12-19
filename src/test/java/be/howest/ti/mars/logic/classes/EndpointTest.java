package be.howest.ti.mars.logic.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndpointTest {

    @Test
    void getEndpoint() {
        Endpoint s1 = new Endpoint(1, "1","1","1");
       assertEquals("1",s1.getAddress());
    }

    @Test
    void getAuth() {
        Endpoint s1 = new Endpoint(1, "1","1","1");
        assertEquals("1",s1.getAuth());
    }

    @Test
    void getP256dh() {
        Endpoint s1 = new Endpoint(1, "1","1","1");
        assertEquals("1",s1.getP256dh());
    }
}