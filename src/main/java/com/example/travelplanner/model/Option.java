package com.example.travelplanner.model;

public class Option {
    private final int id;
    private final String name;
    private final String type; // This is either transport type or hotel location
    private final double cost;

    public Option(int id, String name, String type, double cost) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }
}
