package com.journaldev.customlistview;

public class Payment {

    private String title;
    private String collectionAmount;
    private String agentPercentage;
    private int agentAmount;
    private String paymentType;
    private String currency;
    private String date;
    private String time;
    private String id;
    private String location;
    private String status;

    public Payment() { }

    public Payment(String title, String collectionAmount, String agentPercentage, int agentAmount, String paymentType, String currency, String date, String time, String id, String location, String status) {
        this.title = title;
        this.collectionAmount = collectionAmount;
        this.agentPercentage = agentPercentage;
        this.agentAmount = agentAmount;
        this.paymentType = paymentType;
        this.currency = currency;
        this.date = date;
        this.time = time;
        this.id = id;
        this.location = location;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCollectionAmount() {
        return collectionAmount;
    }

    public void setCollectionAmount(String collectionAmount) {
        this.collectionAmount = collectionAmount;
    }

    public String getAgentPercentage() {
        return agentPercentage;
    }

    public void setAgentPercentage(String agentPercentage) {
        this.agentPercentage = agentPercentage;
    }

    public int getAgentAmount() {
        return agentAmount;
    }

    public void setAgentAmount(int agentAmount) {
        this.agentAmount = agentAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}