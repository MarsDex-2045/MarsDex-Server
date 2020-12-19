package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Colony;
import be.howest.ti.mars.logic.classes.Company;
import be.howest.ti.mars.logic.classes.Location;
import be.howest.ti.mars.logic.exceptions.CorruptedDataException;
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

class ColonyRepositoryTest {
    private static final String URL = "jdbc:h2:~/test";
    public static final Logger LOGGER = Logger.getLogger(ColonyRepositoryTest.class.getName());

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
    void getAllColonies() {
        ColonyRepository data = ColonyRepository.getInstance();
        Colony ref1 = new Colony(1, "Haberlandt Survey", new Location(-22.42744, 162.18224, 0.000));
        Colony ref2 = new Colony(2, "Durrance Camp", new Location(-80.60405, 80.04179, 160.000));
        Colony ref3 = new Colony(3, "Ehrlich City", new Location(44.32803, 103.71858, 300.000));
        Colony ref4 = new Colony(4, "Silves Claim", new Location(22.21773, 24.33564, -200.232));

        Set<Colony> colonies = data.getAllColonies();

        assertEquals(8,colonies.size());
        assertTrue(colonies.contains(ref1));
        assertTrue(colonies.contains(ref2));
        assertTrue(colonies.contains(ref3));
        assertTrue(colonies.contains(ref4));
    }

    @Test
    void getColony() {
        ColonyRepository data = ColonyRepository.getInstance();
        Colony ref = new Colony(3, "Ehrlich City", new Location(44.32803, 103.71858, 300.000));
        Company refC1 = new Company(4, "Geminorum Blue Vison Partners", "$2a$10$kIFnjINOAWcXHeqhtqjwjeecgDOK31LsABWdI1COMKZHyJto9pn0e", "gbvp@mars.com", "+552434221");
        Company refC2 = new Company(5, "Hydrae Noblement Services", "$2a$10$rWDNR/.2Cz0AR0z/D6IEw.duy7mbaYn6zkU8q4.93M7dO2VgNobUO", "hydraenoble@mars.com", "+454553234");

        Colony colony = data.getColony(3);

        assertEquals(ref, colony);
        assertTrue(colony.getCompanies().contains(refC1));
        assertTrue(colony.getCompanies().contains(refC2));
        assertThrows(IdentifierException.class, () -> data.getColony(22));
    }

    @Test
    void getColonyOfCompany() {
        ColonyRepository data = ColonyRepository.getInstance();
        Colony ref = data.getColony(2);

        Colony result = data.getColonyOfCompany(2);

        assertEquals(ref, result);
        assertThrows(IdentifierException.class, () -> data.getColonyOfCompany(234));
    }
}