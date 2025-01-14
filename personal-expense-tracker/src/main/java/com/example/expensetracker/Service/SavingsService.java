package com.example.expensetracker.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expensetracker.Entity.Savings;
import com.example.expensetracker.Repo.SavingsRepository;

@Service
public class SavingsService {

    @Autowired
    private SavingsRepository savingsRepository;

    public Savings getSavings() {
        return savingsRepository.findAll().stream().findFirst().orElse(null);
    }

    public Savings updateSavings(Savings savings) {
        return savingsRepository.save(savings);
    }
}
