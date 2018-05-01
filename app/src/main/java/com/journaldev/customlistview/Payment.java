package com.journaldev.customlistview;

public class Payment {

    private String title;
    private int collection_amount;
    private int agent_percentage;
    private int agent_amount;
    private String paymentType;
    private String currency;
    private String date;
    private String time;
    private String id;
    private String location;
    private String status;

    public Payment() { }

    public Payment(String title, int collection_amount, int agent_percentage, int agent_amount, String paymentType, String currency, String date, String time, String id, String location, String status) {
        this.title = title;
        this.collection_amount = collection_amount;
        this.agent_percentage = agent_percentage;
        this.agent_amount = agent_amount;
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

    public int getCollection_amount() {
        return collection_amount;
    }

    public void setCollection_amount(int collection_amount) {
        this.collection_amount = collection_amount;
    }

    public int getAgent_percentage() {
        return agent_percentage;
    }

    public void setAgent_percentage(int agent_percentage) {
        this.agent_percentage = agent_percentage;
    }

    public int getAgent_amount() {
        return agent_amount;
    }

    public void setAgent_amount(int agent_amount) {
        this.agent_amount = agent_amount;
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