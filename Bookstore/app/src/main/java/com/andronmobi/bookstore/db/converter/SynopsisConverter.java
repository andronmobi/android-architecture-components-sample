package com.andronmobi.bookstore.db.converter;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

public class SynopsisConverter {

    private static final String DELIM = "~-=-=-=-~";

    @TypeConverter
    public static List<String> stringToStringList(String data) {
        if (data != null) {
            return Arrays.asList(data.split(DELIM));
        }
        return null;
    }

    @TypeConverter
    public static String stringListToString(List<String> synopsisList) {
        if (synopsisList != null) {
            return TextUtils.join(DELIM, synopsisList.toArray());
        }
        return null;
    }
}
