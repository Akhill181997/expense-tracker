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

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense addExpense(Expense expense) {
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

            // Add the new expense amount to the total and check against the budget
            if (totalCategoryExpense + expense.getAmount() > categoryBudget) {
                throw new IllegalArgumentException("Monthly budget for category '" + category + "' exceeded.");
            }
        }

        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByDateRange(LocalDate start, LocalDate end) {
        return expenseRepository.findByDateBetween(start, end);
    }

    private final Map<String, Double> monthlyBudgets = new HashMap<>();

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
        if (category == null || budget == null || budget <= 0) {
            return false;  // Invalid input
        }
        monthlyBudgets.put(category, budget);
        return true;
    }


    public Double getMonthlyBudget(String category) {
        return monthlyBudgets.get(category);
    }
}
