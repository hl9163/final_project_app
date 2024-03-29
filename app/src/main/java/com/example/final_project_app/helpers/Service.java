package com.example.final_project_app.helpers;

public class Service {
    /**
     * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
     * @version	1.0
     * @since		26/12/2022
     * Service object
     */
    private String service_name;
    private String service_cost;
    private String service_time;

    public Service() {
    }

    public Service(String name, String cost, String time) {
        this.service_name = name;
        this.service_cost = cost;
        this.service_time = time;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_cost() {
        return service_cost;
    }

    public void setService_cost(String service_cost) {
        this.service_cost = service_cost;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }
}
