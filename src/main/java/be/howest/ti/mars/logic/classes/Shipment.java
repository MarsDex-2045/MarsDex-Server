package be.howest.ti.mars.logic.classes;

import java.util.Calendar;

public class Shipment {
    private final int id;
    private final Colony sender;
    private final Calendar sendTime;
    private final Colony receiver;
    private Calendar endTime;
    Resource[] content;
    Status status;

    public Shipment(int id, Colony sender, Calendar sendTime, Colony receiver, Resource[] content) {
        this.id = id;
        this.sender = sender;
        this.sendTime = sendTime;
        this.receiver = receiver;
        this.content = content;
        this.status = Status.PAYED;
    }
}
