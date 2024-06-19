package com.example.spending.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static String convertDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
    }
}
