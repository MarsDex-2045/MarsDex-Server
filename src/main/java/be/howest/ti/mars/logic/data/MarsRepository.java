package be.howest.ti.mars.logic.data;


import be.howest.ti.mars.logic.classes.*;
import be.howest.ti.mars.logic.exceptions.*;
import org.h2.tools.Server;
import org.h2.util.json.JSONObject;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
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
    private static final String GENERIC_SQL_ERROR = "Something went wrong with executing the query";
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
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_COLONIES);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Location location = new Location(rs.getDouble("LATITUDE"), rs.getDouble("LONGITUDE"), rs.getDouble("ALTITUDE"));
                Colony colonyInfo = new Colony(rs.getInt("ID"), rs.getString("NAME"), location);
                res.add(colonyInfo);
            }
        } catch (SQLException throwables) {
            LOGGER.log(Level.WARNING, "Something went wrong with executing H2 Query; Returning empty array");
        }
        return res;
    }

    public Company getCompany(int companyId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
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


    public Company addCompany(Company company, int colonyId) {
        int companyId = 0;
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement prep = con.prepareStatement(H2_INSERT_COMPANY, Statement.RETURN_GENERATED_KEYS)) {
            prep.setString(1, company.getName());
            prep.setString(2, company.getEmail());
            prep.setString(3, company.getPhone());
            prep.setString(4, company.getPassword());
            prep.executeUpdate();
            try (ResultSet autoId = prep.getGeneratedKeys()) {
                if (autoId.next()) {
                    companyId = autoId.getInt(1);
                    addColonyLink(companyId, colonyId);
                    return MarsRepository.getInstance().getCompany(companyId);
                } else {
                    LOGGER.severe("Failed getting the auto-id from new insert");
                    throw new SQLException("Failed retrieving the generated ID");
                }
            }
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Unique index or primary key violation")) {
                throw new DuplicationException("This email is already associated with an account.");
            }
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new H2RuntimeException(GENERIC_SQL_ERROR);
        } catch (IdentifierException ex) {
            deleteCompany(companyId);
            throw ex;
        }
    }

    private void deleteCompany(int companyId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_DELETE_COMPANY)) {
            stmt.setInt(1, companyId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.severe("Unable to delete dangling company");
            throw new H2RuntimeException("Internal server error");
        }
    }

    private void addColonyLink(int companyId, int colonyId) {
        try (Connection con = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement prep = con.prepareStatement(H2_INSERT_COLONYLINK)) {
            prep.setInt(1, colonyId);
            prep.setInt(2, companyId);
            prep.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new IdentifierException("Faulty Colony ID");
        }
    }

    private Resource convertToResource(ResultSet rs) throws SQLException {
        return new Resource(rs.getInt("RESOURCE_ID"),
                rs.getString("RESOURCE_NAME"),
                rs.getDouble("PRICE"),
                rs.getDouble("WEIGHT"),
                rs.getDate("ADDED_TIMESTAMP").toLocalDate());

    }

    private Company existenceCheck(int companyId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
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
        try (Connection con = MarsRepository.getInstance().getConnection();
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
            LOGGER.warning("No colony could be found.");
            throw new IdentifierException("Faulty Colony Id");
        } catch (IdentifierException ex) {
            throw new IdentifierException("Faulty company ID");
        }
    }

    private Colony transferToColony(ResultSet rs) {
        try {
            rs.next();
            int cId = rs.getInt("COLONY_ID");
            String cName = rs.getString("COLONY_NAME");
            Location location = new Location(rs.getDouble("LATITUDE"), rs.getDouble("LONGITUDE"), rs.getDouble("ALTITUDE"));
            return new Colony(cId, cName, location);
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "The result set was empty, aborting...");
            throw new IdentifierException("Empty result set; Possible faulty colony ID.");
        }
    }

    public Set<Shipment> getShipments(int companyId) {
        Set<Shipment> res = new HashSet<>();
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_TRANSPORT_DETAILS)) {
            stmt.setInt(1, companyId);
            stmt.setInt(2, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    res.add(covertToShipment(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        }
        if (res.isEmpty()) {
            LOGGER.log(Level.INFO, "Potential empty company detected; Running Existence check.");
            existenceCheck(companyId);
        }
        return res;
    }

    private Shipment covertToShipment(ResultSet rs) throws SQLException {
        try {
            int id = rs.getInt("ID");
            Colony sender = getColony(rs.getInt("SENDER_ID"));
            Colony receiver = getColony(rs.getInt("RECEIVER_ID"));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LocalDateTime sendDate = new Timestamp(df.parse(rs.getString("SEND_TIME")).getTime()).toLocalDateTime();
            Status status = getStatus(rs, id);
            rs.getObject("RECEIVE_TIME");
            if (!rs.wasNull()) {
                LocalDateTime receiveDate = new Timestamp(df.parse(rs.getString("RECEIVE_TIME")).getTime()).toLocalDateTime();
                return new Shipment(id, sender, sendDate, receiver, receiveDate, getShipmentResources(id), status);
            } else {
                return new Shipment(id, sender, sendDate, receiver, getShipmentResources(id), status);
            }
        } catch (ParseException ex) {
            String msg = String.format("Date corrupted at row %s in table SHIPMENTS in Scheme MARSDEX", rs.getInt("ID"));
            LOGGER.log(Level.SEVERE, msg);
            throw new CorruptedDataException("Date data corrupted");
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
                throw new CorruptedDataException("Status enum not recognized");
        }
    }

    private Set<Resource> getShipmentResources(int shipmentId) throws SQLException {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_TRANSPORT_RESOURCES)) {
            stmt.setInt(1, shipmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                Set<Resource> resources = new HashSet<>();
                while (rs.next()) {
                    resources.add(convertToResource(rs));
                }
                return resources;
            }
        }
    }

    public boolean insertResourceOfCompany(Resource resource, int companyId) {
        existenceCheck(companyId);
        if (resourceCheck(resource.getName(), companyId)) {
            throw new DuplicationException("This resource already exists. Please edit the resource instead.");
        }
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_INSERT_RESOURCE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, resource.getPrice());
            stmt.setString(2, resource.getName());
            stmt.executeUpdate();
            try (ResultSet result = stmt.getGeneratedKeys()) {
                if (result.next()) {
                    return linkResourceCompany(result.getInt(1), companyId, resource);
                } else {
                    LOGGER.severe("Failed getting the auto-id from new insert");
                    throw new SQLException("Failed retrieving the generated ID");
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "The given company couldn't be found.");
            throw new H2RuntimeException(ex.getMessage());
        }
    }

    private boolean linkResourceCompany(int resourceId, int companyId, Resource resource) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_INSERT_COMPANIES_RESOURCES)) {
            stmt.setInt(1, companyId);
            stmt.setInt(2, resourceId);
            stmt.setDouble(3, resource.getWeight());
            stmt.setDate(4, Date.valueOf(resource.getAddDate()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        }
    }

    private boolean resourceCheck(String resourceName, int companyId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_RESOURCE_COMPANY)) {
            stmt.setString(1, resourceName);
            stmt.setInt(2, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        }
    }

    public Colony getColonyOfCompany(int companyId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_COLONY_OF_COMPANY)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                return transferToColony(rs);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        } catch (IdentifierException ex) {
            existenceCheck(companyId);
            LOGGER.log(Level.SEVERE, String.format("Company with ID %s doesn't have a colony; Possible corrupted data entry, Please check manually", companyId));
            throw new CorruptedDataException(String.format("Faulty entry in table COLONIES_COMPANIES: Company with id %s doesn't have a colony.", companyId));
        }
    }

    public boolean updateResourceOfCompany(String name, Double weight, int companyId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_UPDATE_RESOURCE)) {
            existenceCheck(companyId);
            Resource selectedResource = getResourceByName(name, companyId);
            stmt.setDouble(1, weight);
            stmt.setInt(2, companyId);
            stmt.setInt(3, selectedResource.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        }
    }

    public Resource getResourceByName(String name, int companyId) throws SQLException {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_RESOURCE_BY_NAME)) {
            stmt.setString(1, name.toLowerCase(Locale.ROOT));
            stmt.setInt(2, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Resource(rs.getInt("ID"),
                            rs.getString("NAME"),
                            rs.getDouble("PRICE"),
                            rs.getDouble("WEIGHT"),
                            rs.getDate("ADDED_TIMESTAMP").toLocalDate());
                } else {
                    LOGGER.warning("The resource name is faulty");
                    throw new IdentifierException("No resources exists with this name.");
                }
            }
        }
    }

    public void deleteResourceFromCompany(int resourceId, int companyId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_OCCURRENCE_OF_RESOURCE)) {
            stmt.setInt(1, resourceId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (rs.getInt(1) > 1) {
                        removeResourceEntry(resourceId, companyId);
                    } else {
                        removeResourceAndReferences(resourceId, companyId);
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        }
    }

    private void removeResourceAndReferences(int resourceId, int companyId) {
        try (Connection con = MarsRepository.getInstance().getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement entryStmt = con.prepareStatement(H2_DELETE_RESOURCE_ENTRY);
                 PreparedStatement resourceStmt = con.prepareStatement(H2_DELETE_RESOURCE)) {
                entryStmt.setInt(1, companyId);
                entryStmt.setInt(2, resourceId);
                resourceStmt.setInt(1, resourceId);
                int changed = entryStmt.executeUpdate();
                if (changed == 0) {
                    existenceCheck(companyId);
                    LOGGER.warning("Entry could not be found or doesn't exist anymore");
                    throw new IdentifierException("Resource not found");
                }
                con.commit();
                changed = resourceStmt.executeUpdate();
                if (changed == 0) {
                    LOGGER.warning("Resource could not be found or doesn't exit anymore");
                    throw new IdentifierException("Resource not found");
                }
                con.commit();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        }
    }

    private void removeResourceEntry(int resourceId, int companyId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_DELETE_RESOURCE_ENTRY)) {
            stmt.setInt(1, companyId);
            stmt.setInt(2, resourceId);
            int changed = stmt.executeUpdate();
            if (changed == 0) {
                existenceCheck(companyId);
                LOGGER.warning("Entry could not be found");
                throw new IdentifierException("Resource not found");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        }
    }
    public void saveSubscription(JSONObject subscriptionDetails) {

        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement prep = con.prepareStatement(H2_INSERT_COMPANY)) {
            //prep.setString(1, subscriptionDetails.endpoint);
            //prep.setString(2, company.getEmail());
            //prep.setString(3, company.getPhone());
            prep.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getMessage().contains("Unique index or primary key violation")){
                throw new DuplicationException("This email is already associated with an account.");
            }
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new H2RuntimeException(GENERIC_SQL_ERROR);
        }
    }
}

