package be.howest.ti.mars.logic.data;


import be.howest.ti.mars.logic.classes.Colony;
import be.howest.ti.mars.logic.classes.Company;
import be.howest.ti.mars.logic.classes.Location;
import be.howest.ti.mars.logic.classes.Resource;
import be.howest.ti.mars.logic.exceptions.IdentifierException;
import org.h2.tools.Server;


import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.data.H2Statements.*;

/*
MBL: this is only a starter class to use a H2 database.
To make this class useful, please complete it with the topics seen in the module OOA & SD
- Make sure the conf/config.json properties are correct.
- The h2 web console is available at http://localhost:9000
- The h2 database file is located at ~/mars-db
- Hint:
  - Mocking this repository is not needed. Create database creating and population script in plain SQL.
    Use the @Before or @Before each (depending on the type of test) to quickly setup a fully populated db.
 */
public class MarsRepository {
    private static final MarsRepository INSTANCE = new MarsRepository();
    private static final Logger LOGGER = Logger.getLogger(MarsRepository.class.getName());
    private Server dbWebConsole;
    private String username;
    private String password;
    private String url;

    private MarsRepository() {
    }

    public static MarsRepository getInstance() {
        return INSTANCE;
    }

    public void cleanUp() {
        dbWebConsole.stop();
    }

    public static void configure(String url, String username, String password, int console)
            throws SQLException {
        INSTANCE.username = username;
        INSTANCE.password = password;
        INSTANCE.url = url;
        INSTANCE.dbWebConsole = Server.createWebServer(
                "-ifNotExists",
                "-webPort", String.valueOf(console)).start();
    }

    public Set<Colony> getAllColonies() {
        Set<Colony> res = new HashSet<>();
        try (Connection con = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement stmt = con.prepareStatement(H2_GET_COLONIES);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Location location = new Location(rs.getDouble("LATITUDE"), rs.getDouble("LONGITUDE"), rs.getDouble("ALTITUDE"));
                Colony colonyInfo = new Colony(rs.getInt("ID"), rs.getString("NAME"), location);
                res.add(colonyInfo);
            }
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Something went wrong with executing H2 Query.");
        }
        return res;
    }

    public Company getCompany(int companyId) {
        try (Connection con = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement stmt = con.prepareStatement(H2_GET_COMPANY_FULL)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                Company company = new Company(rs.getInt("COMPANY_ID"),
                        rs.getString("COMPANY_NAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("EMAIL"),
                        rs.getString("PHONE"));
                company.addResource(convertToResource(rs));
                while (rs.next()) {
                    company.addResource(convertToResource(rs));
                }
                return company;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.INFO, "Potential empty company detected; Running Existence check.");
            return existenceCheck(companyId);
        }
    }


    //rutger
    private void addCompany(String email, String name, String pwd, String phone, String colony) {
        int companyId;
        try (Connection con = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement prep = con.prepareStatement(H2_INSERT_COMPANY, Statement.RETURN_GENERATED_KEYS)) {
            prep.setString(1, "name");
            prep.setString(2, "pwd");
            prep.setString(3, "email");
            prep.setString(4, "phone");
            prep.executeUpdate();
            try (ResultSet autoId = prep.getGeneratedKeys()) {
                autoId.next();
                companyId = (autoId.getInt(1));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new RuntimeException("A database error occured.");
        }
        addColonyLink(companyId,getColonyIdByName(name));
    }

    private void addColonyLink(int companyId, int colonyId) {
        try (Connection con = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement prep = con.prepareStatement(H2_INSERT_COMPANYIDCOLONYID)) {
            prep.setInt(1, colonyId);
            prep.setInt(2, companyId);
            prep.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new RuntimeException("A database error occured.");
        }
    }


    private int getColonyIdByName(String colony) {
        try (Connection con = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement prep = con.prepareStatement(H2_GET_ColonyIDByName)) {
            {
                prep.setString(1, colony);
                ResultSet rs = prep.executeQuery();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new RuntimeException("A database error occured.");
        }
    }

    //einde rutger
    private Resource convertToResource(ResultSet rs) throws SQLException {
        LocalDate date = rs.getDate("ADDED_TIMESTAMP").toLocalDate();
        return new Resource(rs.getInt("RESOURCE_ID"),
                rs.getString("RESOURCE_NAME"),
                rs.getDouble("PRICE"),
                rs.getDouble("WEIGHT"),
                new Calendar.Builder().setDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()).build());
    }

    private Company existenceCheck(int companyId) {
        try (Connection con = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement stmt = con.prepareStatement(H2_GET_COMPANY_SIMPLE)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return new Company(rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("EMAIL"),
                        rs.getString("PHONE"));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.INFO, "Check ended: There are no companies with the given ID");
            throw new IdentifierException("Faulty Company ID");
        }
    }
}
