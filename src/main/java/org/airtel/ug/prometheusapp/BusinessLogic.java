package org.airtel.ug.prometheusapp;

import io.prometheus.client.Gauge;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BusinessLogic {

    private Gauge total_transactions_processed;
    private Gauge total_product_decrements;

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

        total_transactions_processed = Gauge
                .build()
                .name("total_transactions_processed")
                .help("Total transactions processed")
                .labelNames("type")
                .register();

        total_product_decrements = Gauge
                .build()
                .name("total_decrements")
                .help("total decrements made per product")
                .labelNames("product", "billingoption", "type")
                .register();

        //init the gauges for the products
        for (String product : VOICE_PRODUCTS) {
            total_product_decrements.labels(product, "airtel_money", "voice").set(0);
            total_product_decrements.labels(product, "airtime", "voice").set(0);
        }

    }

    public String sayHello() {
        total_transactions_processed.labels("voice").inc();

        //randomly choose a procduct
        Random random = new Random();
        int billing_option_choice = Math.abs(random.nextInt(2));
        String product = VOICE_PRODUCTS[Math.abs(random.nextInt(VOICE_PRODUCTS.length - 1))];

        total_product_decrements.labels(product, BILLING_OPTIONS[billing_option_choice], "voice").inc(Math.abs(random.nextInt(100)));

        return "Hello there!";
    }

    public String resetAllGauges() {
        total_transactions_processed.labels("voice").set(0);

        for (String product : VOICE_PRODUCTS) {
            total_product_decrements.labels(product, "airtel_money", "voice").set(0);
            total_product_decrements.labels(product, "airtime", "voice").set(0);
        }

        return "Resetting gauges complete!";
    }
}
