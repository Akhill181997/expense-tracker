package com.example.expensetracker.Service;

import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Entity.Income;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecurringService {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public RecurringService(IncomeService incomeService, ExpenseService expenseService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    // Scheduled task that runs once a day at midnight
    @Scheduled(cron = "0 0 0 * * ?") // Cron expression for daily task
    public void addDailyRecurringIncome() {
        List<Income> recurringIncomes = incomeService.getRecurringIncome();

        for (Income income : recurringIncomes) {
            if ("DAILY".equalsIgnoreCase(income.getFrequency())) {
                Income newIncome = new Income();
                newIncome.setAmount(income.getAmount());
                newIncome.setSource(income.getSource());
                newIncome.setDate(LocalDate.now().plusDays(1)); // Add one day to the date
                newIncome.setIsRecurring(true);
                newIncome.setFrequency("DAILY");

                incomeService.addIncome(newIncome);
            }
        }
    }

    // Scheduled task that runs once a week (every Monday at midnight)
    @Scheduled(cron = "0 0 0 ? * MON") // Cron expression for weekly task
    public void addWeeklyRecurringIncome() {
        List<Income> recurringIncomes = incomeService.getRecurringIncome();

        for (Income income : recurringIncomes) {
            if ("WEEKLY".equalsIgnoreCase(income.getFrequency())) {
                Income newIncome = new Income();
                newIncome.setAmount(income.getAmount());
                newIncome.setSource(income.getSource());
                newIncome.setDate(LocalDate.now().plusWeeks(1)); // Add one week to the date
                newIncome.setIsRecurring(true);
                newIncome.setFrequency("WEEKLY");

                incomeService.addIncome(newIncome);
            }
        }
    }

    // Scheduled task that runs once a month (1st day of each month at midnight)
    @Scheduled(cron = "0 0 0 1 * ?") // Cron expression for monthly task
    public void addMonthlyRecurringIncome() {
        List<Income> recurringIncomes = incomeService.getRecurringIncome();

        for (Income income : recurringIncomes) {
            if ("MONTHLY".equalsIgnoreCase(income.getFrequency())) {
                Income newIncome = new Income();
                newIncome.setAmount(income.getAmount());
                newIncome.setSource(income.getSource());
                newIncome.setDate(LocalDate.now().plusMonths(1)); // Add one month to the date
                newIncome.setIsRecurring(true);
                newIncome.setFrequency("MONTHLY");

                incomeService.addIncome(newIncome);
            }
        }
    }

    // Scheduled task that runs once a year (1st January at midnight)
    @Scheduled(cron = "0 0 0 1 1 ?") // Cron expression for annual task
    public void addAnnualRecurringIncome() {
        List<Income> recurringIncomes = incomeService.getRecurringIncome();

        for (Income income : recurringIncomes) {
            if ("ANNUAL".equalsIgnoreCase(income.getFrequency())) {
                Income newIncome = new Income();
                newIncome.setAmount(income.getAmount());
                newIncome.setSource(income.getSource());
                newIncome.setDate(LocalDate.now().plusYears(1)); // Add one year to the date
                newIncome.setIsRecurring(true);
                newIncome.setFrequency("ANNUAL");

                incomeService.addIncome(newIncome);
            }
        }
    }

    // You can create similar methods for expenses below

    // Scheduled task that runs once a day at midnight
    @Scheduled(cron = "0 0 0 * * ?") // Cron expression for daily task
    public void addDailyRecurringExpense() {
        List<Expense> recurringExpenses = expenseService.getRecurringExpenses();

        for (Expense expense : recurringExpenses) {
            if ("DAILY".equalsIgnoreCase(expense.getFrequency())) {
                Expense newExpense = new Expense();
                newExpense.setAmount(expense.getAmount());
                newExpense.setCategory(expense.getCategory());
                newExpense.setDate(LocalDate.now().plusDays(1)); // Add one day to the date
                newExpense.setIsRecurring(true);
                newExpense.setFrequency("DAILY");

                expenseService.addExpense(newExpense);
            }
        }
    }

    // Scheduled task that runs once a week (every Monday at midnight)
    @Scheduled(cron = "0 0 0 ? * MON") // Cron expression for weekly task
    public void addWeeklyRecurringExpense() {
        List<Expense> recurringExpenses = expenseService.getRecurringExpenses();

        for (Expense expense : recurringExpenses) {
            if ("WEEKLY".equalsIgnoreCase(expense.getFrequency())) {
                Expense newExpense = new Expense();
                newExpense.setAmount(expense.getAmount());
                newExpense.setCategory(expense.getCategory());
                newExpense.setDate(LocalDate.now().plusWeeks(1)); // Add one week to the date
                newExpense.setIsRecurring(true);
                newExpense.setFrequency("WEEKLY");

                expenseService.addExpense(newExpense);
            }
        }
    }

    // Scheduled task that runs once a month (1st day of each month at midnight)
    @Scheduled(cron = "0 0 0 1 * ?") // Cron expression for monthly task
    public void addMonthlyRecurringExpense() {
        List<Expense> recurringExpenses = expenseService.getRecurringExpenses();

        for (Expense expense : recurringExpenses) {
            if ("MONTHLY".equalsIgnoreCase(expense.getFrequency())) {
                Expense newExpense = new Expense();
                newExpense.setAmount(expense.getAmount());
                newExpense.setCategory(expense.getCategory());
                newExpense.setDate(LocalDate.now().plusMonths(1)); // Add one month to the date
                newExpense.setIsRecurring(true);
                newExpense.setFrequency("MONTHLY");

                expenseService.addExpense(newExpense);
            }
        }
    }

    // Scheduled task that runs once a year (1st January at midnight)
    @Scheduled(cron = "0 0 0 1 1 ?") // Cron expression for annual task
    public void addAnnualRecurringExpense() {
        List<Expense> recurringExpenses = expenseService.getRecurringExpenses();

        for (Expense expense : recurringExpenses) {
            if ("ANNUAL".equalsIgnoreCase(expense.getFrequency())) {
                Expense newExpense = new Expense();
                newExpense.setAmount(expense.getAmount());
                newExpense.setCategory(expense.getCategory());
                newExpense.setDate(LocalDate.now().plusYears(1)); // Add one year to the date
                newExpense.setIsRecurring(true);
                newExpense.setFrequency("ANNUAL");

                expenseService.addExpense(newExpense);
            }
        }
    }
}

