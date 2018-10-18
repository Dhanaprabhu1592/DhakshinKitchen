package com.whistledevelopers.dhakshinkitchen;

public class ItemsModel {


    String name;
    String count;

    public ItemsModel() {
    }

    public ItemsModel(String name, String count) {
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
