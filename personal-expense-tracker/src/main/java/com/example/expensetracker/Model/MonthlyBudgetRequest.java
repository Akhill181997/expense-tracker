package com.example.expensetracker.Model;

public class MonthlyBudgetRequest {
    private String category;  // Category of the expense
    private Double budget;    // Monthly budget for the category

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}
