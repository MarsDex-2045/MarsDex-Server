package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Endpoint;
import be.howest.ti.mars.logic.exceptions.*;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.data.H2Statements.*;


public class NotificationRepository {
    public static final Logger LOGGER = Logger.getLogger(NotificationRepository.class.getName());
    private static final NotificationRepository INSTANCE = new NotificationRepository();
    private static final String PUBLIC_KEY = "BAcgnkauwyqPz1MI31KN9sN8wpIPEMkfhbmEijXcodAUzCoy1u5tIePU7HpATIv2VQOFN45Mu4Wc1qx-6HRxv_g";
    private static final String PRIVATE_KEY = "4WTRfJSoLwBVSoq5d6U_ZgY7JBMcupC0SxnTTApwLFE";
    private static final int DUPLICATION_ERROR = 23505;

    private NotificationRepository() {
    }

    public static NotificationRepository getInstance() {
        return INSTANCE;
    }

    public Endpoint addSubscription(String endpoint, String auth, String p256dh) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement prep = con.prepareStatement(H2_INSERT_SUBSCRIPTION)) {
            prep.setString(1, endpoint);
            prep.setString(2, auth);
            prep.setString(3, p256dh);
            prep.executeUpdate();
            return INSTANCE.getEndpoint(endpoint);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == DUPLICATION_ERROR) {
                return INSTANCE.getEndpoint(endpoint);
            } else {
                LOGGER.log(Level.SEVERE, MarsRepository.GENERIC_SQL_ERROR);
                throw new H2RuntimeException(ex.getMessage());
            }
        }
    }

    private Endpoint getEndpoint(String endpoint) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_SUBSCRIPTION_BY_ENDPOINT)) {
            stmt.setString(1, endpoint);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Endpoint(rs.getInt("ID"),
                            rs.getString("endpoint"),
                            rs.getString("auth"),
                            rs.getString("p256dh"));
                } else {
                    throw new IdentifierException("Endpoint could not be found");
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, MarsRepository.GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        }
    }

    private Endpoint getEndpoint(int pushId) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_SUBSCRIPTION_BY_ID)) {
            stmt.setInt(1, pushId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Endpoint(rs.getInt("ID"),
                            rs.getString("endpoint"),
                            rs.getString("auth"),
                            rs.getString("p256dh"));
                } else {
                    throw new IdentifierException("Endpoint could not be found");
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, MarsRepository.GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        }
    }

    public void pushNotification(String message, int pushId) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Endpoint endpoint = INSTANCE.getEndpoint(pushId);
            PushService push = new PushService(PUBLIC_KEY, PRIVATE_KEY);
            String address = endpoint.getAddress();
            Subscription.Keys keys = new Subscription.Keys();
            keys.auth = endpoint.getAuth();
            keys.p256dh = endpoint.getP256dh();
            Subscription sub = new Subscription(address, keys);
            Notification notification = new Notification(sub, message);
            push.send(notification);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, "Cryptographic error");
            throw new FormatException(ex.getMessage());
        } catch (ExecutionException | JoseException | IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to send push notification");
            throw new PushException("Unable to send the notification");
        } catch (NoSuchProviderException e) {
            LOGGER.log(Level.SEVERE, "Requested provider (Bouncy Castle) could not be found");
            throw new VerificationException("Provider could not be found");
        } catch (GeneralSecurityException ex) {
            LOGGER.log(Level.SEVERE, "Public or Private Key is faulty");
            throw new VerificationException(ex.getMessage());
        } catch (InterruptedException ex){
            LOGGER.log(Level.SEVERE, "Something has gone wrong with the thread");
            Thread.currentThread().interrupt();
        }
    }


}
