package com.whistledevelopers.dhakshinkitchen.model;

import java.io.Serializable;

public class OngoingModel implements Serializable {


    String name;
    String status;
    String orderId;


    public OngoingModel(String name, String status, String orderId) {
        this.name = name;
        this.status = status;
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderId() {
        return orderId;
    }
}

