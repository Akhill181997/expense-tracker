package com.example.expensetracker.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Income {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String source;
	private double amount;
	private LocalDate date;

	// Default Constructor
	public Income() {}

	// Parameterized Constructor
	public Income(long id, String source, double amount, LocalDate date) {
		this.id = id;
		this.source = source;
		this.amount = amount;
		this.date = date;
	}

	// Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	// toString Method
	@Override
	public String toString() {
		return "Income{" +
				"id=" + id +
				", source='" + source + '\'' +
				", amount=" + amount +
				", date=" + date +
				'}';
	}
}
