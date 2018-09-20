package com.example.shamsuddha.tourmate;

public class TravelExpense {

    private String id;
    private String expenseDetails;
    private String expenseAmount;

    public TravelExpense(String id, String expenseDetails, String expenseAmount) {
        this.id = id;
        this.expenseDetails = expenseDetails;
        this.expenseAmount = expenseAmount;
    }

    public TravelExpense(String expenseDetails, String expenseAmount) {
        this.expenseDetails = expenseDetails;
        this.expenseAmount = expenseAmount;
    }

    public TravelExpense() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(String expenseDetails) {
        this.expenseDetails = expenseDetails;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
}
