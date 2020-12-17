package be.howest.ti.mars.logic.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTest {

    @Test
    void getEndpoint() {
        Subscription s1 = new Subscription("1","1","1");
       assertEquals("1",s1.getEndpoint());
    }

    @Test
    void getAuth() {
        Subscription s1 = new Subscription("1","1","1");
        assertEquals("1",s1.getAuth());
    }

    @Test
    void getP256dh() {
        Subscription s1 = new Subscription("1","1","1");
        assertEquals("1",s1.getP256dh());
    }
}