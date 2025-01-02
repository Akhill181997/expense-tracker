package com.example.expensetracker.Repo;

import com.example.expensetracker.Entity.Income;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface IncomeRepository extends ReactiveCrudRepository<Income, Long> {
    Flux<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
