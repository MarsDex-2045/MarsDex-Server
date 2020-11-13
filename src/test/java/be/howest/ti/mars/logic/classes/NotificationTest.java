package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {
    private static final Logger LOGGER = Logger.getLogger(NotificationTest.class.getName());


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

        LOGGER.log(Level.INFO, res.toString());
        assertEquals("Test Message",res.getString("heading"));
        assertEquals("Test Message", res.getString("message"));
    }

    private Notification[] generateNotifications(){
        Notification[] res = new Notification[3];
        res[0] = new Notification(1, "Test Message", "Test Message");
        res[1] = new Notification(2, "Test Message", "Test Message");
        res[2] = new Notification(1, "Not a Test Message", "Not a Test Message");
        return res;
    }
}