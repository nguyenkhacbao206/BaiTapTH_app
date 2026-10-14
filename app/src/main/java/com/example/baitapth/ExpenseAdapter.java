package com.example.baitapth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Expense expense);
    }

    private List<Expense> expenseList;
    private final OnItemClickListener listener;

    public ExpenseAdapter(List<Expense> expenseList, OnItemClickListener listener) {
        this.expenseList = expenseList;
        this.listener = listener;
    }

    public void updateData(List<Expense> newList) {
        this.expenseList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.bind(expense, listener);
    }

    @Override
    public int getItemCount() {
        return expenseList != null ? expenseList.size() : 0;
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAmount, tvCategory, tvDate;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvExpenseTitle);
            tvAmount = itemView.findViewById(R.id.tvExpenseAmount);
            tvCategory = itemView.findViewById(R.id.tvExpenseCategory);
            tvDate = itemView.findViewById(R.id.tvExpenseDate);
        }

        public void bind(final Expense expense, final OnItemClickListener listener) {
            tvTitle.setText(expense.getTitle());
            tvAmount.setText(CurrencyUtils.formatVND(expense.getAmount()));
            tvCategory.setText(expense.getCategory());
            tvDate.setText(expense.getDate());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(expense);
                }
            });
        }
    }
}
