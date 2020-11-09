package be.howest.ti.mars.logic.controller;

import io.vertx.core.json.JsonObject;

public class MarsController {
    public String getMessage() {
        return "Hello, Mars!";
    }
    public String getColonies() {
        JsonObject res1 = new JsonObject();
        res1.put("uuid", 1);
        res1.put("name", "Colony1");
        res1.put("location", "location1");
        JsonObject res2 = new JsonObject();
        res2.put("uuid", 1);
        res2.put("name", "Colony1");
        res2.put("location", "location1");
        return "["+res1+","+res2+"]";
    }

}
