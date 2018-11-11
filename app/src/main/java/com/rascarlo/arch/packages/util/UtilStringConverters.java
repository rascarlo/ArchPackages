package com.rascarlo.arch.packages.util;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.Formatter;

import java.util.List;

public class UtilStringConverters {

    public static String convertBytesToMb(Context context, String bytes) {
        return Formatter.formatFileSize(context, Long.parseLong(bytes));
    }

    public static String convertListToCommaSeparatedString(List<String> stringList) {
        return TextUtils.join(", ", stringList);
    }
}
