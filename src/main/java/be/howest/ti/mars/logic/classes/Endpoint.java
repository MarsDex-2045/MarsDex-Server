package be.howest.ti.mars.logic.classes;

public class Endpoint {
    private final int id;
    private final String address;
    private final String auth;
    private final String p256dh;

    public Endpoint(int id, String address, String auth, String p256dh) {
        this.id = id;
        this.address = address;
        this.auth = auth;
        this.p256dh = p256dh;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getAuth() {
        return auth;
    }

    public String getP256dh() {
        return p256dh;
    }
}
