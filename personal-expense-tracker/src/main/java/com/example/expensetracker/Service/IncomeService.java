package com.example.expensetracker.Service;

import com.example.expensetracker.Repo.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.example.expensetracker.Entity.Income;


import java.time.LocalDate;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }


    public Flux<Income> getAllIncome() {
        return incomeRepository.findAll();
    }


    public Mono<Income> addIncome(Income income) {
        return incomeRepository.save(income);
    }


    public Flux<Income> getIncomeByDateRange(LocalDate start, LocalDate end) {
        return incomeRepository.findByDateBetween(start, end);
    }

    // Calculate total income
    public Mono<Double> getTotalIncome() {
        return incomeRepository.findAll()
                .map( income -> income.getAmount() )
                .reduce(0.0, Double::sum);
    }
}
