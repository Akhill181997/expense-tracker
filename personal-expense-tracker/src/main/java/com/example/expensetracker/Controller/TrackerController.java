package com.example.expensetracker.Controller;

import com.example.expensetracker.Model.MonthlyBudgetRequest;
import org.springframework.http.HttpStatus;
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

    // Get all incomes
    @GetMapping("/income")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Income>> getAllIncome() {
        logger.info("Fetching all incomes.");
        List<Income> incomes = incomeService.getAllIncome();
        logger.info("Successfully fetched {} incomes.", incomes.size());
        return ResponseEntity.ok(incomes);
    }

    // Add a new income
    @PostMapping("/income")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {
        logger.info("Adding new income: {}", income);
        Income addedIncome = incomeService.addIncome(income);
        logger.info("Successfully added income with ID: {}", addedIncome.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedIncome);
    }

    // Get all expenses
    @GetMapping("/expenses")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        logger.info("Fetching all expenses.");
        List<Expense> expenses = expenseService.getAllExpenses();
        logger.info("Successfully fetched {} expenses.", expenses.size());
        return ResponseEntity.ok(expenses);
    }

    // Add a new expense
    @PostMapping("/expenses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        logger.info("Adding new expense: {}", expense);
        Expense addedExpense = expenseService.addExpense(expense);
        logger.info("Successfully added expense with ID: {}", addedExpense.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedExpense);
    }

    // Get savings
    @GetMapping("/savings")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<Savings> getSavings() {
        logger.info("Fetching savings.");
        Savings savings = savingsService.getSavings();
        logger.info("Successfully fetched savings: {}", savings);
        return ResponseEntity.ok(savings);
    }

    // Update savings
    @PutMapping("/savings")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Savings> updateSavings(@RequestBody Savings savings) {
        logger.info("Updating savings: {}", savings);
        Savings updatedSavings = savingsService.updateSavings(savings);
        logger.info("Successfully updated savings to: {}", updatedSavings);
        return ResponseEntity.ok(updatedSavings);
    }

    // Get recurring incomes
    @GetMapping("/income/recurring")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Income>> getRecurringIncome() {
        logger.info("Fetching recurring incomes.");
        List<Income> recurringIncomes = incomeService.getRecurringIncome();
        logger.info("Successfully fetched all recurring incomes.");
        return ResponseEntity.ok(recurringIncomes);
    }

    // Get recurring expenses
    @GetMapping("/expenses/recurring")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public ResponseEntity<List<Expense>> getRecurringExpenses() {
        logger.info("Fetching recurring expenses.");
        List<Expense> recurringExpenses = expenseService.getRecurringExpenses();
        logger.info("Successfully fetched all recurring expenses.");
        return ResponseEntity.ok(recurringExpenses);
    }

    // Get remaining balance
    @GetMapping("/remaining-balance")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    public ResponseEntity<Double> getRemainingBalance() {
        logger.info("Calculating remaining balance.");
        double totalIncome = incomeService.getTotalIncome();
        double totalExpenses = expenseService.getTotalExpenses();
        double remainingBalance = totalIncome - totalExpenses;
        logger.info("Remaining balance calculated: {}", remainingBalance);
        return ResponseEntity.ok(remainingBalance);
    }

    // Set monthly budget
    @PostMapping("/monthly-budget")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> setMonthlyBudget(@RequestBody MonthlyBudgetRequest monthlyBudgetRequest) {
        logger.info("Setting monthly budget for category: {} with budget: {}",
                monthlyBudgetRequest.getCategory(), monthlyBudgetRequest.getBudget());
        try {
            boolean isUpdated = expenseService.setMonthlyBudget(
                    monthlyBudgetRequest.getCategory(), monthlyBudgetRequest.getBudget());
            if (isUpdated) {
                logger.info("Monthly budget set successfully for category: {}.", monthlyBudgetRequest.getCategory());
                return ResponseEntity.ok("Monthly budget set successfully.");
            } else {
                logger.info("Failed to set monthly budget for category: {}.", monthlyBudgetRequest.getCategory());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to set monthly budget.");
            }
        } catch (Exception e) {
            logger.error("Error while setting monthly budget for category: {}", monthlyBudgetRequest.getCategory(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while setting the budget.");
        }
    }

    // Get monthly budget for a category
    @GetMapping("/monthly-budget/{category}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Double> getMonthlyBudget(@PathVariable String category) {
        logger.info("Fetching monthly budget for category: {}", category);
        Double budget = expenseService.getMonthlyBudget(category);
        if (budget == null) {
            logger.info("No budget set for category: {}", category);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        logger.info("Fetched monthly budget for category {}: {}", category, budget);
        return ResponseEntity.ok(budget);
    }
}
