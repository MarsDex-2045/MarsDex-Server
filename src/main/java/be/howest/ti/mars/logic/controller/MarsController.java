package be.howest.ti.mars.logic.controller;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class MarsController {
    public String getMessage() {
        return "Hello, Mars!";
    }

    public JsonArray getColonies() {
        JsonArray mock = new JsonArray();
        for (int i = 1; i<20; i++){
            JsonObject colony = new JsonObject();
            colony.put("id", i);
            colony.put("name", "V"+i);
            JsonObject location = new JsonObject();
            location.put("longitude", -74.006015);
            location.put("latitude", 40.712728);
            location.put("altitude", 69.420);
            colony.put("location", location);
            mock.add(colony);
        }
        return mock;
    }

    public JsonArray getCompaniesResources(String id) {
        JsonArray res = new JsonArray();
        for (int i = 1; i<20; i++){
            JsonObject json = new JsonObject();
            json.put("id", id);
            JsonArray container = new JsonArray();
            JsonObject resource = new JsonObject();
            resource.put("name", "gold V"+i);
            resource.put("weight", 200 + i);
            resource.put("added", "2020-01-20");
            resource.put("rarity", 0.005);
            container.add(resource);
            json.put("resource", container);
            res.add(json);
        }
        return res;
    }

    public Object getCompanyById(String id) {
        JsonObject json = new JsonObject();
        json.put("id", id);
        json.put("colony", "b71f5881-cec6-49ee-9c99-e1ea32146913");
        json.put("name", "MarsDex");
        json.put("email", "marsdex@mars.com");
        json.put("phoneNumber", "+3265788999");
        return json;
    }

    public JsonObject getColonyById(String id) {
        JsonObject json = new JsonObject();
        json.put("id", id);
        json.put("name", "Jamerson's landing");
        JsonObject location = new JsonObject();
        location.put("longitude", -74.006015);
        location.put("latitude", 40.712728);
        location.put("altitude", 69.420);
        json.put("location", location);
        JsonArray resources = new JsonArray();
        for (int i = 1; i<20; i++){
            JsonObject resource = new JsonObject();
            resource.put("name", "gold V"+i);
            resource.put("weight", 200 + i);
            resource.put("added", "2020-01-20");
            resource.put("rarity", 0.005);
            resources.add(resource);
        }
        json.put("resources", resources);
        return json;
    }
}
