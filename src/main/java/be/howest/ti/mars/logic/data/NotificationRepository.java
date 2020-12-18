package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.exceptions.IdentifierException;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;

import static be.howest.ti.mars.logic.data.H2Statements.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NotificationRepository {
    public static final Logger LOGGER = Logger.getLogger(NotificationRepository.class.getName());
    private static final NotificationRepository INSTANCE = new NotificationRepository();
    private static final String PUBLIC_KEY = "BAcgnkauwyqPz1MI31KN9sN8wpIPEMkfhbmEijXcodAUzCoy1u5tIePU7HpATIv2VQOFN45Mu4Wc1qx-6HRxv_g";
    private static final String PRIVATE_KEY = "4WTRfJSoLwBVSoq5d6U_ZgY7JBMcupC0SxnTTApwLFE";

    private NotificationRepository() {
    }

    public static NotificationRepository getInstance() {
        return INSTANCE;
    }

    public void addSubscription(String endpoint, String auth, String p256dh) {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement prep = con.prepareStatement(H2_INSERT_SUBSCRIPTION)) {
            prep.setString(1, endpoint);
            prep.setString(2, auth);
            prep.setString(3, p256dh);
            prep.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new IdentifierException("SQL problem");
        }

    }

    public Set<be.howest.ti.mars.logic.classes.Subscription> getNotification() {
        Set<be.howest.ti.mars.logic.classes.Subscription> res = new HashSet<>();
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_SUBSCRIPTIONS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                be.howest.ti.mars.logic.classes.Subscription subscriptionInfo = new be.howest.ti.mars.logic.classes.Subscription(rs.getString("endpoint"), rs.getString("auth"), rs.getString("p256dh"));
                res.add(subscriptionInfo);
            }
        } catch (SQLException throwables) {
            LOGGER.log(Level.WARNING, "Something went wrong with executing H2 Query; Returning empty array");
        }
        return res;
    }

    public void pushNotification(Set<be.howest.ti.mars.logic.classes.Subscription> Subscribers)  {
        Security.addProvider(new BouncyCastleProvider());
        PushService push = null;
        try {
            push = new PushService(PUBLIC_KEY, PRIVATE_KEY);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        for (be.howest.ti.mars.logic.classes.Subscription value : Subscribers) {
            String endpoint = value.getEndpoint();
            Subscription.Keys keys = new Subscription.Keys();
            keys.auth = value.getAuth();
            keys.p256dh = value.getP256dh();
            Subscription sub = new Subscription(endpoint, keys);
            Notification notif = null;
            try {
                notif = new Notification(sub, "leuk2");
            } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
            try {
                assert push != null;
                push.send(notif);
            } catch (GeneralSecurityException | IOException | JoseException | ExecutionException | InterruptedException e) {
                LOGGER.log(Level.INFO, "push failed ");
            }
        }


    }

}
