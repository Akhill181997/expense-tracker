package com.example.expensetracker.Service;

import com.example.expensetracker.Entity.Income;
import com.example.expensetracker.Repo.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public List<Income> getAllIncome() {
        return incomeRepository.findAll();
    }


    public Income addIncome(Income income) {

        validateIncome(income);
        return incomeRepository.save(income);
    }


    public List<Income> getIncomeByDateRange(LocalDate start, LocalDate end) {
        return incomeRepository.findByDateBetween(start, end);
    }


    public double getTotalIncome() {
        return incomeRepository.findAll()
                .stream()
                .mapToDouble(Income::getAmount)
                .sum();
    }


    public List<Income> getRecurringIncome() {
        return incomeRepository.findByIsRecurringTrue();
    }


    private void validateIncome(Income income) {

        if (income.getSource() == null || income.getSource().isEmpty()) {
            throw new IllegalArgumentException("Source cannot be null or empty.");
        }
        if (income.getSource().length() > 100) {
            throw new IllegalArgumentException("Source cannot exceed 100 characters.");
        }


        if (income.getAmount() <= 0) {
            throw new IllegalArgumentException("Income amount must be greater than zero.");
        }


        if (income.getDate() == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        if (income.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the future.");
        }


        if (income.getIsRecurring() != null && income.getIsRecurring()) {

            if (income.getFrequency() == null || income.getFrequency().isEmpty()) {
                throw new IllegalArgumentException("Recurring income must have a frequency.");
            }
            if (income.getFrequency().length() > 50) {
                throw new IllegalArgumentException("Frequency cannot exceed 50 characters.");
            }
        }
    }
}
