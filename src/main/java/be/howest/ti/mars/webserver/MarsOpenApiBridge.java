package be.howest.ti.mars.webserver;

import be.howest.ti.mars.logic.controller.MarsController;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

class MarsOpenApiBridge {
    private final MarsController controller;

    MarsOpenApiBridge() {
        this.controller = new MarsController();
    }

    public Object getMessage(RoutingContext ctx) {
        return controller.getMessage();
    }
    public Object getColonies(RoutingContext ctx) {
        return controller.getColonies();
    }

    public JsonArray getCompaniesResources(RoutingContext ctx) {
        return controller.getCompaniesResources(ctx.request().getParam("id"));
    }

    public Object getCompanyById(RoutingContext ctx) {
        return controller.getCompanyById(ctx.request().getParam("id"));
    }

    public Object getColonyById(RoutingContext ctx) {
        return controller.getColonyById(ctx.request().getParam("id"));
    }
}
