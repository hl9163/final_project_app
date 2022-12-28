package com.example.final_project_app.helpers;

public class Client {

    private String client_name;
    private String client_email;
    private String client_password;
    private String client_phone;
    private String client_business;

    public Client(String client_name, String client_email, String client_password, String client_phone, String client_business) {
        this.client_name = client_name;
        this.client_email = client_email;
        this.client_password = client_password;
        this.client_phone = client_phone;
        this.client_business = client_business;
    }

    public Client() {
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getClient_password() {
        return client_password;
    }

    public void setClient_password(String client_password) {
        this.client_password = client_password;
    }

    public String getClient_phone() {
        return client_phone;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }

    public String getClient_business() {
        return client_business;
    }

    public void setClient_business(String client_business) {
        this.client_business = client_business;
    }
}
