package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;

import java.util.Objects;

public class Notification {
    private final int id;
    private final String heading;
    private final String message;

    public Notification(int id, String heading, String message) {
        this.id = id;
        this.heading = heading;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public JsonObject toJSON(){
        JsonObject json = new JsonObject();
        json.put("heading", this.heading);
        json.put("message", this.message);
        return json;
    }
}
