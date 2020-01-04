package org.airtel.ug.prometheusapp;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;



@Path("/")
public class Client {

    @Inject
    private BusinessLogic businessLogic;

    @GET
    @Path("/hello")
    public String getGreeting() {
        return businessLogic.sayHello();
    }

    @GET
    @Path("/reset")
    public String resetCounter() {
        return businessLogic.resetAllGauges();
    }
}
