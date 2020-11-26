package be.howest.ti.mars.webserver;

import be.howest.ti.mars.logic.classes.Company;
import be.howest.ti.mars.logic.controller.MarsController;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

class MarsOpenApiBridge {
    private final MarsController controller;

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
        return controller.getCompanyResources(ctx.request().getParam("companyId"));
    }

    public Object getCompanyById(RoutingContext ctx) {
        return controller.getCompanyById(ctx.request().getParam("companyId"));
    }

    public Object getCompanyTransports(RoutingContext ctx) {
        return controller.getCompanyTransports(ctx.request().getParam("companyId"));
    }

    public Object getColonyById(RoutingContext ctx) {
        return controller.getColonyById(ctx.request().getParam("colonyId"));
    }

    public JsonObject makeCompany(RoutingContext ctx) {
        JsonObject body = ctx.getBodyAsJson();
        String name = body.getString("name");
        String colony = body.getString("colony");
        String email = body.getString("email");
        String password = body.getString("password");
        String phone = body.getString("phone");
        Company company = new Company(0,name,password,email,phone);
        return controller.makeCompany(company);
    }

    public Boolean editCompanyResources(RoutingContext ctx) {
        return true;
    }

    public boolean addResourceToCompany(RoutingContext ctx) {
        return true;
    }
}
