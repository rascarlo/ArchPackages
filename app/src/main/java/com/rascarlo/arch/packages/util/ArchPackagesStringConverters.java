/*
 *     Copyright (C) 2018 rascarlo <rascarlo@gmail.com>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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