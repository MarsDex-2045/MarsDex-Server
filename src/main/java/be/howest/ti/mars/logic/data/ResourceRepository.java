package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Resource;
import be.howest.ti.mars.logic.exceptions.DuplicationException;
import be.howest.ti.mars.logic.exceptions.H2RuntimeException;
import be.howest.ti.mars.logic.exceptions.IdentifierException;

import java.sql.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.data.H2Statements.*;
import static be.howest.ti.mars.logic.data.H2Statements.H2_DELETE_RESOURCE_ENTRY;
import static be.howest.ti.mars.logic.data.MarsRepository.GENERIC_SQL_ERROR;

public class ResourceRepository {
    public static final Logger LOGGER = Logger.getLogger(CompanyRepository.class.getName());
    private static final ResourceRepository INSTANCE = new ResourceRepository();
    private final IdentifierException noResourceFound = new IdentifierException("Resource not found");

    public static ResourceRepository getInstance() {
        return INSTANCE;
    }

    public boolean updateResourceOfCompany(String name, Double weight, int companyId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_UPDATE_RESOURCE)) {
            CompanyRepository.getInstance().existenceCheck(companyId);
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

    public boolean insertResourceOfCompany(Resource resource, int companyId) {
        CompanyRepository.getInstance().existenceCheck(companyId);
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
            LOGGER.log(Level.SEVERE, GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        }
    }

    private ResourceRepository(){}

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
                    CompanyRepository.getInstance().existenceCheck(companyId);
                    LOGGER.warning("Entry could not be found or doesn't exist anymore");
                    throw noResourceFound;
                }
                con.commit();
                changed = resourceStmt.executeUpdate();
                if (changed == 0) {
                    LOGGER.warning("Resource could not be found or doesn't exit anymore");
                    throw noResourceFound;
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
                CompanyRepository.getInstance().existenceCheck(companyId);
                LOGGER.warning("Entry could not be found");
                throw noResourceFound;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, GENERIC_SQL_ERROR);
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
}
