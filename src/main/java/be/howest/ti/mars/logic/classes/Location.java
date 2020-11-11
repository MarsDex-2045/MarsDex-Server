package be.howest.ti.mars.logic.classes;

import java.util.Objects;

public class Location {
    private final float  Longitude;
    private final  float  Latitude;
    private final float  Altitude;


    public Location(float  longitude, float  latitude, float  altitude) {
        Longitude = longitude;
        Latitude = latitude;
        Altitude = altitude;
    }

    public float  getLongitude() {
        return Longitude;
    }

    public float  getLatitude() {
        return Latitude;
    }

    public float  getAltitude() {
        return Altitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(Longitude, location.Longitude) &&
                Objects.equals(Latitude, location.Latitude) &&
                Objects.equals(Altitude, location.Altitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Longitude, Latitude, Altitude);
    }
}

