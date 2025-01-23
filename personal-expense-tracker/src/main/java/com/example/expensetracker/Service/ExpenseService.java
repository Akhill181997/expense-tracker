package com.example.expensetracker.Service;

import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Repo.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    private final Map<String, Double> monthlyBudgets = new HashMap<>();

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense addExpense(Expense expense) {
        
        validateExpense(expense);

        String category = expense.getCategory();
        Double categoryBudget = monthlyBudgets.get(category);

        if (categoryBudget != null) {

            LocalDate now = LocalDate.now();
            LocalDate startOfMonth = now.withDayOfMonth(1);
            LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

            double totalCategoryExpense = expenseRepository.findByCategoryAndDateBetween(category, startOfMonth, endOfMonth)
                    .stream()
                    .mapToDouble(Expense::getAmount)
                    .sum();


            if (totalCategoryExpense + expense.getAmount() > categoryBudget) {
                throw new IllegalArgumentException("Monthly budget for category '" + category + "' exceeded.");
            }
        }

        return expenseRepository.save(expense);
    }


    public List<Expense> getExpensesByDateRange(LocalDate start, LocalDate end) {
        return expenseRepository.findByDateBetween(start, end);
    }


    public double getTotalExpenses() {
        return expenseRepository.findAll()
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }


    public List<Expense> getRecurringExpenses() {
        return expenseRepository.findByIsRecurringTrue();
    }


    public boolean setMonthlyBudget(String category, Double budget) {
        if (category == null || category.isEmpty() || budget == null || budget <= 0) {
            return false;  // Invalid input
        }
        monthlyBudgets.put(category, budget);
        return true;
    }


    public Double getMonthlyBudget(String category) {
        return monthlyBudgets.get(category);
    }


    private void validateExpense(Expense expense) {

        if (expense.getCategory() == null || expense.getCategory().isEmpty()) {
            throw new IllegalArgumentException("Expense category cannot be null or empty.");
        }


        if (expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Expense amount must be greater than zero.");
        }


        if (expense.getDate() == null || expense.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Expense date cannot be in the future.");
        }


        if (expense.getIsRecurring() != null && expense.getIsRecurring() && (expense.getFrequency() == null || expense.getFrequency().isEmpty())) {
            throw new IllegalArgumentException("Recurring expense must have a frequency.");
        }
    }
}
