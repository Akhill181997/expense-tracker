package com.example.expensetracker.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String category;
	private double amount;
	private LocalDate date;
	private Boolean isRecurring;
	private String frequency;
	//private Double monthlyBudget;


	public Expense() {}


	public Expense(Long id, String category, double amount, LocalDate date) {
		this.id = id;
		this.category = category;
		this.amount = amount;
		this.date = date;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Boolean getIsRecurring() {
		return isRecurring;
	}

	public void setIsRecurring(Boolean isRecurring) {
		this.isRecurring = isRecurring;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/*public Double getMonthlyBudget() {
		return monthlyBudget;
	}

	public void setMonthlyBudget(Double monthlyBudget) {
		this.monthlyBudget = monthlyBudget;
	}*/


	@Override
	public String toString() {
		return "Expense{" +
				"id=" + id +
				", category='" + category + '\'' +
				", amount=" + amount +
				", date=" + date +
				'}';
	}
}