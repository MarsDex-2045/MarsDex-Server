package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Company;
import be.howest.ti.mars.logic.classes.Resource;
import be.howest.ti.mars.logic.exceptions.DuplicationException;
import be.howest.ti.mars.logic.exceptions.IdentifierException;
import io.vertx.core.json.JsonArray;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ResourceRepositoryTest {
    private static final String URL = "jdbc:h2:~/test";
    public static final Logger LOGGER = Logger.getLogger(ResourceRepositoryTest.class.getName());

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
    void updateResourceOfCompany() throws SQLException {
        ResourceRepository data = ResourceRepository.getInstance();

        data.updateResourceOfCompany("Painite", 123.456, 2);
        Resource result = data.getResourceByName("Painite", 2);

        assertThrows(IdentifierException.class, () -> data.updateResourceOfCompany("Painite", 123.456, 26));
        assertThrows(IdentifierException.class, () -> data.updateResourceOfCompany("Painites", 123.456, 2));
        assertAll(() -> {
            assertEquals("Painite", result.getName());
            assertEquals(123.456, result.getWeight());
        });
    }

    @Test
    void getResourceByName() throws SQLException {
        ResourceRepository data = ResourceRepository.getInstance();
        Resource ref = new Resource(1, "Painite", 71.596, 2500.0, LocalDate.parse("2050-11-01"));

        Resource resource = data.getResourceByName("Painite", 2);

        assertEquals(ref, resource);
        assertEquals(ref.toJSON(), resource.toJSON());
    }

    @Test
    void deleteResourceFromCompany() {
        ResourceRepository data = ResourceRepository.getInstance();
        Resource ref1 = new Resource(2, "Alexandite", 271.192, 35000.0, LocalDate.parse("2050-02-22"));
        Resource ref2 = new Resource(13, "Hafnium 178", 69.098, 124.221, LocalDate.parse("2033-11-01"));

        data.deleteResourceFromCompany(2,5);
        data.deleteResourceFromCompany(13, 11);
        Company company5 = CompanyRepository.getInstance().getCompany(5);
        Company company11 = CompanyRepository.getInstance().getCompany(11);

        assertThrows(IdentifierException.class, () -> data.deleteResourceFromCompany(2, 5));
        assertThrows(IdentifierException.class, () -> data.deleteResourceFromCompany(1234, 7));
        assertThrows(IdentifierException.class, () -> data.deleteResourceFromCompany(5, 1234));
        assertFalse(company5.allResourcesToJSONObject().getJsonArray("resources").contains(ref1.toJSON()));
        assertFalse(company11.allResourcesToJSONObject().getJsonArray("resources").contains(ref2.toJSON()));
    }

    @Test
    void insertResourceOfCompany() {
        Resource nr = new Resource(19, "Cobalt", 23.664, 2223.390, LocalDate.now());
        ResourceRepository data = ResourceRepository.getInstance();

        data.insertResourceOfCompany(nr, 4);
        JsonArray resources = CompanyRepository.getInstance().getCompany(4).allResourcesToJSONObject().getJsonArray("resources");

        assertThrows(DuplicationException.class, () -> data.insertResourceOfCompany(nr, 4));
        assertThrows(IdentifierException.class, () -> data.insertResourceOfCompany(nr, 234));

        assertTrue(resources.contains(nr.toJSON()));
    }

    @Test
    void testDeleteResourceFromCompany() {
        ResourceRepository data = ResourceRepository.getInstance();
        Resource shared = new Resource(8, "Coltan", 5.343, 50.221, LocalDate.parse("2049-03-12"));
        Resource nonExistent = new Resource(120, "Coal", 20.222, 50.21266, LocalDate.now());
        Resource nonEntry = new Resource(1, "Painite", 71.596, 250.0, LocalDate.parse("2050-11-01"));
        Resource independent = new Resource(17, "Serendibite", 172.629, 44.215, LocalDate.parse("2049-03-12"));

        data.deleteResourceFromCompany(shared.getId(), 10);
        data.deleteResourceFromCompany(independent.getId(), 10);

        assertThrows(IdentifierException.class, () -> data.deleteResourceFromCompany(nonExistent.getId(), 10));
        assertThrows(IdentifierException.class, () -> data.deleteResourceFromCompany(nonEntry.getId(), 10));
        assertFalse(CompanyRepository.getInstance().getCompany(10).allResourcesToJSONObject().getJsonArray("resources").contains(independent.toJSON()));
        assertFalse(CompanyRepository.getInstance().getCompany(10).allResourcesToJSONObject().getJsonArray("resources").contains(shared.toJSON()));
    }
}