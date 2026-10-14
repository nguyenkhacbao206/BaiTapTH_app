package com.example.baitapth;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExpenseDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvAmount, tvCategory, tvDate, tvNote;
    private String expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvAmount = findViewById(R.id.tvDetailAmount);
        tvCategory = findViewById(R.id.tvDetailCategory);
        tvDate = findViewById(R.id.tvDetailDate);
        tvNote = findViewById(R.id.tvDetailNote);

        findViewById(R.id.btnBackHeaderDetail).setOnClickListener(v -> finish());
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        expenseId = getIntent().getStringExtra(MainActivity.EXTRA_EXPENSE_ID);
        if (expenseId == null) {
            Toast.makeText(this, "Không tìm thấy thông tin khoản chi", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Expense expense = ExpenseRepository.getInstance().getExpenseById(expenseId);
        if (expense == null) {
            Toast.makeText(this, "Khoản chi không tồn tại", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvTitle.setText(expense.getTitle());
        tvAmount.setText(CurrencyUtils.formatVND(expense.getAmount()));
        tvCategory.setText(expense.getCategory());
        tvDate.setText(expense.getDate());

        if (TextUtils.isEmpty(expense.getNote())) {
            tvNote.setText("(Không có ghi chú)");
        } else {
            tvNote.setText(expense.getNote());
        }

        findViewById(R.id.btnDeleteExpense).setOnClickListener(v -> deleteExpense());
    }

    private void deleteExpense() {
        if (expenseId != null) {
            ExpenseRepository.getInstance().deleteExpense(expenseId);
            Toast.makeText(this, "Đã xóa khoản chi!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
