package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static be.howest.ti.mars.logic.classes.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class MarsTest {

    @Test
    void getShipments() {
        Mars mars = generateMars();
        Set<Shipment> shipments = Set.of(generateShipments()[0], generateShipments()[2]);

        assertEquals(shipments, mars.getShipments());
    }

    @Test
    void getColonies() {
        Mars mars = generateMars();
        Set<Colony> colonies = Set.of(generateColonies()[0], generateColonies()[2]);

        assertEquals(colonies, mars.getColonies());
    }

    @Test
    void getColoniesAsJSON() {
    }

    @Test
    void getTransportAsJSON() {
    }

    private JsonArray generateRefArray() {
        JsonArray res = new JsonArray();
        Shipment[] shipments = generateShipments();
        res.add(shipments[0]);
        res.add(shipments[2]);
        return res;
    }
}