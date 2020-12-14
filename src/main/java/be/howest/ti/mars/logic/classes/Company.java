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
    private int storage;
    private final Set<Resource> resources;
    private final Deque<Notification> notifications;

    public Company(int id, String name, String password, String email, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.resources = new HashSet<>();
        this.notifications = new LinkedList<>();
        this.storage = 0;
    }

    public Company(int id, String name, String password, String email, String phone, int storage) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.resources = new HashSet<>();
        this.notifications = new LinkedList<>();
        this.storage = storage;
    }

    public boolean checkPassword(String givenPassword){
        return givenPassword.equals(this.password);
    }

    public void addResource(Resource resource){
        this.resources.add(resource);
    }

    public JsonObject allResourcesToJSONObject(){
        JsonObject json = new JsonObject();
        json.put("id", this.id);
        json.put("resources", allResourcesToJSONArray());
        return json;
    }

    protected JsonArray allResourcesToJSONArray(){
        JsonArray resourcesList = new JsonArray();
        for (Resource resource : this.resources){
            resourcesList.add(resource.toJSON());
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

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, phone);
    }
}
