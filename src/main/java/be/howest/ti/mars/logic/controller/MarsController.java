package be.howest.ti.mars.logic.controller;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MarsController {
    public String getMessage() {
        return "Hello, Mars!";
    }

    public Object getColonies() {

        JsonObject res1 = new JsonObject();
        res1.put("id", "idnumero1");
        res1.put("name", "RutgerIsDeBeste");
        res1.put("storage", 1500);
        JsonObject loc1 = new JsonObject();
        //make JSon object Location
        loc1.put("longitude", 1);
        loc1.put("langitude", 2);
        loc1.put("Altitude", 3);
        //add location to the Jsonobject 1
        res1.put("location", loc1);

        JsonObject res2 = new JsonObject();
        res2.put("id", "idnumero2");
        res2.put("name", "RutgerIsDeBeste2");
        res2.put("storage", 15004);
        JsonObject loc2 = new JsonObject();
        //make JSon object Location
        loc2.put("longitude", 44);
        loc2.put("langitude", 66);
        loc2.put("Altitude", 88);
        //add location to the Jsonobject 2
        res2.put("location", loc2);

        JsonObject res3 = new JsonObject();
        res3.put("id", "idnumero3");
        res3.put("name", "RutgerIsDeBeste3");
        res3.put("storage", 5400);
        JsonObject loc3 = new JsonObject();
        //make JSon object Location
        loc3.put("longitude", 11);
        loc3.put("langitude", 22);
        loc3.put("Altitude", 33);
        //add location to the Jsonobject 3
        res3.put("location", loc3);

        JsonObject res4 = new JsonObject();
        res4.put("id", "idnumero4");
        res4.put("name", "RutgerIsDeBeste4");
        res4.put("storage", 5000);
        JsonObject loc4 = new JsonObject();
        //make JSon object Location
        loc4.put("longitude", 45);
        loc4.put("langitude", 42);
        loc4.put("Altitude", 63);
        //add location to the Jsonobject 4
        res4.put("location", loc4);

        JsonObject res5 = new JsonObject();
        res5.put("id", "idnumero5");
        res5.put("name", "RutgerIsDeBeste5");
        res5.put("storage", 1500);
        JsonObject loc5 = new JsonObject();
        //make JSon object Location
        loc5.put("longitude", 1);
        loc5.put("langitude", 2);
        loc5.put("Altitude", 3);
        //add location to the Jsonobject 5
        res5.put("location", loc5);

        JsonObject res6 = new JsonObject();
        res6.put("id", "idnumero6");
        res6.put("name", "RutgerIsDeBeste6");
        res6.put("storage", 15004);
        JsonObject loc6 = new JsonObject();
        //make JSon object Location
        loc6.put("longitude", 44);
        loc6.put("langitude", 66);
        loc6.put("Altitude", 88);
        //add location to the Jsonobject 6
        res6.put("location", loc6);

        JsonObject res7 = new JsonObject();
        res7.put("id", "idnumero7");
        res7.put("name", "RutgerIsDeBeste7");
        res7.put("storage", 5400);
        JsonObject loc7 = new JsonObject();
        //make JSon object Location
        loc7.put("longitude", 11);
        loc7.put("langitude", 22);
        loc7.put("Altitude", 33);
        //add location to the Jsonobject 7
        res7.put("location", loc7);

        JsonObject res8 = new JsonObject();
        res8.put("id", "idnumero8");
        res8.put("name", "RutgerIsDeBeste8");
        res8.put("storage", 5000);
        JsonObject loc8 = new JsonObject();
        //make JSon object Location
        loc8.put("longitude", 45);
        loc8.put("langitude", 42);
        loc8.put("Altitude", 63);
        //add location to the Jsonobject 8
        res8.put("location", loc8);
        JsonObject[] mock = new JsonObject[8];

        mock[0] = res1;
        mock[1] = res2;
        mock[2] = res3;
        mock[3] = res4;
        mock[4] = res5;
        mock[5] = res6;
        mock[6] = res7;
        mock[7] = res8;

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

    public Object getCompanyTransports(String id) {
        JsonArray transports = new JsonArray();
        for (int i = 1; i < 20; i++) {
            JsonObject json = new JsonObject();
            json.put("shippingId", id);
            json.put("status", "Payed");
            JsonArray resources = new JsonArray();
            for (int j = 1; j < 5; j++) {
                JsonObject resource = new JsonObject();
                resource.put("name", "Silver V" + 1);
                resource.put("weight", 200.45 + j);
                resource.put("added", LocalDate.now());
                resource.put("rarity", 0.05);
                resources.add(resource);
            }
            json.put("resources", resources);
            json.put("sendTime", LocalDateTime.now());
            JsonObject sender = new JsonObject();
            sender.put("sender", "?");
        }
        return transports;
    }
}
