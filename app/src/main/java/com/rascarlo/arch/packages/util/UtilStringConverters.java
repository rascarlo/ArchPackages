package com.rascarlo.arch.packages.util;

import android.content.Context;
import android.text.format.Formatter;

public class UtilStringConverters {

    public static String convertBytesToMb(Context context, String bytes) {
        return Formatter.formatFileSize(context, Long.parseLong(bytes));
    }
}
