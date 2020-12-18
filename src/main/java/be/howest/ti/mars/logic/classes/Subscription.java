package be.howest.ti.mars.logic.classes;

public class Subscription {

    private final String endpoint;
    private final String auth;
    private final String p256dh;



    public Subscription(String endpoint, String auth, String p256dh) {
        this.endpoint = endpoint;
        this.auth = auth;
        this.p256dh = p256dh;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getAuth() {
        return auth;
    }

    public String getP256dh() {
        return p256dh;
    }
}
