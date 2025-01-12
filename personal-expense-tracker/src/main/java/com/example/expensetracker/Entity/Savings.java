package com.example.expensetracker.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Savings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double targetAmount;
	private double savedAmount;

	// Default Constructor
	public Savings() {}

	// Parameterized Constructor
	public Savings(Long id, double targetAmount, double savedAmount) {
		this.id = id;
		this.targetAmount = targetAmount;
		this.savedAmount = savedAmount;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(double targetAmount) {
		this.targetAmount = targetAmount;
	}

	public double getSavedAmount() {
		return savedAmount;
	}

	public void setSavedAmount(double savedAmount) {
		this.savedAmount = savedAmount;
	}


	@Override
	public String toString() {
		return "Savings{" +
				"id=" + id +
				", targetAmount=" + targetAmount +
				", savedAmount=" + savedAmount +
				'}';
	}
}
