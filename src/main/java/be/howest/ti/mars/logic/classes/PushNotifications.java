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

        String endpoint = "https://fcm.googleapis.com/fcm/send/eWFL6YkGXgI:APA91bGEJbfgcQLzwdHdzffeMC8tNFDG1hyh7y5TQaFZNF0DdvtqmtXnjWoJdws2gKkKU_XaKPTesbmDxpj4MtAVupQ5lg_1rJghV1mj73nZOCBbIijPxHLXKIauEKWQTdBcLj0F_gJR";
        Subscription.Keys keys = new Subscription.Keys();
        keys.auth = "CV9JTCTBdqevlX9O75a_TA";
        keys.p256dh = "BN-tysBjoKXsguvYAeJ6llDLwvWLv7eeLhv3UgDKhDkx2iFMw9EkYlDWqGwmJaznha_7KEKhzNyZKiJVHLb3lo8";
        Subscription sub = new Subscription(endpoint, keys);
        Notification notif = new Notification(sub, "Hello");

        push.send(notif);
        System.out.println(push.send(notif));
        System.out.println("done");
    }
}
