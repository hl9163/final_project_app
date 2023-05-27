package com.example.final_project_app.helpers;

public class Queue {
    private String userId;
    private String businessId;
    private String date;
    private String time;
    private Service service;

    public Queue() {
    }

    public Queue(String userId, String businessId, String date, String time, Service service) {
        this.userId = userId;
        this.businessId = businessId;
        this.date = date;
        this.time = time;
        this.service = service;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
