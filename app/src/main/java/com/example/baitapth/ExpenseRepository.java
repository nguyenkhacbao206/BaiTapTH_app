package com.example.baitapth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpenseRepository {
    private static ExpenseRepository instance;
    private final List<Expense> expenseList;

    private ExpenseRepository() {
        expenseList = new ArrayList<>();
        // Seed initial sample data as specified in requirements:
        // 1. Ăn sáng | 30.000 | Ăn uống | 12/10/2026
        // 2. Đổ xăng | 50.000 | Đi lại | 12/10/2026
        // 3. Mua sách | 120.000 | Học tập | 13/10/2026
        expenseList.add(new Expense(
                UUID.randomUUID().toString(),
                "Ăn sáng",
                30000,
                "Ăn uống",
                "12/10/2026",
                "Ăn phở sáng"
        ));
        expenseList.add(new Expense(
                UUID.randomUUID().toString(),
                "Đổ xăng",
                50000,
                "Đi lại",
                "12/10/2026",
                "Đổ xăng xe máy"
        ));
        expenseList.add(new Expense(
                UUID.randomUUID().toString(),
                "Mua sách",
                120000,
                "Học tập",
                "13/10/2026",
                "Sách Lập trình Android"
        ));
    }

    public static synchronized ExpenseRepository getInstance() {
        if (instance == null) {
            instance = new ExpenseRepository();
        }
        return instance;
    }

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenseList);
    }

    public Expense getExpenseById(String id) {
        for (Expense expense : expenseList) {
            if (expense.getId().equals(id)) {
                return expense;
            }
        }
        return null;
    }

    public void addExpense(Expense expense) {
        if (expense.getId() == null || expense.getId().isEmpty()) {
            expense.setId(UUID.randomUUID().toString());
        }
        expenseList.add(0, expense);
    }

    public boolean deleteExpense(String id) {
        for (int i = 0; i < expenseList.size(); i++) {
            if (expenseList.get(i).getId().equals(id)) {
                expenseList.remove(i);
                return true;
            }
        }
        return false;
    }

    public double getTotalAmount() {
        double total = 0;
        for (Expense expense : expenseList) {
            total += expense.getAmount();
        }
        return total;
    }
}
