package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.classes.*;
import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.exceptions.DuplicationException;
import be.howest.ti.mars.logic.exceptions.FormatException;
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
        MarsController controller = new MarsController();

        JsonObject resources = controller.getCompanyResources("2");

        assertEquals(2, resources.getInteger("id"));
        assertTrue(resources.containsKey("resources"));
        assertEquals(8, resources.getJsonArray("resources").size());
        assertThrows(IdentifierException.class, () -> controller.getCompanyResources("22"));
    }

    @Test
    void getCompanyTransports() {
        JsonArray json = new MarsController().getCompanyTransports("2");

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

    @Test
    void addResource() {
        JsonObject input = new JsonObject();
        input.put("name", "Gritium").put("weight", 203.243662).put("price", 124.976382);
        MarsController controller = new MarsController();

        assertAll(() ->{
            assertThrows(FormatException.class, () -> controller.addResource(input, "4"));
            input.put("weight", 203.234);
            assertThrows(FormatException.class, () -> controller.addResource(input, "4"));
            input.put("price", 234.223);
        });

        controller.addResource(input, "4");

        JsonObject ref = MarsRepository.getInstance().getCompany(4).allResourcesToJSONObject();

        assertThrows(DuplicationException.class, () -> controller.addResource(input, "4"));
    }

    @Test
    void testGetCompanyById() {
        MarsController controller = new MarsController();
        Company refCompany = MarsRepository.getInstance().getCompany(2);
        Colony refColony = MarsRepository.getInstance().getColony(2);

        JsonObject json = controller.getCompanyById("2");

        assertEquals(refColony.getName(), json.getString("colony"));
        assertAll(() ->{
            assertEquals(refColony.getName(), json.getString("colony"));
            assertEquals(refCompany.toJSON().getString("name"), json.getString("name"));
            assertEquals(refCompany.toJSON().getInteger("id"), json.getInteger("id"));
            assertEquals(refCompany.toJSON().getString("email"), json.getString("email"));
            assertEquals(refCompany.toJSON().getString("phoneNumber"), json.getString("phoneNumber"));
        });
    }

    @Test
    void makeCompany() {
        MarsController controller = new MarsController();
        Company refCompany = new Company(-1, "Yamazaki Landing", "Ar1sAkA", "yamazaki@mars.com", "+336722115");

        JsonObject response = controller.makeCompany(refCompany, 2);

        assertTrue(response.getBoolean("processed"));
        assertEquals(12, response.getInteger("id"));
    }
}
