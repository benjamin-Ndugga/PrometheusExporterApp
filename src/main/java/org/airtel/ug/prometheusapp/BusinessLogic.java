package org.airtel.ug.prometheusapp;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BusinessLogic {

    private Counter processed_transactions_total;
    private Gauge product_decrements_shillings;
    //private Summary method_calls_response_seconds;

    private static final String BILLING_OPTIONS[] = {"airtime", "airtel_money"};

    private static final String VOICE_PRODUCTS[] = {
        "daily_100MB",
        "daily_500MB",
        "daily_700MB",
        "1GB_Phone",
        "10GB_Phone",
        "daily_500MB",
        "3Day_phone",
        "4Day_phone",
        "kiromore",
        "kinokika",
        "ild_usa",
        "ild_ken",
        "ild_china",
        "cmb_10",
        "cmb_20",
        "cmb_25",
        "cmb_75",
        "cmb_30",
        "cmb_60",
        "20GB_Phone",
        "daily_700MB",
        "6Day_phone",
        "10Day_phone",
        "tooti",
        "roaming_2k",
        "private_apn"
    };

    @PostConstruct
    private void initMetricInfo() {

            processed_transactions_total = Counter
                .build()
                .name("processed_transactions_total")
                .help("Total transactions processed")
                .labelNames("type")
                .register();

        product_decrements_shillings = Gauge
                .build()
                .name("product_decrements_shillings")
                .help("total decrements made per product")
                .labelNames("product", "billingoption", "type")
                .register();

        //init the gauges for the products
        for (String product : VOICE_PRODUCTS) {
            product_decrements_shillings.labels(product, "airtel_money", "data").set(0);
            product_decrements_shillings.labels(product, "airtime", "data").set(0);
        }

//        method_calls_response_seconds = Summary
//                .build()
//                .name("external_method_calls_latency_seconds")
//                .help("amount of time it takes to send and read response from calls made to externa systems.")
//                .labelNames("method")
//                .register();
    }

    public String sayHello() {

        //Timer startTimer = method_calls_response_seconds.labels("helloMethod").startTimer();
        processed_transactions_total.labels("data").inc();

        //randomly choose a procduct
        Random random = new Random();
        int billing_option_choice = Math.abs(random.nextInt(2));
        String product = VOICE_PRODUCTS[Math.abs(random.nextInt(VOICE_PRODUCTS.length - 1))];

        product_decrements_shillings.labels(product, BILLING_OPTIONS[billing_option_choice], "data").inc(Math.abs(random.nextInt(100)));
        //product_decrements_shillings.labels(product, BILLING_OPTIONS[billing_option_choice], "data").inc(1000);

//        try {
//            int sleep_time = Math.abs(random.nextInt(1000));
//            System.out.println("Sleeping for " + sleep_time + " millis.");
//            Thread.sleep(sleep_time);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
//        }

        //startTimer.observeDuration();
        return "Hello there!";
    }

    public String resetAllGauges() {
        //processed_transactions_total.labels("data").set(0);

        for (String product : VOICE_PRODUCTS) {
            product_decrements_shillings.labels(product, "airtel_money", "data").set(0);
            product_decrements_shillings.labels(product, "airtime", "data").set(0);
        }

        return "Resetting gauges complete!";
    }
}
