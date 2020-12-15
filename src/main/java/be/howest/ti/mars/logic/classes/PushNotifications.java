package be.howest.ti.mars.logic.classes;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.concurrent.ExecutionException;

public class PushNotifications {
    private static final String PUBLIC_KEY = "BAcgnkauwyqPz1MI31KN9sN8wpIPEMkfhbmEijXcodAUzCoy1u5tIePU7HpATIv2VQOFN45Mu4Wc1qx-6HRxv_g";
    private static final String PRIVATE_KEY = "4WTRfJSoLwBVSoq5d6U_ZgY7JBMcupC0SxnTTApwLFE";

    public static void main(String[] args) throws GeneralSecurityException, InterruptedException, ExecutionException, JoseException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        PushService push = new PushService(PUBLIC_KEY, PRIVATE_KEY);

        String endpoint = "https://fcm.googleapis.com/fcm/send/csPLLqWMAZk:APA91bGsIT98ZXSKWJM0oCkL4IjDSNrqKv31bIIWHmMXBlkF9--KByQWQYQlTQY_7d1NhHcmjjlm_dClNttzQXU-TVCBfo_JY_MOF2lDRetAB5wvwC7Ona_-E8in6we4MYBCfQhseS2a";
        Subscription.Keys keys = new Subscription.Keys();
        keys.auth = "s03BNsnunznMLpAsvzuhkg";
        keys.p256dh = "BJ-ADMN1c01Nu5PQd6DtQaQTlyhN7Z4hL3At-1oXSynWPey_Pc6jhiZPeFHWMGwi6H_RWobmS-YGmsQNqp5FGxY";
        Subscription sub = new Subscription(endpoint, keys);
        Notification notif = new Notification(sub, "Hello");

        push.send(notif);
        System.out.println(push.send(notif));
        System.out.println("done");
    }
}
