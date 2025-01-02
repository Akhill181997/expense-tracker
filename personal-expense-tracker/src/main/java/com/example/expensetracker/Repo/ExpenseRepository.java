package com.example.expensetracker.Repo;

import com.example.expensetracker.Entity.Expense;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface ExpenseRepository extends ReactiveCrudRepository<Expense, Long> {
    // Reactive method to find expenses by date range
    Flux<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
