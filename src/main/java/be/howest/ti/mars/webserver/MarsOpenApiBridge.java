package be.howest.ti.mars.webserver;

import be.howest.ti.mars.logic.controller.MarsController;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

class MarsOpenApiBridge {
    private final MarsController controller;
    private static final String COMPANY_ID_PARAMETER = "companyId";
    private static final String COLONY_ID_PARAMETER = "colonyId";

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

    public Object makeCompany(RoutingContext ctx) {
        return controller.makeCompany();
    }

    public Boolean editCompanyResources(RoutingContext ctx) {
        return true;
    }

    public JsonObject addResourceToCompany(RoutingContext ctx) {
        controller.addResource(ctx.getBodyAsJson(), ctx.request().getParam(COMPANY_ID_PARAMETER));
        return new JsonObject();
    }
}
