package ir.runo.shamsidate;

import com.hosseini.persian.dt.PersianDT;
import com.hosseini.persian.dt.PersianDate.Generate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by runo280 on 3/13/18.
 */

public class Date {

    public static String getPersianDate() {
        Calendar calendar = Calendar.getInstance();
        return generate(calendar, getFormat(calendar));
    }

    private static String getFormat(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    private static String generate(Calendar calendar, String format) {
        Generate generate = PersianDT
                .Instance()
                .generate(format, "{DATE}").Separator("-");
        return String.format(Locale.forLanguageTag("fa"),"امروز %s %d %s %d",
                generate.getDayName(),
                generate.getDayDigit(),
                generate.getJustMonthName(),
                generate.getYear());

    }
}
