package be.howest.ti.mars.logic.data;


import be.howest.ti.mars.logic.classes.*;
import be.howest.ti.mars.logic.exceptions.CorruptedDateException;
import be.howest.ti.mars.logic.exceptions.IdentifierException;
import org.h2.tools.Server;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.username, this.password);
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
            LOGGER.log(Level.SEVERE, "Something went wrong with executing H2 Query; Returning empty array");
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

    public Colony getColony(int id) {
        try (Connection con = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement stmt = con.prepareStatement(H2_GET_COLONY)) {
            String companyIdColumnName = "COMPANY_ID";
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Colony colony = transferToColony(rs);
                colony.addCompany(getCompany(rs.getInt(companyIdColumnName)));
                while (rs.next()) {
                    colony.addCompany(getCompany(rs.getInt(companyIdColumnName)));
                }
                return colony;
            }
        } catch (SQLException throwables) {
            LOGGER.severe("No colony could be found.");
            throw new IdentifierException("Faulty Colony Id");
        }
    }

    private Colony transferToColony(ResultSet rs) throws SQLException {
        rs.next();
        int cId = rs.getInt("COLONY_ID");
        String cName = rs.getString("COLONY_NAME");
        Location location = new Location(rs.getDouble("LATITUDE"), rs.getDouble("LONGITUDE"), rs.getDouble("ALTITUDE"));
        return new Colony(cId, cName, location);
    }

    public Set<Shipment> getShipments(int companyId) {
        try (Connection con = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement stmt = con.prepareStatement(H2_GET_TRANSPORT)) {
            stmt.setInt(1, companyId);
            stmt.setInt(2, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                Set<Shipment> res = new HashSet<>();
                while (rs.next()) {
                    covertToShipment(rs);
                }
                return Collections.emptySet();
            }
        } catch (SQLException ex) {
            throw new IdentifierException("Faulty Company ID");
        } catch (ParseException ex) {
            throw new IllegalArgumentException();
        }
    }

    private void covertToShipment(ResultSet rs) throws SQLException, ParseException {
        int id = rs.getInt("ID");
        Colony sender = getColony(rs.getInt("SENDER_ID"));
        Colony receiver = getColony(rs.getInt("RECEIVER_ID"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sendDate = df.parse(rs.getString("SEND_TIME"));
        Status status = getStatus(rs, id);
        rs.getObject("RECEIVE_DATE");
        if (!rs.wasNull()) {
            Date receiverDate = df.parse(rs.getString("RECEIVE_TIME"));
            LOGGER.info(() -> String.format("%s %s %s %s %s",
                    sender.toShortJSON().toString(),
                    receiver.toShortJSON().toString(),
                    sendDate.toString(),
                    receiverDate.toString(),
                    status.toString()));
        } else {
            LOGGER.info(() -> String.format("%s %s %s %s",
                    sender.toShortJSON().toString(),
                    receiver.toShortJSON().toString(),
                    sendDate.toString(),
                    status.toString()));
        }
    }

    private Status getStatus(ResultSet rs, int id) throws SQLException {
        switch (rs.getString("STATUS")) {
            case "In Transit":
                return Status.IN_TRANSPORT;
            case "Payed":
                return Status.PAYED;
            case "Processing":
                return Status.PROCESSING;
            case "Delivered":
                return Status.DELIVERED;
            default:
                LOGGER.log(Level.SEVERE, () ->
                        String.format("Corrupted data discovered: Status column in row with id %s at table SHIPMENTS in schema MARSDEX",
                                id));
                throw new CorruptedDateException("Status enum not recognized");
        }
    }
}
