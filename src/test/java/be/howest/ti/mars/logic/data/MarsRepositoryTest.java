package be.howest.ti.mars.logic.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    void setupTest(){
        try{
            createDataBase();
        } catch (IOException | SQLException e) {
            LOGGER.severe("Something went wrong while creating the database.");
        }
    }

    private void createDataBase() throws IOException, SQLException {
        executeScript("src/test/resources/dbStructure.sql");
        executeScript("src/test/resources/populateDb.sql");
    }

    private void executeScript(String filePath) throws IOException, SQLException {
        String batch = Files.readString(Path.of(filePath));
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(batch)) {
            stmt.execute();
            LOGGER.log(Level.INFO, "Executed SQL File from " + filePath);
        } catch (SQLException throwables) {
            LOGGER.severe("A SQL Error has occurred: " + throwables.getMessage());
            throw throwables;
        }
    }

    @Test
    void getAllColonies() {
    }

    @Test
    void getCompany() {
    }

    @Test
    void getColony() {
    }
}