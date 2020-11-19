package be.howest.ti.mars.webserver;

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
        return controller.getCompanyResources(ctx.request().getParam("id"));
    }

    public Object getCompanyById(RoutingContext ctx) {
        return controller.getCompanyById(ctx.request().getParam("id"));
    }

    public Object getCompanyTransports(RoutingContext ctx) {
        return controller.getCompanyTransports(ctx.request().getParam("id"));
    }

    public Object getColonyById(RoutingContext ctx) {
        return controller.getColonyById(ctx.request().getParam("id"));
    }

    public Object makeCompany(RoutingContext ctx) {
        return controller.makeColony();
    }

    public Boolean editCompanyResources(RoutingContext ctx) {
        return true;
    }

    public boolean addResourceToCompany(RoutingContext ctx) {
        return true;
    }
}
