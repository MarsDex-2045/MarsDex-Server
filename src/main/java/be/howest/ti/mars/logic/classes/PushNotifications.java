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
    private static final String PUBLIC_KEY = "BA0gv_6WGAyW2Y7uv8z8TAN9vwOlFw_B4caUDZmQhuO3oN1ZI8ehRJ8RqXPgIik1ix0RSc86tIhnBw4T7FopmtU";
    private static final String PRIVATE_KEY = "3o1Ubths59P4Q6xxpVY8DVI8RWMAydPqUI0rZJx8mvk";
    public static void main(String[] args) throws GeneralSecurityException, InterruptedException, ExecutionException, JoseException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        PushService push = new PushService(PUBLIC_KEY, PRIVATE_KEY);
        String endpoint = "https://fcm.googleapis.com/fcm/send/dIavIBFZPfQ:APA91bHxqusiGSwc5DNMb2AUR9J9rnGpSAdtIU4bT1XEwDgE39J_uIlHju-du5YECHg3deNqmrz0CzSD9-HIbgIWZmXGQ14L8cxp9gtv9I4hHY2Ye1xP2jd1RvFgYiidOUaj1S6mCMsw";
        Subscription.Keys keys = new Subscription.Keys();
        keys.auth = "6RgtTmBTcTUPJIkHpzUBhg";
        keys.p256dh = "BDOv5Ghg3ON1q_eZE8tiYNB77OaadRbNm-Jxx5ZRGx3H4i3vvaWn6BaaMTzkaM6a3uP1ZiWVLQ_2fabOwqYIy8I";
        Subscription sub = new Subscription(endpoint, keys);
        Notification notif = new Notification(sub, "Hello");
        push.send(notif);
        System.out.println("done");
    }
}
