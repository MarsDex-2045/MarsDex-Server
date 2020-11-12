package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;

public class Colony {
    private final int id;
    private final Location location;
    private final String name;

    public Colony(int id,  String name,Location location) {
        this.id = id;
        this.location = location;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public JsonObject toJson(){
        JsonObject ColonyJSON = new JsonObject();
        ColonyJSON .put("id", this.getId());
        ColonyJSON .put("name", this.getName());
        ColonyJSON .put("location", this.getLocation());
        return ColonyJSON ;
    }


}
