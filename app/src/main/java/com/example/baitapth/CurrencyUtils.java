package com.example.baitapth;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyUtils {
    public static String formatVND(double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
        return decimalFormat.format(amount) + " đ";
    }
}
