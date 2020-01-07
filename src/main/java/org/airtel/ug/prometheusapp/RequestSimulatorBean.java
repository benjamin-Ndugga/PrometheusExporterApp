package org.airtel.ug.prometheusapp;

import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class RequestSimulatorBean {

    @Inject
    private BusinessLogic businessLogic;

    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void simulateRequest() {
        System.out.println("Simulating Request at "+new Date());
        businessLogic.sayHello();
    }

}
