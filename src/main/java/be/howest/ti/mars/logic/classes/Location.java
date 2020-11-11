package be.howest.ti.mars.logic.classes;

import java.util.Objects;

public class Location {
    private final double   Longitude;
    private final double Latitude;
    private final double Altitude;


    public Location(double longitude, double latitude, double altitude) {
        Longitude = longitude;
        Latitude = latitude;
        Altitude = altitude;
    }

    public double  getLongitude() {
        return Longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double  getAltitude() {
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

