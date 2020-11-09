package be.howest.ti.mars.webserver;

import be.howest.ti.mars.logic.controller.MarsController;
import io.vertx.ext.web.RoutingContext;

class MarsOpenApiBridge {
    private final MarsController controller;

    MarsOpenApiBridge() {
        this.controller = new MarsController();
    }

    public Object getMessage(RoutingContext ctx) {
        return controller.getMessage();
    }
}
