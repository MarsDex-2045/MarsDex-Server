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
}
