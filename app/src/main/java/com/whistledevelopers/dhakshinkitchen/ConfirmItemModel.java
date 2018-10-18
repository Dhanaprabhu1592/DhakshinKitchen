package com.whistledevelopers.dhakshinkitchen;

import java.io.Serializable;

public class ConfirmItemModel implements Serializable {
    String name;
    String count;

    public ConfirmItemModel(String name, String count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
