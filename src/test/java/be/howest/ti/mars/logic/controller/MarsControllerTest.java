package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.classes.*;
import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.exceptions.IdentifierException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class MarsControllerTest {
    public static final Logger LOGGER = Logger.getLogger(MarsController.class.getName());
    private static final String URL = "jdbc:h2:~/test";

    @BeforeAll
    static void setupTestSuite() throws SQLException{
        MarsRepository.configure(URL, "sa", "", 9000);
    }

    @BeforeEach
    void setupTest() throws IOException, SQLException {
        try (Connection con = MarsRepository.getInstance().getConnection()){
            executeScript("src/test/resources/dbClean.sql", con);
            executeScript("src/test/resources/dbConstruction.sql", con);
        }
    }

    @AfterAll
    static void closeConnection() {
        MarsRepository.getInstance().cleanUp();
    }

    private void executeScript(String filePath, Connection con) throws IOException, SQLException {
        RunScript.execute(con, new FileReader(filePath));
        LOGGER.log(Level.INFO, "Executed SQL File from " + filePath);
    }

    @Test
    void getMessageReturnsAWelcomeMessage() {
        // Arrange
        MarsController sut = new MarsController();

        // Act
        String message = sut.getMessage();

        //Assert
        assertTrue(StringUtils.isNoneBlank(message));
    }

    @Test
    void getColonies() {
        MarsRepository data = MarsRepository.getInstance();
        Colony ref1 = new Colony(1,"Haberlandt Survey", new Location(0.00000, 0.00000, 0.000));
        Colony ref2 = new Colony(2, "Durrance Camp", new Location(40.22451, -80.56218, 160.000));
        Colony ref3 = new Colony(3, "Ehrlich City", new Location(33.21322, -33.2132, 300.000));
        Colony ref4 = new Colony(4, "Silves Claim", new Location(22.21773, 24.33564, -200.232));

        JsonArray json = new MarsController().getColonies();

        assertTrue(json.contains(ref1.toShortJSON()));
        assertTrue(json.contains(ref2.toShortJSON()));
        assertTrue(json.contains(ref3.toShortJSON()));
        assertTrue(json.contains(ref4.toShortJSON()));
    }

    @Test
    void getColonyById() {
        MarsRepository data = MarsRepository.getInstance();
        Colony ref = new Colony(3, "Ehrlich City", new Location(33.21322, 	-33.2132, 300.0));
        ref.addCompany(new Company(4, "Geminorum Blue Vison Partners", "V1s10na1r", "gbvp@mars.com", "+552434221", 150000));
        ref.addCompany(new Company(5, "Hydrae Noblement Services", "8ydr0n", "hydraenoble@mars.com", "+454553234", 250000));

        JsonObject json = new MarsController().getColonyById("3");

        assertEquals(3, json.getInteger("id"));
        assertEquals("Ehrlich City", json.getString("name"));
        assertTrue(json.containsKey("location"));
        assertTrue(json.containsKey("resources"));
        assertThrows(IdentifierException.class, () -> data.getColony(22));
    }

    @Test
    void getCompanyResources() {
        MarsRepository data = MarsRepository.getInstance();

        JsonObject json = data.getCompany(2).allResourcesToJSONObject();
        JsonArray resources = json.getJsonArray("resources");

        assertEquals(2, json.getInteger("id"));
        assertTrue(json.containsKey("resources"));
        assertEquals(8, resources.size());
        assertThrows(IdentifierException.class, () -> data.getCompany(22));
    }

    @Test
    void getCompanyTransports() {
        Calendar date = new Calendar.Builder().setDate(2052, 3, 22).build();
        Resource ref1 = new Resource(1, "Painite", 71.596, 200.0 , date);
        Resource ref2 = new Resource(2, "Alexandrite", 271.192, 200.0, date);

        JsonArray json = new MarsController().getCompanyTransports("2");

        LOGGER.log(Level.INFO, json.toString());
        assertAll(() -> {
            assertEquals(18, json.size());
            JsonObject transport = json.getJsonObject(1);
            assertTrue(transport.containsKey("resources"));
            assertTrue(transport.containsKey("shippingId"));
            assertTrue(transport.containsKey("status"));
            assertTrue(transport.containsKey("sendTime"));
            assertTrue(transport.getJsonObject("sendTime").containsKey("date"));
            assertTrue(transport.getJsonObject("sendTime").containsKey("time"));
            assertTrue(transport.containsKey("sender"));
            assertTrue(transport.containsKey("receiveTime"));
            assertTrue(transport.containsKey("receiver"));
        });
    }
}
