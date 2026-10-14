package com.example.baitapth;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etTitle, etAmount, etDate, etNote;
    private Spinner spCategory;
    private Calendar calendar;

    private static final String[] CATEGORIES = new String[]{
            "Ăn uống",
            "Đi lại",
            "Học tập",
            "Giải trí",
            "Sức khỏe",
            "Khác"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etTitle = findViewById(R.id.etAddTitle);
        etAmount = findViewById(R.id.etAddAmount);
        spCategory = findViewById(R.id.spAddCategory);
        etDate = findViewById(R.id.etAddDate);
        etNote = findViewById(R.id.etAddNote);

        findViewById(R.id.btnBackHeader).setOnClickListener(v -> finish());

        // Setup Category Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                CATEGORIES
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        // Setup Date Picker
        calendar = Calendar.getInstance();
        updateDateInView();

        etDate.setOnClickListener(v -> showDatePicker());

        findViewById(R.id.btnSaveExpense).setOnClickListener(v -> saveExpense());
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateInView();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDateInView() {
        String formattedDate = String.format(
                Locale.getDefault(),
                "%02d/%02d/%d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR)
        );
        etDate.setText(formattedDate);
    }

    private void saveExpense() {
        String title = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
        String amountStr = etAmount.getText() != null ? etAmount.getText().toString().trim() : "";
        String category = spCategory.getSelectedItem() != null ? spCategory.getSelectedItem().toString() : "Khác";
        String date = etDate.getText() != null ? etDate.getText().toString().trim() : "";
        String note = etNote.getText() != null ? etNote.getText().toString().trim() : "";

        if (TextUtils.isEmpty(title)) {
            etTitle.setError("Vui lòng nhập tên khoản chi");
            etTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(amountStr)) {
            etAmount.setError("Vui lòng nhập số tiền");
            etAmount.requestFocus();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                etAmount.setError("Số tiền phải lớn hơn 0");
                etAmount.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            etAmount.setError("Số tiền không hợp lệ");
            etAmount.requestFocus();
            return;
        }

        Expense newExpense = new Expense(
                null,
                title,
                amount,
                category,
                date,
                note
        );

        ExpenseRepository.getInstance().addExpense(newExpense);
        Toast.makeText(this, "Đã thêm khoản chi!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
