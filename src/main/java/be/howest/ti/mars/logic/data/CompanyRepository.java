package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Company;
import be.howest.ti.mars.logic.exceptions.DuplicationException;
import be.howest.ti.mars.logic.exceptions.H2RuntimeException;
import be.howest.ti.mars.logic.exceptions.IdentifierException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.data.H2Statements.*;

public class CompanyRepository {
    public static final Logger LOGGER = Logger.getLogger(CompanyRepository.class.getName());
    private static final CompanyRepository INSTANCE = new CompanyRepository();

    private CompanyRepository(){}

    public static CompanyRepository getInstance() {
        return INSTANCE;
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
                company.addResource(MarsRepository.convertToResource(rs));
                while (rs.next()) {
                    company.addResource(MarsRepository.convertToResource(rs));
                }
                return company;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.INFO, "Potential empty company detected; Running Existence check.");
            return existenceCheck(companyId);
        }
    }

    protected Company existenceCheck(int companyId) {
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
                    return CompanyRepository.getInstance().getCompany(companyId);
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
            throw new H2RuntimeException(MarsRepository.GENERIC_SQL_ERROR);
        } catch (IdentifierException ex) {
            deleteCompany(companyId);
            throw ex;
        }
    }

    private void addColonyLink(int companyId, int colonyId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement prep = con.prepareStatement(H2_INSERT_COLONYLINK)) {
            prep.setInt(1, colonyId);
            prep.setInt(2, companyId);
            prep.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new IdentifierException("Faulty Colony ID");
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


    public Company authenticateCompany(String email, String password) {
        throw new UnsupportedOperationException();
    }
}
