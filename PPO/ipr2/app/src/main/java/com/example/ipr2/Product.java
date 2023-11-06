package com.example.ipr2;

public class Product {
    private String name;
    private String type;
    private int cost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public Product(String name, String type, int cost) {
        this.name = name;
        this.type = type;
        this.cost = cost;
    }
}
