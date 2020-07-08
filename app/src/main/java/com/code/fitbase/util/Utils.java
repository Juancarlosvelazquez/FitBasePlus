package com.code.fitbase.util;

import android.widget.EditText;

import java.util.Date;

public class Utils {
    public static long DAY_IN_MILLISECONDS = 1000L * 60 * 60 * 24;

    public static Date nDaysAgo(int n) {
        long stamp = System.currentTimeMillis() - DAY_IN_MILLISECONDS * n;
        return new Date(stamp);
    }

    public static Date nDaysAgo(EditText input) {
        String daysText = input.getText().toString();
        int daysCount = Integer.parseInt(daysText);
        return nDaysAgo(daysCount);
    }
}
