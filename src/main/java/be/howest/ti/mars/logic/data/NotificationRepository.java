package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Company;
import be.howest.ti.mars.logic.exceptions.DuplicationException;
import be.howest.ti.mars.logic.exceptions.H2RuntimeException;
import be.howest.ti.mars.logic.exceptions.IdentifierException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NotificationRepository {
    public static final Logger LOGGER = Logger.getLogger(NotificationRepository.class.getName());
    private static final NotificationRepository INSTANCE = new NotificationRepository();

    private NotificationRepository(){}

    public static NotificationRepository getInstance() {
        return INSTANCE;
    }

    public void addSubscription(String endpoint, String auth, String p256dh) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement prep = con.prepareStatement(H2_INSERT_SUBSCRIPTION)) {
            prep.setInt(1, colonyId);
            prep.setInt(2, companyId);
            prep.setInt(3, companyId);
            prep.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new IdentifierException("Faulty Colony ID");
        }

}
