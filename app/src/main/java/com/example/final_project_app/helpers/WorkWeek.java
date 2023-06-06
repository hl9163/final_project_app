package com.example.final_project_app.helpers;

import java.util.ArrayList;

public class WorkWeek{
    /**
     * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
     * @version	1.0
     * @since		18/03/2023
     * WorkWeek object
     */
    private ArrayList<Boolean> days_of_work;
    private ArrayList<String> regular_day;
    private ArrayList<String> sunday_queues;
    private ArrayList<String> monday_queues;
    private ArrayList<String> tuesday_queues;
    private ArrayList<String> wednesday_queues;
    private ArrayList<String> thursday_queues;
    private ArrayList<String> friday_queues;
    private ArrayList<String> saturday_queues;
    private int frequency_index;

    public WorkWeek(ArrayList<Boolean> days_of_work, ArrayList<String> regular_day, int frequency_index) {
        this.days_of_work = days_of_work;
        this.regular_day = regular_day;
        this.sunday_queues = regular_day;
        this.monday_queues = regular_day;
        this.tuesday_queues = regular_day;
        this.wednesday_queues = regular_day;
        this.thursday_queues = regular_day;
        this.friday_queues = regular_day;
        this.saturday_queues = regular_day;
        this.frequency_index = frequency_index;
    }

    public WorkWeek() {
    }

    public ArrayList<Boolean> getDays_of_work() {
        return days_of_work;
    }

    public void setDays_of_work(ArrayList<Boolean> days_of_work) {
        this.days_of_work = days_of_work;
    }

    public ArrayList<String> getRegular_day() {
        return regular_day;
    }

    public void setRegular_day(ArrayList<String> regular_day) {
        this.regular_day = regular_day;
    }

    public ArrayList<String> getSunday_queues() {
        return sunday_queues;
    }

    public void setSunday_queues(ArrayList<String> sunday_queues) {
        this.sunday_queues = sunday_queues;
    }

    public ArrayList<String> getMonday_queues() {
        return monday_queues;
    }

    public void setMonday_queues(ArrayList<String> monday_queues) {
        this.monday_queues = monday_queues;
    }

    public ArrayList<String> getTuesday_queues() {
        return tuesday_queues;
    }

    public void setTuesday_queues(ArrayList<String> tuesday_queues) {
        this.tuesday_queues = tuesday_queues;
    }

    public ArrayList<String> getWednesday_queues() {
        return wednesday_queues;
    }

    public void setWednesday_queues(ArrayList<String> wednesday_queues) {
        this.wednesday_queues = wednesday_queues;
    }

    public ArrayList<String> getThursday_queues() {
        return thursday_queues;
    }

    public void setThursday_queues(ArrayList<String> thursday_queues) {
        this.thursday_queues = thursday_queues;
    }

    public ArrayList<String> getFriday_queues() {
        return friday_queues;
    }

    public void setFriday_queues(ArrayList<String> friday_queues) {
        this.friday_queues = friday_queues;
    }

    public ArrayList<String> getSaturday_queues() {
        return saturday_queues;
    }

    public void setSaturday_queues(ArrayList<String> saturday_queues) {
        this.saturday_queues = saturday_queues;
    }

    public int getFrequency_index() {
        return frequency_index;
    }

    public void setFrequency_index(int frequency_index) {
        this.frequency_index = frequency_index;
    }
}
