package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;

import java.util.Calendar;
import java.util.Objects;

public class Resource {
    private final int id;
    private final String name;
    private final double price;
    private double weight;
    private final Calendar addDate;

    public Resource(int id, String name, double price, double weight, Calendar addDate) {
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

    public Calendar getAddDate() {
        return addDate;
    }

    public JsonObject toJSON() {
        JsonObject json = new JsonObject();
        json.put("name", this.name);
        json.put("price", this.price);
        json.put("added", calendarToDateFormat(this.addDate));
        json.put("weight", this.weight);
        return json;
    }

    protected static String calendarToDateFormat(Calendar calendar){
        return calendar.get(Calendar.YEAR) + "-" +
                calendar.get(Calendar.MONTH) + "-" +
                calendar.get(Calendar.DAY_OF_MONTH);
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
