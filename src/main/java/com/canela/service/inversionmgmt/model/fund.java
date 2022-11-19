package com.canela.service.inversionmgmt.model;

public class fund {
    private int id;
    private char risk;
    private double minimum_investment;
    private String availability;

    public fund() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getRisk() {
        return risk;
    }

    public void setRisk(char risk) {
        this.risk = risk;
    }

    public double getMinimum_investment() {
        return minimum_investment;
    }

    public void setMinimum_investment(double minimum_investment) {
        this.minimum_investment = minimum_investment;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
