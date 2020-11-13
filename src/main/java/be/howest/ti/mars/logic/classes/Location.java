package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;

import java.util.Objects;

public class Location {
    private final double longitude;
    private final double latitude;
    private final double altitude;


    public Location(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public JsonObject toJson() {
        JsonObject locationJSON = new JsonObject();
        locationJSON.put("longitude", this.getLongitude());
        locationJSON.put("latitude", this.getLatitude());
        locationJSON.put("altitude", this.getAltitude());
        return locationJSON;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(longitude, location.longitude) &&
                Objects.equals(latitude, location.latitude) &&
                Objects.equals(altitude, location.altitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude, altitude);
    }
}

