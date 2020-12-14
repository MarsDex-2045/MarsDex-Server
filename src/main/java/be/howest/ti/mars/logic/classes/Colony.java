package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.*;

public class Colony {
    private final int id;
    private final Location location;
    private final String name;
    private final Set<Company> companies;

    public Colony(int id, String name, Location location) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.companies  = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void addCompany(Company company){
        this.companies.add(company);
    }

    public JsonObject toJSON(){
        JsonObject json = new JsonObject();
        json.put("id", this.id)
                .put("name", this.name)
                .put("location", this.location.toJson());
        JsonArray allResources = new JsonArray();
        for (Company company: this.companies){
            for (int i = 0; i < company.allResourcesToJSONArray().size(); i++){
                allResources.add(company.allResourcesToJSONArray().getJsonObject(i));
            }
        }
        json.put("resources", allResources);
        return json;
    }

    public JsonObject toShortJSON(){
        JsonObject json = new JsonObject();
        json.put("id", this.id)
                .put("name", this.name)
                .put("location", this.location.toJson());
        return json;
    }

    public Set<Company> getCompanies() {
        return companies;
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

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, name);
    }
}
