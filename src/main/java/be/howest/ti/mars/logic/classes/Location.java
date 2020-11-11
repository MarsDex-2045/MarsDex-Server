package be.howest.ti.mars.logic.classes;

import java.util.Objects;

public class Location {
    private final Double  Longitude;
    private final Double Latitude;
    private final Double Altitude;


    public Location(Double longitude, Double latitude, Double altitude) {
        Longitude = longitude;
        Latitude = latitude;
        Altitude = altitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public Double getAltitude() {
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
