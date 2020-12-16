package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.classes.TestData.generateNotifications;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {
    @Test
    void testEquals() {
        Notification[] notifications = generateNotifications();
        assertEquals(notifications[0], notifications[2]);
        assertNotEquals(notifications[1], notifications[2]);
    }

    @Test
    void testHashCode() {
        Notification[] notifications = generateNotifications();
        assertNotEquals(notifications[0].hashCode(), notifications[1].hashCode());
        assertEquals(notifications[0].hashCode(), notifications[2].hashCode());
    }

    @Test
    void toJSON() {
        Notification[] notifications = generateNotifications();

        JsonObject res = notifications[1].toJSON();

        assertEquals("Test Message",res.getString("heading"));
        assertEquals("Test Message", res.getString("message"));
    }
}