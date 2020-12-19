package be.howest.ti.mars.logic.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndpointTest {

    @Test
    void getId() {
        Endpoint test =  generateEndpoint();

        assertEquals(1, test.getId());
    }

    @Test
    void getAddress() {
        Endpoint test =  generateEndpoint();

        assertEquals("address", test.getAddress());
    }

    @Test
    void testGetAuth() {
        Endpoint test =  generateEndpoint();

        assertEquals("auth", test.getAuth());
    }

    @Test
    void testGetP256dh() {
        Endpoint test =  generateEndpoint();

        assertEquals("p256dh", test.getP256dh());
    }

    @Test
    void testEquals() {
        Endpoint test =  generateEndpoint();
        Endpoint endpoint2 = new Endpoint(2, "address", "auth", "p256dh");
        Endpoint endpoint3 = new Endpoint(1, "faultyAddress", "auth", "p256dh");
        Endpoint dup = new Endpoint(1, "address", "auth", "p256dh");

        assertEquals(test, dup);
        assertNotEquals(test, endpoint2);
        assertNotEquals(test, endpoint3);
    }

    @Test
    void testHashCode() {
        int test =  generateEndpoint().hashCode();
        int endpoint2 = new Endpoint(2, "address", "auth", "p256dh").hashCode();
        int endpoint3 = new Endpoint(1, "faultyAddress", "auth", "p256dh").hashCode();
        int dup = new Endpoint(1, "address", "auth", "p256dh").hashCode();

        assertEquals(test, dup);
        assertNotEquals(test, endpoint2);
        assertNotEquals(test, endpoint3);
    }

    private Endpoint generateEndpoint(){
        return new Endpoint(1, "address", "auth", "p256dh");
    }
}