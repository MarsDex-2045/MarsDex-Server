package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;
import java.util.Set;

public class Shipment {
    private final int id;
    private final Colony sender;
    private final Calendar sendTime;
    private final Colony receiver;
    private Calendar endTime;
    private final Set<Resource> content;
    private final Status status;

    public Shipment(int id, Colony sender, Colony receiver, Set<Resource> content) {
        this.id = id;
        this.sender = sender;
        LocalDateTime time = LocalDateTime.now();
        this.sendTime = new Calendar.Builder().setDate(time.getYear(), time.getMonthValue(), time.getDayOfMonth())
                .setTimeOfDay(time.getHour(), time.getMinute(), time.getSecond())
                .build();
        this.receiver = receiver;
        this.content = content;
        this.status = Status.PAYED;
    }

    public Shipment(int id, Colony sender, Calendar sendTime, Colony receiver, Calendar endTime, Set<Resource> content, Status status) {
        this.id = id;
        this.sender = sender;
        this.sendTime = sendTime;
        this.receiver = receiver;
        this.endTime = endTime;
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
        json.put("sender", this.sender.toJSON());
        if(this.endTime != null){
            json.put("receiveTime", timeToJSON(this.endTime));
        }
        json.put("receiver", this.receiver.toJSON());
        return json;
    }

    private JsonObject timeToJSON(Calendar calendar){
        JsonObject res = new JsonObject();
        res.put("date", Resource.calendarToDateFormat(calendar));
        res.put("time", calendarToTimeFormat(calendar));
        return res;
    }

    private static String calendarToTimeFormat(Calendar calendar){
        return calendar.get(Calendar.HOUR) + ":" +
                calendar.get(Calendar.MINUTE);
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
