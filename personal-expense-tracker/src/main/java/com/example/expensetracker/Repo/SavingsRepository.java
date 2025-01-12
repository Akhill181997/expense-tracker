package com.example.expensetracker.Repo;

import com.example.expensetracker.Entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
