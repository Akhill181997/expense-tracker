package com.example.expensetracker.Controller;

import com.example.expensetracker.Model.MonthlyBudgetRequest;

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


    @GetMapping("/income/recurring")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Income> getRecurringIncome() {
        return incomeService.getRecurringIncome();  }

    @GetMapping("/expenses/recurring")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public List<Expense> getRecurringExpenses() {
        return expenseService.getRecurringExpenses();
    }

    @GetMapping("/remaining-balance")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public Double getRemainingBalance() {
        double totalIncome = incomeService.getTotalIncome();
        double totalExpenses = expenseService.getTotalExpenses();
        return totalIncome - totalExpenses;
    }


    @PostMapping(value = "/monthly-budget")
    @PreAuthorize("hasRole('ADMIN')")
    public String setMonthlyBudget(@RequestBody MonthlyBudgetRequest monthlyBudgetRequest) {
        boolean isUpdated = expenseService.setMonthlyBudget(monthlyBudgetRequest.getCategory(), monthlyBudgetRequest.getBudget());
        if (isUpdated) {
            return "Monthly budget set successfully.";
        } else {
            return "Failed to set monthly budget.";
        }
    }




    @GetMapping("/monthly-budget/{category}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Double getMonthlyBudget(@PathVariable String category) {
        return expenseService.getMonthlyBudget(category);
    }

}
