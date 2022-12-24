package com.example.final_project_app.helpers;

public class Client {

    String client_name;
    String client_email;
    String client_password;
    String client_phone;
    String client_fav_city;

    public Client(String name, String email, String password, String phone, String fav_city) {
         client_name = name;
         client_email= email;
         client_password = password;
         client_phone = phone;
         client_fav_city = fav_city;
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

    public String getClient_fav_city() {
        return client_fav_city;
    }

    public void setClient_fav_city(String client_fav_city) {
        this.client_fav_city = client_fav_city;
    }
}
