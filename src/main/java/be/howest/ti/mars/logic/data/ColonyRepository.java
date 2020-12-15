package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Colony;
import be.howest.ti.mars.logic.classes.Location;
import be.howest.ti.mars.logic.exceptions.CorruptedDataException;
import be.howest.ti.mars.logic.exceptions.H2RuntimeException;
import be.howest.ti.mars.logic.exceptions.IdentifierException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.data.H2Statements.*;
import static be.howest.ti.mars.logic.data.MarsRepository.transferToColony;

public class ColonyRepository {
    public static final Logger LOGGER = Logger.getLogger(ColonyRepository.class.getName());
    private static final ColonyRepository INSTANCE = new ColonyRepository();

    private ColonyRepository() {
    }

    public static ColonyRepository getInstance() {
        return INSTANCE;
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

    public Colony getColony(int id) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_COLONY)) {
            String companyIdColumnName = "COMPANY_ID";
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Colony colony = transferToColony(rs);
                colony.addCompany(CompanyRepository.getInstance().getCompany(rs.getInt(companyIdColumnName)));
                while (rs.next()) {
                    colony.addCompany(CompanyRepository.getInstance().getCompany(rs.getInt(companyIdColumnName)));
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

    public Colony getColonyOfCompany(int companyId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_COLONY_OF_COMPANY)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                return transferToColony(rs);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, MarsRepository.GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        } catch (IdentifierException ex) {
            CompanyRepository.getInstance().existenceCheck(companyId);
            LOGGER.log(Level.SEVERE, String.format("Company with ID %s doesn't have a colony; Possible corrupted data entry, Please check manually", companyId));
            throw new CorruptedDataException(String.format("Faulty entry in table COLONIES_COMPANIES: Company with id %s doesn't have a colony.", companyId));
        }
    }
}
