package com.whistledevelopers.dhakshinkitchen;

import java.io.Serializable;

public class ConfirmItemModel implements Serializable {
    String name;
    String count;
    String qty;

    public ConfirmItemModel(String name, String count,String qty) {
        this.name = name;
        this.count = count;
        this.qty=qty;
    }



    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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
