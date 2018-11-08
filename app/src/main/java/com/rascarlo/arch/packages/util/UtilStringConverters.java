package com.rascarlo.arch.packages.util;

import android.content.Context;
import android.text.format.Formatter;

import java.util.List;

public class UtilStringConverters {

    public static String convertBytesToMb(Context context, String bytes) {
        return Formatter.formatFileSize(context, Long.parseLong(bytes));
    }

    public static String convertListToCommaSeparatedString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        if (stringList.size() > 0) {
            for (int i = 0; i < stringList.size(); i++) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(stringList.get(i));
            }
        }
        return stringBuilder.toString();
    }

    public static String convertListToNewLineSeparatedString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        if (stringList.size() > 0) {
            for (int i = 0; i < stringList.size(); i++) {
                if (i > 0) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append("- ").append(stringList.get(i));
            }
        }
        return stringBuilder.toString();
    }
}
