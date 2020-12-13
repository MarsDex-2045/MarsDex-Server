package be.howest.ti.mars.logic.controller;


import be.howest.ti.mars.logic.classes.Company;
import be.howest.ti.mars.logic.classes.Resource;
import be.howest.ti.mars.logic.classes.Shipment;

import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.exceptions.FormatException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import java.util.Set;

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
/*
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
                resource.put("price", 20.221 + 1);
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
            if (i % 2 == 0) {
                json.putNull("receiveTime");
            } else {
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
*/
    public JsonArray getCompanyTransports(String idString) {
        int id = Integer.parseInt(idString);
        Set<Shipment> shipments = MarsRepository.getInstance().getShipments(id);
        JsonArray json = new JsonArray();
        for (Shipment shipment : shipments) {
            json.add(shipment.toJSON());
        }
        return json;
    }

    public JsonObject makeCompany(Company company, int colonyId) {
        int companyId = 0;
        Map<Integer, Boolean> res = MarsRepository.getInstance().addCompany(company, colonyId);
        Set<Integer> keySet = res.keySet();
        JsonObject returnBody = new JsonObject();
        for (Integer id : keySet) {
            companyId = id;
        }
        returnBody.put("company-id", companyId).put("succeeded", true);
        return returnBody;
    }

    public JsonObject addResource(JsonObject resource, String companyId) {
        Double price = resource.getDouble("price");
        Double weight = resource.getDouble("weight");
        String name = resource.getString("name");

        int priceDecimals = new BigDecimal(String.valueOf(price)).scale();
        int weightDecimals = new BigDecimal(String.valueOf(weight)).scale();
        if (priceDecimals > 3 || weightDecimals > 3) {
            throw new FormatException("Too many decimals; Only 3 or less decimals are accepted");
        }

        Resource newResource = new Resource(-1, name, price, weight, LocalDate.now());
        JsonObject json = new JsonObject();
        json.put("processed", MarsRepository.getInstance().insertResourceOfCompany(newResource, Integer.parseInt(companyId)));
        return json;
    }
}
