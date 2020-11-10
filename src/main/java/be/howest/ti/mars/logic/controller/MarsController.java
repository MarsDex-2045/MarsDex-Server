package be.howest.ti.mars.logic.controller;

import io.vertx.core.json.JsonObject;

import java.lang.reflect.Array;

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
        res2.put("id","idnumero2");
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
        JsonObject[] Mock = new JsonObject[4];

        Mock[0] = res1;
        Mock[1] = res2;
        Mock[2] = res3;
        Mock[3] = res4;

        return Mock;

    }
}
