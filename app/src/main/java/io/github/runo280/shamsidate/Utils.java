package io.github.runo280.shamsidate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;

import androidx.core.graphics.ColorUtils;

import com.hosseini.persian.dt.PersianDT;
import com.hosseini.persian.dt.PersianDate.Generate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by runo280 on 3/13/18.
 */

class Utils {

    private static Typeface font = null;
    private static Typeface farsiFont = null;
    private static String fontPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/Android/ShamsiFont.ttf";
    private static String FarsifontPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/Android/ShamsiFarsiFont.ttf";

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

    static Typeface getFarsiTypeFace(Context context) {
        Log.d("FONT", FarsifontPath);
        File fontFile = new File(FarsifontPath);
        if (farsiFont == null)
            farsiFont = Typeface.createFromFile(fontFile);
        return farsiFont;
    }

    static Typeface getTypeFace(Context context) {
        Log.d("FONT", fontPath);
        File fontFile = new File(fontPath);
        if (font == null)
            font = Typeface.createFromFile(fontFile);
        return font;
    }

    static boolean fontIsPresent(Context context) {
        File fontFile = new File(fontPath);
        File FarsifontFile = new File(FarsifontPath);
        if (!fontFile.exists()) {
            String fontFileName = "Vazir_Medium.ttf";
            FileUtils.copyAssets(context, fontFileName, fontPath);
        }
        if (!FarsifontFile.exists()) {
            String farsifontFileName = "Vazir_Medium_FD.ttf";
            FileUtils.copyAssets(context, farsifontFileName, FarsifontPath);
        }
        return fontFile.exists() && FarsifontFile.exists();
    }

    /*public static void hideLauncherIcon(Context context, boolean hide) {
        int status = hide ? PackageManager.COMPONENT_ENABLED_STATE_DISABLED : PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        PackageManager p = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, MainActivity.class);
        p.setComponentEnabledSetting(componentName, status, PackageManager.DONT_KILL_APP);
        p.getComponentEnabledSetting(componentName);
    }*/

    static int makeColorDarker(int baseColor, float ratio) {
        return ColorUtils.blendARGB(baseColor, Color.BLACK, ratio);
    }
}