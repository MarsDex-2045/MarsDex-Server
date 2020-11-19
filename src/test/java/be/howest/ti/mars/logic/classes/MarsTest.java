package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.classes.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MarsTest {
    private static final Logger LOGGER = Logger.getLogger(MarsTest.class.getName());


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
        Mars mars = generateMars();
        JsonArray refArray = generateRefColoniesArray();

        JsonArray marsArray = mars.getColoniesAsJSON();

        LOGGER.log(Level.INFO, marsArray.toString());
        assertTrue(marsArray.contains(refArray.getJsonObject(0)));
        assertTrue(marsArray.contains(refArray.getJsonObject(1)));
    }

    @Test
    void getShipmentsAsJSON() {
        Mars mars = generateMars();
        JsonArray refArray = generateRefShipmentsArray();

        JsonArray marsArray = mars.getShipmentsAsJSON();

        LOGGER.log(Level.INFO, marsArray.toString());
        assertTrue(marsArray.contains(refArray.getJsonObject(0)));
        assertTrue(marsArray.contains(refArray.getJsonObject(1)));
    }

    private JsonArray generateRefShipmentsArray() {
        JsonArray res = new JsonArray();
        res.add(generateShipments()[0].toJSON());
        res.add(generateShipments()[2].toJSON());
        return res;
    }

    private JsonArray generateRefColoniesArray() {
        JsonArray res = new JsonArray();
        res.add(generateColonies()[0].toShortJSON());
        res.add(generateColonies()[2].toShortJSON());
        return res;
    }
}