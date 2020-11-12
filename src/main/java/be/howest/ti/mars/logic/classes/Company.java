package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;

import java.util.*;

public class Company {
    private final int id;
    private final String name;
    private final String password;
    private final String email;
    private final String phone;
    private String storage;
    private final Map<Resource, Double> resources;
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

    public boolean checkPassword(String givenPassword){
        return givenPassword.equals(this.password);
    }

    private JsonArray allResources(){
        JsonArray resourcesList = new JsonArray();
        for (Map.Entry<Resource, Double> entrySet : this.resources.entrySet()){
            double resourceWeight = entrySet.getKey().getWeight();
            double resourceAmount = entrySet.getValue();
            Resource totalResource = entrySet.getKey();
            totalResource.setWeight(resourceWeight * resourceAmount);
            resourcesList.add(totalResource.toJSON());
        }
        return resourcesList;
    }
}
