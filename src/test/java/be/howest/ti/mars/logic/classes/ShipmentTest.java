package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.classes.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {
    @Test
    void toJSON() {
        Shipment[] shipments = generateShipments();

        JsonObject json = shipments[1].toJSON();

        assertEquals(1, json.getInteger("shippingId"));
        assertEquals(Status.DELIVERED.name(), json.getString("status"));
        assertTrue(json.getJsonArray("resources").contains(resource1.toJSON()));
        assertTrue(json.getJsonArray("resources").contains(resource2.toJSON()));
        assertTrue(json.getJsonArray("resources").contains(resource3.toJSON()));
        assertEquals("2052-10-12", json.getJsonObject("sendTime").getString("date"));
        assertEquals("02:02", json.getJsonObject("sendTime").getString("time"));
        assertEquals(generateColonies()[0].toShortJSON(), json.getJsonObject("sender"));
        assertEquals(generateColonies()[2].toShortJSON(), json.getJsonObject("receiver"));
        assertEquals("2052-11-12", json.getJsonObject("receiveTime").getString("date"));
        assertEquals("10:22", json.getJsonObject("receiveTime").getString("time"));
    }

    @Test
    void testEquals() {
        Shipment[] shipments = generateShipments();
        assertEquals(shipments[0], shipments[1]);
        assertNotEquals(shipments[1], shipments[2]);
    }

    @Test
    void testHashCode() {
        Shipment[] shipments = generateShipments();
        assertNotEquals(shipments[0], shipments[2]);
        assertEquals(shipments[0], shipments[1]);
    }
}