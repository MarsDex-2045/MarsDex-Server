package be.howest.ti.mars.logic.controller;

import io.vertx.core.json.JsonObject;

public class MarsController {
    public String getMessage() {
        return "Hello, Mars!";
    }

    public Object getColonies() {
        JsonObject res1 = new JsonObject();
        res1.put("id", 1);
        res1.put("name", "Colony1");
        res1.put("location", "location1");
        JsonObject res2 = new JsonObject();
        res2.put("id", 2);
        res2.put("name", "Colony2");
        res2.put("location", "location2");
        JsonObject[] res = new JsonObject[2];
        res[0]= res1;
        res[1]= res2;
        return res;
    }
}
