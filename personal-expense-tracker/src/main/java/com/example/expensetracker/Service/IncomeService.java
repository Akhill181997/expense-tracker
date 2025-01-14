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
}