package be.howest.ti.mars.webserver;

import be.howest.ti.mars.logic.classes.Company;
import be.howest.ti.mars.logic.controller.MarsController;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

class MarsOpenApiBridge {
    private final MarsController controller;
    private static final String COMPANY_ID_PARAMETER = "companyId";
    private static final String COLONY_ID_PARAMETER = "colonyId";
    private static final String RESOURCE_ID_PARAMETER = "resourceId";

    MarsOpenApiBridge() {
        this.controller = new MarsController();
    }

    public Object getMessage(RoutingContext ctx) {
        return controller.getMessage();
    }
    public JsonArray getColonies(RoutingContext ctx) {
        return controller.getColonies();
    }

    public JsonObject getCompanyResources(RoutingContext ctx) {
        return controller.getCompanyResources(ctx.request().getParam(COMPANY_ID_PARAMETER));
    }

    public Object getCompanyById(RoutingContext ctx) {
        return controller.getCompanyById(ctx.request().getParam(COMPANY_ID_PARAMETER));
    }

    public Object getCompanyTransports(RoutingContext ctx) {
        return controller.getCompanyTransports(ctx.request().getParam(COMPANY_ID_PARAMETER));
    }

    public Object getColonyById(RoutingContext ctx) {
        return controller.getColonyById(ctx.request().getParam(COLONY_ID_PARAMETER));
    }

    public JsonObject makeCompany(RoutingContext ctx) {
        JsonObject body = ctx.getBodyAsJson();
        String name = body.getString("name");
        int colonyIdInt = body.getInteger("colonyId");
        String email = body.getString("email");
        String password = body.getString("password");
        String phone = body.getString("phone");
        Company company = new Company(-1,name,password,email,phone);
        return controller.makeCompany(company,colonyIdInt);
    }

    public Boolean editCompanyResources(RoutingContext ctx) {
        return true;
    }

    public JsonObject addResourceToCompany(RoutingContext ctx) {
        return controller.addResource(ctx.getBodyAsJson(), ctx.request().getParam(COMPANY_ID_PARAMETER));
    }

    public Object deleteResourceOfCompany(RoutingContext ctx) {
        return controller.deleteResource(ctx.request().getParam(RESOURCE_ID_PARAMETER));
    }
}
