package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Resource {
    private final int id;
    private final String name;
    private final double price;
    private double weight;
    private final LocalDate addDate;

    public Resource(int id, String name, double price, double weight, LocalDate addDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.addDate = addDate;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getAddDate() {
        return addDate;
    }

    public int getId() {
        return id;
    }

    public JsonObject toJSON() {
        JsonObject json = new JsonObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("price", this.price);
        json.put("added", this.addDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        json.put("weight", this.weight);
        return json;
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
}
