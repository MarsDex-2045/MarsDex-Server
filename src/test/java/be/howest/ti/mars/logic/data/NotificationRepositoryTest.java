package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Endpoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationRepositoryTest {
    @Test
    void testAddSubscription() {
        NotificationRepository data = NotificationRepository.getInstance();
        String address = "test.browser.com";
        String auth = "auth.auther";
        String p256dh = "hasher";

        Endpoint endpoint = data.addSubscription(address, auth, p256dh);

        assertEquals(endpoint, data.addSubscription(address, auth, p256dh));
        assertEquals(address, endpoint.getAddress());
        assertEquals(auth, endpoint.getAuth());
        assertEquals(p256dh, endpoint.getP256dh());
    }
}