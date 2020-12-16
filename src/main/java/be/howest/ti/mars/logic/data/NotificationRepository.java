package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Company;
import be.howest.ti.mars.logic.exceptions.DuplicationException;
import be.howest.ti.mars.logic.exceptions.H2RuntimeException;
import be.howest.ti.mars.logic.exceptions.IdentifierException;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import static be.howest.ti.mars.logic.data.H2Statements.*;

import java.sql.*;
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

    }/*
    public void RegisterPush(){
        ecurity.addProvider(new BouncyCastleProvider());
        PushService push = new PushService(PUBLIC_KEY, PRIVATE_KEY);

        String endpoint = "https://updates.push.services.mozilla.com/wpush/v2/gAAAAABf2K4dH_PIx4CAq8lFvkycIML5xbIgTT8vb1Z7F4F5MaMsXinLJ_mvGR9ae2TDnxVvKjs4HlEbu-2LfWzulVWWOaomtbUZX0sqVqrY0LFWlC2eQfV7LzM8tOXjPu-UP2rzmlZ8T3JcvdyiyUxtO14CeHjqVBjXhJ4xv9kfsGMcXEvLkPI";
        Subscription.Keys keys = new Subscription.Keys();
        keys.auth = "77_3uUiAbwgk_Z_Q-aYVLA";
        keys.p256dh = "BIxpRjzcB5CW6N_e4YUpEeltjzG8LK5oaJ9DC3ema9axCV9YR8-Ke-PG1-d_REx7i629ljvRfoeLHm9yPCasaGA";
        Subscription sub = new Subscription(endpoint, keys);
        Notification notif = new Notification(sub, "leuk");

        push.send(notif);
        System.out.println(push.send(notif));
        System.out.println("done");
    }
    */
}
