package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Company;
import be.howest.ti.mars.logic.exceptions.DuplicationException;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class CompanyRepositoryTest {
    private static final String URL = "jdbc:h2:~/test";
    public static final Logger LOGGER = Logger.getLogger(CompanyRepositoryTest.class.getName());

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
    void getCompany() {
        CompanyRepository data = CompanyRepository.getInstance();
        Company ref1 = new Company(2, "MaMiCo", "B1g1r0n", "mamico@mars.com", "+3422893567");
        Company ref2 = new Company(1, "MarsDex", "DataH0arder", "marsdex@mars.com", "+6623145878");


        Company maMiCo = data.getCompany(2);
        Company marsDex = data.getCompany(1);

        assertEquals(ref1, maMiCo);
        assertEquals(ref2, marsDex);
        assertThrows(IdentifierException.class, () -> data.getCompany(22));
    }

    @Test
    void addCompany() {
        CompanyRepository data = CompanyRepository.getInstance();
        Company newCompany1 = new Company(-1, "Walz Depot", "B1gSt0rage", "walzstorage@mars.com", "+3245677829");
        Company newCompany2 = new Company(-1, "Powell High", "F1yH1gh.Icarus", "powell@mars.com", "+32451662744");

        Company result = data.addCompany(newCompany1, 2);

        assertThrows(DuplicationException.class, () -> data.addCompany(newCompany1, 2));
        assertThrows(IdentifierException.class, () -> data.addCompany(newCompany2, 234));

        assertAll(() ->{
            assertEquals("Walz Depot", result.getName());
            assertEquals("B1gSt0rage", result.getPassword());
            assertEquals("walzstorage@mars.com", result.getEmail());
            assertEquals("+3245677829", result.getPhone());
        });
    }
}