package com.example.expensetracker.Service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Repo.ExpenseRepository;

import java.time.LocalDate;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }


    public Flux<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }


    public Mono<Expense> addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }


    public Flux<Expense> getExpensesByDateRange(LocalDate start, LocalDate end) {
        return expenseRepository.findByDateBetween(start, end);
    }


    public Mono<Double> getTotalExpenses() {
        return expenseRepository.findAll()
                .map(Expense::getAmount)
                .reduce(0.0, Double::sum);
    }

}
