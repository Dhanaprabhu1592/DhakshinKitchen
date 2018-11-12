package com.whistledevelopers.dhakshinkitchen;

import java.io.Serializable;

public class ItemsModel implements Serializable{


    String name;
    String count;
    boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
