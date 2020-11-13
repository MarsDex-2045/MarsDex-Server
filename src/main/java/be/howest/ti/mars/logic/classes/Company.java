package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.*;

public class Company {
    private final int id;
    private final String name;
    private final String password;
    private final String email;
    private final String phone;
    private String storage;
    private final Map<Resource, Integer> resources;
    private final Deque<Notification> notifications;

    public Company(int id, String name, String password, String email, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.resources = new HashMap<>();
        this.notifications = new LinkedList<>();
        this.storage = null;
    }

    public boolean checkPassword(String givenPassword){
        return givenPassword.equals(this.password);
    }

    public void addResource(Resource resource, int amount){
        this.resources.put(resource, amount);
    }

    public JsonObject allResourcesToJSONObject(){
        JsonObject json = new JsonObject();
        json.put("id", this.id);
        json.put("resources", allResourcesToJSONArray());
        return json;
    }

    protected JsonArray allResourcesToJSONArray(){
        JsonArray resourcesList = new JsonArray();
        for (Map.Entry<Resource, Integer> entrySet : this.resources.entrySet()){
            double resourceWeight = entrySet.getKey().getWeight();
            double resourceAmount = entrySet.getValue();
            Resource totalResource = entrySet.getKey();
            totalResource.setWeight(resourceWeight * resourceAmount);
            resourcesList.add(totalResource.toJSON());
        }
        return resourcesList;
    }

    public JsonObject toJSON(){
        JsonObject json = new JsonObject();
        json.put("id", this.id)
                .put("name", this.name)
                .put("email", this.email)
                .put("phoneNumber", this.phone);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id &&
                name.equals(company.name) &&
                password.equals(company.password) &&
                email.equals(company.email) &&
                phone.equals(company.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, phone);
    }
}
