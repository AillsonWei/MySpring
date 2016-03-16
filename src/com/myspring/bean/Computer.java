package com.myspring.bean;

import java.io.Serializable;

/**
 * Created by AillsonWei on 2016/3/16.
 */
public class Computer implements Serializable{
    private String name;
    private String mainboard;
    private String cpu;
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainboard() {
        return mainboard;
    }

    public void setMainboard(String mainboard) {
        this.mainboard = mainboard;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "name='" + name + '\'' +
                ", mainboard='" + mainboard + '\'' +
                ", cpu='" + cpu + '\'' +
                ", price=" + price +
                '}';
    }
}
