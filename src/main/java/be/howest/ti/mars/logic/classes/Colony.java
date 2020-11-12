package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Colony {
    private final int id;
    private final Location location;
    private final String name;
    HashMap<Integer, Resource> resources = new HashMap<>();

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public HashMap<Integer, Resource> getResources() {
        return resources;
    }

    public Colony(int id, String name, Location location, JsonObject resources) {
        this.id = id;
        this.location = location;
        this.name = name;
    }
    public void AddResource(int id, String name, double price, double weight, Calendar addDate){
    Resource add = new Resource(id,name,price,weight,addDate);
        resources.put(id,add);
    }
    public ArrayList<Resource> GetAllResources(){
        return new ArrayList<>(resources.values());
    }


    public JsonObject toJson(){
        JsonObject ColonyJSON = new JsonObject();
        ColonyJSON .put("id", this.getId());
        ColonyJSON .put("name", this.getName());
        ColonyJSON .put("location", this.getLocation());


        return ColonyJSON ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Colony colony = (Colony) o;
        return getId() == colony.getId() &&
                getLocation().equals(colony.getLocation()) &&
                getName().equals(colony.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLocation(), getName());
    }
}
