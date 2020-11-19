package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;

import java.util.HashSet;
import java.util.Set;

public class Mars {
    Set<Shipment> shipments;
    Set<Colony> colonies;

    public Mars(){
        this.colonies = new HashSet<>();
        this.shipments = new HashSet<>();
    }

    public Mars(Set<Colony> colonies, Set<Shipment> shipments){
        this.colonies = colonies;
        this.shipments = shipments;
    }

    public Set<Shipment> getShipments() {
        return shipments;
    }

    public Set<Colony> getColonies() {
        return colonies;
    }

    public JsonArray getColoniesAsJSON(){
        JsonArray json = new JsonArray();
        for (Colony colony: this.colonies){
            json.add(colony.toJSON());
        }
        return json;
    }

    public JsonArray getTransportAsJSON(){
        JsonArray json = new JsonArray();
        for (Shipment shipment: this.shipments){
            json.add(shipment.toJSON());
        }
        return json;
    }
}
