package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;

import java.util.Date;
import java.util.Objects;

public class Resource {
    private final int id;
    private final String name;
    private final double price;
    private final double weight;
    private final Date addDate;

    public Resource(int id, String name, double price, double weight, Date addDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.addDate = addDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return id == resource.id &&
                name.equals(resource.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

   public JsonObject toJSON(){
        JsonObject json = new JsonObject();
        json.put("name", this.name);
        json.put("price", this.price);
        json.put("added", this.addDate.toString());
        json.put("added", this.weight);
        return json;
    }
}
