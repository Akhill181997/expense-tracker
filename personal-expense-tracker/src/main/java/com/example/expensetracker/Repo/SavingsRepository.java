package com.example.expensetracker.Repo;

import com.example.expensetracker.Entity.Savings;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SavingsRepository extends ReactiveCrudRepository<Savings, Long> {
}
