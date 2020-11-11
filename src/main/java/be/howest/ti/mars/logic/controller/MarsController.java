package be.howest.ti.mars.logic.controller;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    public JsonArray getCompanyResources(String id) {
        JsonArray res = new JsonArray();
        for (int i = 1; i<20; i++){
            JsonObject json = new JsonObject();
            json.put("id", id);
            JsonArray container = new JsonArray();
            JsonObject resource = new JsonObject();
            resource.put("name", "gold V"+i);
            resource.put("weight", 200 + i);
            resource.put("added", LocalDate.now());
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

    public Object getCompanyTransports(String id) {
        JsonArray transports = new JsonArray();
        for (int i = 1; i < 10; i++) {
            JsonObject json = new JsonObject();
            json.put("shippingId", id);
            json.put("status", "Payed");
            JsonArray resources = new JsonArray();
            for (int j = 1; j < 5; j++) {
                JsonObject resource = new JsonObject();
                resource.put("name", "Silver V" + 1);
                resource.put("weight", 200.45 + j);
                resource.put("added", LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth());
                resource.put("rarity", 0.05);
                resources.add(resource);
            }
            json.put("resources", resources);
            JsonObject sendTime = new JsonObject();
            sendTime.put("date", LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth());
            sendTime.put("time", LocalTime.now().getHour() + ":" + LocalTime.now().getMinute());
            json.put("sendTime", sendTime);
            JsonArray colonies = getColonies();
            json.put("sender", colonies.getValue(0));
            LocalDateTime receiveData = LocalDateTime.of(2052, 11, 5, 22, 22);
            if(i % 2 == 0){
                json.putNull("receiveTime");
            }
            else{
                JsonObject receiveTime = new JsonObject();
                receiveTime.put("date", receiveData.getYear() + "-" + receiveData.getMonthValue() + "-" + receiveData.getDayOfMonth());
                receiveTime.put("time", receiveData.getHour() + ":" + receiveData.getMinute());
                json.put("receiveTime", receiveTime);
            }
            json.put("receiver", colonies.getValue(2));
            transports.add(json);
        }
        return transports;
    }
}
