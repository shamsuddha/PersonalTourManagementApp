package com.example.shamsuddha.tourmate;

public class TravelEvent {

    String id;
    String travelDestination;
    String estimatedBudget;
    String fromDate;
    String toDate;



    public TravelEvent(String travelDestination, String estimatedBudget, String fromDate, String toDate) {
        this.travelDestination = travelDestination;
        this.estimatedBudget = estimatedBudget;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public TravelEvent(String id, String travelDestination, String estimatedBudget, String fromDate, String toDate) {
        this.id = id;
        this.travelDestination = travelDestination;
        this.estimatedBudget = estimatedBudget;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public TravelEvent() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTravelDestination() {
        return travelDestination;
    }

    public void setTravelDestination(String travelDestination) {
        this.travelDestination = travelDestination;
    }

    public String getEstimatedBudget() {
        return estimatedBudget;
    }

    public void setEstimatedBudget(String estimatedBudget) {
        this.estimatedBudget = estimatedBudget;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
