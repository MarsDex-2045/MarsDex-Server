package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.classes.*;
import be.howest.ti.mars.logic.data.*;
import be.howest.ti.mars.logic.exceptions.FormatException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.logging.Logger;

public class MarsController {
    public static final Logger LOGGER = Logger.getLogger(NotificationRepository.class.getName());


    public JsonArray getColonies() {
        JsonArray json = new JsonArray();
        ColonyRepository.getInstance().getAllColonies().forEach(
                colony -> json.add(colony.toShortJSON())
        );
        return json;
    }

    public JsonObject getColonyById(String idString) {
        int id = Integer.parseInt(idString);
        return ColonyRepository.getInstance().getColony(id).toJSON();
    }

    public JsonObject getCompanyResources(String idString) {
        int id = Integer.parseInt(idString);
        return CompanyRepository.getInstance().getCompany(id).allResourcesToJSONObject();
    }

    public JsonObject getCompanyById(String idString) {
        int id = Integer.parseInt(idString);
        Company company = CompanyRepository.getInstance().getCompany(id);
        Colony colony = ColonyRepository.getInstance().getColonyOfCompany(company.getId());
        JsonObject res = company.toJSON();
        res.put("colony", colony.getName());
        return res;
    }

    public JsonArray getCompanyTransports(String idString) {
        int id = Integer.parseInt(idString);
        Set<Shipment> shipments = ShipmentRepository.getInstance().getShipments(id);
        JsonArray json = new JsonArray();
        for (Shipment shipment : shipments) {
            json.add(shipment.toJSON());
        }
        return json;
    }

    public JsonObject makeCompany(Company company, int colonyId) {
        Company res = CompanyRepository.getInstance().addCompany(company, colonyId);
        JsonObject returnBody = new JsonObject();
        returnBody.put("id", res.getId()).put("processed", true);
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
        json.put("processed", ResourceRepository.getInstance().insertResourceOfCompany(newResource, Integer.parseInt(companyId)));
        return json;
    }

    public JsonObject editResourceFromCompany(JsonObject requestBody, String companyId) {
        String name = requestBody.getString("name");
        Double weight = requestBody.getDouble("weight");
        int id = Integer.parseInt(companyId);

        int weightDecimals = new BigDecimal(String.valueOf(weight)).scale();
        if (weightDecimals > 3) {
            throw new FormatException("Too many decimals; Only 3 or less decimals are accepted");
        }

        JsonObject json = new JsonObject();
        json.put("updated", ResourceRepository.getInstance().updateResourceOfCompany(name, weight, id));
        return json;
    }

    public JsonObject deleteResource(String resourceIdString, String companyIdString) {
        int resourceId = Integer.parseInt(resourceIdString);
        int companyId = Integer.parseInt(companyIdString);

        ResourceRepository.getInstance().deleteResourceFromCompany(resourceId, companyId);

        return new JsonObject().put("deleted", true);
    }

    public JsonObject authenticateCompany(JsonObject credentials) {
        String email = credentials.getString("email");
        String password = credentials.getString("password");

        Company company = CompanyRepository.getInstance().authenticateCompany(email, password);
        return new JsonObject().put("company", company.getName()).put("id", company.getId());
    }

    public JsonObject saveSubscription(String endpoint, String auth, String p256dh) {
        Endpoint dbInsert = NotificationRepository.getInstance().addSubscription(endpoint, auth, p256dh);
        return new JsonObject()
                .put("id", dbInsert.getId())
                .put("endpoint", dbInsert.getAddress())
                .put("auth", dbInsert.getAuth())
                .put("p256dh", dbInsert.getP256dh());
    }

    public JsonObject pushNotifications(String companyIdString, String pushIdString) {
        int companyId = Integer.parseInt(companyIdString);
        int pushId = Integer.parseInt(pushIdString);
        Company company = CompanyRepository.getInstance().getCompany(companyId);
        for (Resource resource : company.getResources()) {
            if (resource.getWeight() < 500) {
                String msg = resource.getName() + " is low in storage. Only " + resource.getWeight() + " KG remaining";
                NotificationRepository.getInstance().pushNotification(msg, pushId);
            }
        }
        return new JsonObject().put("push", true);
    }
}
