package com.example.final_project_app.helpers;

import java.util.ArrayList;

public class Business {
    private String business_name;
    private String business_logoLink;
    private String business_address;
    private String business_city;
    private String subject;
    private String business_menage_account_link;
    private boolean is_open;
    private ArrayList<Service> business_services;
    private WorkWeek work_schedule;

    public Business(String business_name, String business_logoLink, String business_address,
                    String business_city, String business_menage_account_link,
                    ArrayList<Service> business_services, String subject, boolean is_open,
                    WorkWeek work_schedule) {
        this.business_name = business_name;
        this.business_logoLink = business_logoLink;
        this.business_address = business_address;
        this.business_city = business_city;
        this.business_menage_account_link = business_menage_account_link;
        this.business_services = business_services;
        this.subject = subject;
        this.is_open = is_open;
        this.work_schedule = work_schedule;
    }

    public Business() {
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getBusiness_logoLink() {
        return business_logoLink;
    }

    public void setBusiness_logoLink(String business_logoLink) {
        this.business_logoLink = business_logoLink;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }
    public String getBusiness_city() {
        return business_city;
    }

    public void setBusiness_city(String business_city) {
        this.business_city = business_city;
    }

    public String getBusiness_menage_account_link() {
        return business_menage_account_link;
    }

    public void setBusiness_menage_account_link(String business_menage_account_link) {
        this.business_menage_account_link = business_menage_account_link;
    }

    public ArrayList<Service> getBusiness_services() {
        return business_services;
    }

    public void setBusiness_services(ArrayList<Service> business_services) {
        this.business_services = business_services;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public WorkWeek getWork_schedule() {
        return work_schedule;
    }

    public void setWork_schedule(WorkWeek work_schedule) {
        this.work_schedule = work_schedule;
    }
}
