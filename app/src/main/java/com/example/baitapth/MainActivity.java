package com.example.baitapth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_EXPENSE_ID = "extra_expense_id";

    private TextView tvTotalAmount;
    private TextView tvEmptyState;
    private RecyclerView rvExpenses;
    private ExpenseAdapter adapter;
    private ExpenseRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = ExpenseRepository.getInstance();

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        rvExpenses = findViewById(R.id.rvExpenses);

        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpenseAdapter(repository.getAllExpenses(), expense -> {
            Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
            intent.putExtra(EXTRA_EXPENSE_ID, expense.getId());
            startActivity(intent);
        });
        rvExpenses.setAdapter(adapter);

        findViewById(R.id.btnAddExpense).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        List<Expense> expenses = repository.getAllExpenses();
        adapter.updateData(expenses);

        double total = repository.getTotalAmount();
        tvTotalAmount.setText(CurrencyUtils.formatVND(total));

        if (expenses.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            rvExpenses.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            rvExpenses.setVisibility(View.VISIBLE);
        }
    }
}
