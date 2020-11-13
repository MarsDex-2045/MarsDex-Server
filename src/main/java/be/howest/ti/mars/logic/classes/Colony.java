package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonObject;

import java.util.*;

public class Colony {
    private final int id;
    private final Location location;
    private final String name;
    private final List<Company> companies = new ArrayList<>();

    public Colony(int id, String name, Location location) {
        this.id = id;
        this.location = location;
        this.name = name;
    }


    public JsonObject toJson(){
        JsonObject json = new JsonObject();
        json .put("id", this.id);
        json .put("name", this.name);
        json .put("location", this.location);
        //W.I.P.

        return json ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Colony colony = (Colony) o;
        return id == colony.id &&
                location.equals(colony.location) &&
                name.equals(colony.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, name);
    }
}


