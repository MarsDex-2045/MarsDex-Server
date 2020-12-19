package be.howest.ti.mars.logic.classes;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endpoint endpoint = (Endpoint) o;
        return id == endpoint.id && address.equals(endpoint.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address);
    }
}
