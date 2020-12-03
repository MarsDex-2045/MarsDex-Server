package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.data.MarsRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class MarsController {

    public String getMessage() {
        return "Hello, Mars!";
    }

    public JsonArray getColonies() {
        JsonArray json = new JsonArray();
        MarsRepository.getInstance().getAllColonies().forEach(
                colony -> json.add(colony.toShortJSON())
        );
        return json;
    }

    public JsonObject getColonyById(String idString) {
        int id = Integer.parseInt(idString);
        return MarsRepository.getInstance().getColony(id).toJSON();
    }

    public JsonObject getCompanyResources(String idString) {
        int id = Integer.parseInt(idString);
        return MarsRepository.getInstance().getCompany(id).allResourcesToJSONObject();
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

    public Object getCompanyTransports(String idString) {
        int id = Integer.parseInt(idString);
        return MarsRepository.getInstance().getShipments(id);
    }

    public JsonObject makeCompany() {
        JsonObject json = new JsonObject();
        json.put("processed", true);
        json.put("id", 2);
        return json;
    }
}
