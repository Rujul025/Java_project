package com.example.travelplanner.model;

public class Place {
    private String name, city, state, zone, type, bestTimeToVisit, significance, weeklyOff;
    private double googleRating, numReviews;
    private int entranceFee, establishmentYear;
    private boolean airportNearby, dslrAllowed;

    public Place(String name, String city, String state, String zone, String type,
                 int establishmentYear, String bestTimeToVisit, String significance,
                 double googleRating, int entranceFee, boolean airportNearby,
                 String weeklyOff, boolean dslrAllowed, double numReviews) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.zone = zone;
        this.type = type;
        this.establishmentYear = establishmentYear;
        this.bestTimeToVisit = bestTimeToVisit;
        this.significance = significance;
        this.googleRating = googleRating;
        this.entranceFee = entranceFee;
        this.airportNearby = airportNearby;
        this.weeklyOff = weeklyOff;
        this.dslrAllowed = dslrAllowed;
        this.numReviews = numReviews;
    }

    // Getters for display
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZone() { return zone; }
    public String getType() { return type; }
    public int getEstablishmentYear() { return establishmentYear; }
    public String getBestTimeToVisit() { return bestTimeToVisit; }
    public String getSignificance() { return significance; }
    public double getGoogleRating() { return googleRating; }
    public int getEntranceFee() { return entranceFee; }
    public boolean isAirportNearby() { return airportNearby; }
    public String getWeeklyOff() { return weeklyOff; }
    public boolean isDslrAllowed() { return dslrAllowed; }
    public double getNumReviews() { return numReviews; }
}
