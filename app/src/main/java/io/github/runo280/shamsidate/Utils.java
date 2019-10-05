package io.github.runo280.shamsidate;

import com.hosseini.persian.dt.PersianDT;
import com.hosseini.persian.dt.PersianDate.Generate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by runo280
 */

class Utils {

    private static String getFormat(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.getTime());
    }

    static String getPersianDate() {
        Calendar calendar = Calendar.getInstance();
        return generate(getFormat(calendar));
    }

    static String getPersianDateShort() {
        Calendar calendar = Calendar.getInstance();
        return generateShort(getFormat(calendar));
    }

    private static String generate(String format) {
        Generate generate = PersianDT
                .Instance()
                .generate(format, "{DATE}").Separator("-");
        return String.format(Locale.forLanguageTag("fa"), "%s %d %s %d",
                generate.getDayName(),
                generate.getDayDigit(),
                generate.getJustMonthName(),
                generate.getYear());
    }

    private static String generateShort(String format) {
        Generate generate = PersianDT
                .Instance()
                .generate(format, "{DATE}").Separator("-");
        return String.format(Locale.forLanguageTag("fa"), "%s، %d %s",
                generate.getDayName(),
                generate.getDayDigit(),
                generate.getJustMonthName());

    }
}