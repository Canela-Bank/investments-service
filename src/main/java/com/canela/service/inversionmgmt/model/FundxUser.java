package com.canela.service.inversionmgmt.model;

public class FundxUser {
    private int id;
    private String associated_account;
    private double value;
    private char risk;
    public char getRisk() {
        return risk;
    }

    public void setRisk(char risk) {
        this.risk = risk;
    }



    public FundxUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssociated_account() {
        return associated_account;
    }

    public void setAssociated_account(String associated_account) {
        this.associated_account = associated_account;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
