package com.example.expensetracker.Service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.example.expensetracker.Entity.Savings;
import com.example.expensetracker.Repo.SavingsRepository;

@Service
public class SavingsService {
    private final SavingsRepository savingsRepository;

    public SavingsService(SavingsRepository savingsRepository) {
        this.savingsRepository = savingsRepository;
    }


    public Mono<Savings> getSavings() {
        return savingsRepository.findAll().next();
    }


    public Mono<Savings> updateSavings(Savings savings) {
        return savingsRepository.save(savings);
    }
}
