package com.example.expensetracker.Controller;

import com.example.expensetracker.Model.MonthlyBudgetRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Entity.Income;
import com.example.expensetracker.Entity.Savings;
import com.example.expensetracker.Service.ExpenseService;
import com.example.expensetracker.Service.IncomeService;
import com.example.expensetracker.Service.SavingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TrackerController {

    private static final Logger logger = LoggerFactory.getLogger(TrackerController.class);

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
        logger.info("Fetching all incomes.");
        List<Income> incomes = incomeService.getAllIncome();
        logger.info("Successfully fetched {} incomes.", incomes.size());
        return incomes;
    }

    @PostMapping("/income")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {
        logger.info("Adding new income: {}", income);

        Income addedIncome = incomeService.addIncome(income);
        logger.info("Successfully added income with ID: {}", addedIncome.getId());

        return ResponseEntity.ok(addedIncome);
    }

    @GetMapping("/expenses")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public List<Expense> getAllExpenses() {
       // logger.info("Fetching all expenses.");
        List<Expense> expenses = expenseService.getAllExpenses();
        logger.info("Successfully fetched {} expenses.", expenses.size());
        return expenses;
    }

    @PostMapping("/expenses")
    @PreAuthorize("hasRole('ADMIN')")
    public Expense addExpense(@RequestBody Expense expense) {
        logger.info("Adding new expense: {}", expense);
        Expense addedExpense = expenseService.addExpense(expense);
        logger.info("Successfully added expense with ID: {}", addedExpense.getId());
        return addedExpense;
    }

    @GetMapping("/savings")
    @PreAuthorize("hasRole('GUEST')")
    public Savings getSavings() {
        logger.info("Fetching savings.");
        Savings savings = savingsService.getSavings();
        logger.info("Successfully fetched savings: {}", savings);
        return savings;
    }

    @PutMapping("/savings")
    @PreAuthorize("hasRole('ADMIN')")
    public Savings updateSavings(@RequestBody Savings savings) {
        logger.info("Updating savings: {}", savings);
        Savings updatedSavings = savingsService.updateSavings(savings);
        logger.info("Successfully updated savings to: {}", updatedSavings);
        return updatedSavings;
    }

    @GetMapping("/income/recurring")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Income> getRecurringIncome() {
        logger.info("Fetching recurring incomes.");
        List<Income> recurringIncomes = incomeService.getRecurringIncome();
        logger.info("Successfully fetched all recurring incomes.");
        return recurringIncomes;
    }

    @GetMapping("/expenses/recurring")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public List<Expense> getRecurringExpenses() {
        logger.info("Fetching recurring expenses.");
        List<Expense> recurringExpenses = expenseService.getRecurringExpenses();
       // logger.info("Successfully fetched {} recurring expenses.", recurringExpenses.size());
        logger.info("Successfully fetched all recurring expenses.");
        return recurringExpenses;
    }

    @GetMapping("/remaining-balance")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public Double getRemainingBalance() {
        logger.info("Calculating remaining balance.");
        double totalIncome = incomeService.getTotalIncome();
        double totalExpenses = expenseService.getTotalExpenses();
        double remainingBalance = totalIncome - totalExpenses;
        logger.info("Remaining balance calculated: {}", remainingBalance);
        return remainingBalance;
    }

    @PostMapping(value = "/monthly-budget")
    @PreAuthorize("hasRole('ADMIN')")
    public String setMonthlyBudget(@RequestBody MonthlyBudgetRequest monthlyBudgetRequest) {
        logger.info("Setting monthly budget for category: {} with budget: {}",
                monthlyBudgetRequest.getCategory(), monthlyBudgetRequest.getBudget());
        try {
            boolean isUpdated = expenseService.setMonthlyBudget(
                    monthlyBudgetRequest.getCategory(), monthlyBudgetRequest.getBudget());
            if (isUpdated) {
                logger.info("Monthly budget set successfully for category: {}.", monthlyBudgetRequest.getCategory());
                return "Monthly budget set successfully.";
            } else {
                logger.warn("Failed to set monthly budget for category: {}.", monthlyBudgetRequest.getCategory());
                return "Failed to set monthly budget.";
            }
        } catch (Exception e) {
            logger.error("Error while setting monthly budget for category: {}", monthlyBudgetRequest.getCategory(), e);
            throw e;
        }
    }

    @GetMapping("/monthly-budget/{category}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Double getMonthlyBudget(@PathVariable String category) {
        logger.info("Fetching monthly budget for category: {}", category);
        Double budget = expenseService.getMonthlyBudget(category);
        logger.info("Fetched monthly budget for category {}: {}", category, budget);
        return budget;
    }
}
