package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Colony;
import be.howest.ti.mars.logic.classes.Company;
import be.howest.ti.mars.logic.classes.Location;
import be.howest.ti.mars.logic.exceptions.IdentifierException;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class MarsRepositoryTest {
    private static final String URL = "jdbc:h2:~/test";
    public static final Logger LOGGER = Logger.getLogger(MarsRepositoryTest.class.getName());

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

    private void executeScript(String filePath, Connection con) throws IOException, SQLException {
        RunScript.execute(con, new FileReader(filePath));
        LOGGER.log(Level.INFO, "Executed SQL File from " + filePath);
    }

    @Test
    void getAllColonies() {
        MarsRepository data = MarsRepository.getInstance();
        Colony ref1 = new Colony(1,"Haberlandt Survey", new Location(0.00000, 0.00000, 0.000));
        Colony ref2 = new Colony(2, "Durrance Camp", new Location(40.22451, -80.56218, 160.000));
        Colony ref3 = new Colony(3, "Ehrlich City", new Location(33.21322, -33.2132, 300.000));
        Colony ref4 = new Colony(4, "Silves Claim", new Location(22.21773, 24.33564, -200.232));

        Set<Colony> colonies = data.getAllColonies();

        assertEquals(colonies.size(), 4);
        assertTrue(colonies.contains(ref1));
        assertTrue(colonies.contains(ref2));
        assertTrue(colonies.contains(ref3));
        assertTrue(colonies.contains(ref4));
    }

    @Test
    void getCompany() {
        MarsRepository data = MarsRepository.getInstance();
        Company ref1 = new Company(2, "MaMiCo", "B1g1r0n", "mamico@mars.com", "+3422893567", 150000);
        Company ref2 = new Company(1, "MarsDex", "DataH0arder", "marsdex@mars.com", "+6623145878", 0);


        Company maMiCo = data.getCompany(2);
        Company marsDex = data.getCompany(1);

        assertEquals(ref1, maMiCo);
        assertEquals(ref2, marsDex);
        assertThrows(IdentifierException.class, () -> data.getCompany(22));
    }

    @Test
    void getColony() {
        MarsRepository data = MarsRepository.getInstance();
        Colony ref = new Colony(3, "Ehrlich City", new Location(33.21322, 	-33.2132, 300.0));
        Company refC1 = new Company(4, "Geminorum Blue Vison Partners", "V1s10na1r", "gbvp@mars.com", "+552434221", 150000);
        Company refC2 = new Company(5, "Hydrae Noblement Services", "8ydr0n", "hydraenoble@mars.com", "+454553234", 250000);

        Colony colony = data.getColony(3);

        assertEquals(ref, colony);
        assertTrue(colony.getCompanies().contains(refC1));
        assertTrue(colony.getCompanies().contains(refC2));
        assertThrows(IdentifierException.class, () -> data.getColony(22));
    }

/*
    @Test
    void addCompany() {
        MarsRepository data = MarsRepository.getInstance();
        Company refC1 = new Company(500, "toegevoegd", "V1s10na1r", "testgbvp@mars.com", "+552434221", 150000);
        data.addCompany(refC1,3);
        assertEquals(refC1,data.getCompany(500));

    }
*/
}