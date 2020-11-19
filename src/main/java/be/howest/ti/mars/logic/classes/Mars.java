package be.howest.ti.mars.logic.classes;

import java.util.HashSet;
import java.util.Set;

public class Mars {
    Set<Shipment> shipments;
    Set<Colony> colonies;

    public Mars(){
        this.colonies = new HashSet<>();
        this.shipments = new HashSet<>();
    }

    public Set<Shipment> getShipments() {
        return shipments;
    }

    public Set<Colony> getColonies() {
        return colonies;
    }
}
