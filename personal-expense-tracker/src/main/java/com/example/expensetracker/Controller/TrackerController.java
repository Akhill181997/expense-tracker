package com.example.expensetracker.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Entity.Income;
import com.example.expensetracker.Entity.Savings;
import com.example.expensetracker.Service.ExpenseService;
import com.example.expensetracker.Service.IncomeService;
import com.example.expensetracker.Service.SavingsService;

import java.util.List;

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
    public List<Income> getAllIncome() {
        return incomeService.getAllIncome();
    }

    @PostMapping("/income")
    @PreAuthorize("hasRole('ADMIN')")
    public Income addIncome(@RequestBody Income income) {
        return incomeService.addIncome(income);
    }

    @GetMapping("/expenses")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PostMapping("/expenses")
    @PreAuthorize("hasRole('ADMIN')")
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    @GetMapping("/savings")
    @PreAuthorize("hasRole('GUEST')")
    public Savings getSavings() {
        return savingsService.getSavings();
    }

    @PutMapping("/savings")
    @PreAuthorize("hasRole('ADMIN')")
    public Savings updateSavings(@RequestBody Savings savings) {
        return savingsService.updateSavings(savings);
    }

    @GetMapping("/remaining-balance")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public Double getRemainingBalance() {
        double totalIncome = incomeService.getTotalIncome();
        double totalExpenses = expenseService.getTotalExpenses();
        return totalIncome - totalExpenses;
    }
}
