package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

public class Shipment {
    private final int id;
    private final Colony sender;
    private final LocalDateTime sendTime;
    private final Colony receiver;
    private LocalDateTime endTime;
    private final Set<Resource> content;
    private final Status status;

    public Shipment(int id, Colony sender, Colony receiver, Set<Resource> content) {
        this.id = id;
        this.sender = sender;
        this.sendTime = LocalDateTime.now();
        this.receiver = receiver;
        this.content = content;
        this.status = Status.PAYED;
    }

    public Shipment(int id, Colony sender, LocalDateTime sendTime, Colony receiver, LocalDateTime endTime, Set<Resource> content, Status status) {
        this.id = id;
        this.sender = sender;
        this.sendTime = sendTime;
        this.receiver = receiver;
        this.endTime = endTime;
        this.content = content;
        this.status = status;
    }

    public Shipment(int id, Colony sender, LocalDateTime sendTime, Colony receiver, Set<Resource> content, Status status) {
        this.id = id;
        this.sender = sender;
        this.sendTime = sendTime;
        this.receiver = receiver;
        this.endTime = null;
        this.content = content;
        this.status = status;
    }


    public JsonObject toJSON(){
        JsonObject json = new JsonObject();
        json.put("shippingId", this.id);
        json.put("status", this.status.name());
        JsonArray resources = new JsonArray();
        for (Resource resource : this.content){
            resources.add(resource.toJSON());
        }
        json.put("resources", resources);
        json.put("sendTime", timeToJSON(this.sendTime));
        json.put("sender", this.sender.toShortJSON());
        if(this.endTime != null){
            json.put("receiveTime", timeToJSON(this.endTime));
        } else{
            json.putNull("receiveTime");
        }
        json.put("receiver", this.receiver.toShortJSON());
        return json;
    }

    private JsonObject timeToJSON(LocalDateTime dateTime){
        JsonObject res = new JsonObject();
        res.put("date", dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        res.put("time", dateTime.format(DateTimeFormatter.ofPattern("hh:mm")));
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return id == shipment.id &&
                sender.equals(shipment.sender) &&
                sendTime.equals(shipment.sendTime) &&
                receiver.equals(shipment.receiver) &&
                Objects.equals(endTime, shipment.endTime) &&
                content.equals(shipment.content) &&
                status == shipment.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, sendTime, receiver, endTime, content, status);
    }
}
