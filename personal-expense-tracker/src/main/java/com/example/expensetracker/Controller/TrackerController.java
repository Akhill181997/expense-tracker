package com.example.expensetracker.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Entity.Income;
import com.example.expensetracker.Entity.Savings;
import com.example.expensetracker.Service.ExpenseService;
import com.example.expensetracker.Service.IncomeService;
import com.example.expensetracker.Service.SavingsService;

@RestController
@RequestMapping("/api")
public class TrackerController {
    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final SavingsService savingsService;

    public TrackerController(IncomeService incomeService, ExpenseService expenseService, SavingsService savingsService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
        this.savingsService = savingsService;
    }


    @GetMapping("/income")
    @PreAuthorize("hasRole('ADMIN')")
    public Flux<Income> getAllIncome() {
        return incomeService.getAllIncome();
    }


    @PostMapping("/income")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Income> addIncome(@RequestBody Income income) {
        return incomeService.addIncome(income);
    }


    @GetMapping("/expenses")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public Flux<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }


    @PostMapping("/expenses")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Expense> addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }


    @GetMapping("/savings")
    @PreAuthorize("hasRole('GUEST')")
    public Mono<Savings> getSavings() {
        return savingsService.getSavings();
    }


    @PutMapping("/savings")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Savings> updateSavings(@RequestBody Savings savings) {
        return savingsService.updateSavings(savings);
    }


    @GetMapping("/remaining-balance")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public Mono<Double> getRemainingBalance() {
        Mono<Double> totalIncome = incomeService.getTotalIncome();
        Mono<Double> totalExpenses = expenseService.getTotalExpenses();

        return Mono.zip(totalIncome, totalExpenses, (income, expenses) -> income - expenses);
    }
}
