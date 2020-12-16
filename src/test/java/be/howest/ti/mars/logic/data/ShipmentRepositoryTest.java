package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Shipment;
import be.howest.ti.mars.logic.exceptions.IdentifierException;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentRepositoryTest {
    private static final String URL = "jdbc:h2:~/test";
    public static final Logger LOGGER = Logger.getLogger(ShipmentRepositoryTest.class.getName());

    @BeforeAll
    static void setupTestSuite() throws SQLException {
        MarsRepository.configure(URL, "sa", "", 9000);
    }

    @BeforeEach
    void setupTest() throws IOException, SQLException {
        try (Connection con = MarsRepository.getInstance().getConnection()) {
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
    void getShipments() {
        ShipmentRepository data = ShipmentRepository.getInstance();

        Set<Shipment> db = data.getShipments(2);

        assertEquals(18, db.size());
        assertThrows(IdentifierException.class, () -> data.getShipments(234));
    }
}