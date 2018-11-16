package com.rascarlo.arch.packages.util;

import android.content.Context;
import android.text.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class ArchPackagesStringConverters {

    public static String convertBytesToMb(Context context, String bytes) {
        return Formatter.formatFileSize(context, Long.parseLong(bytes));
    }

    public static String convertDate(String stringDate, String stringFormat) {
        SimpleDateFormat simpleDateFormatCurrent = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        simpleDateFormatCurrent.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat simpleDateFormatParsed = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            return String.format(stringFormat, simpleDateFormatParsed.format(simpleDateFormatCurrent.parse(stringDate)));
        } catch (ParseException e) {
            return String.format(stringFormat, simpleDateFormatParsed.format(stringDate));
        }
    }
}