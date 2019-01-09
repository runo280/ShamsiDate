package ir.runo.shamsidate;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;

import com.hosseini.persian.dt.PersianDT;
import com.hosseini.persian.dt.PersianDate.Generate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.core.graphics.ColorUtils;

/**
 * Created by runo280 on 3/13/18.
 */

public class Utils {

    private static Typeface font = null;
    private static String fontPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/Android/ShamsiDateFont.ttf";
    private static String fontFileName = "Vazir_Medium_FD.ttf";

    private static String getFormat(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static String getPersianDate() {
        Calendar calendar = Calendar.getInstance();
        return generate(getFormat(calendar));
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

    public static Typeface getClockTypeFace(Context context) {
        Log.d("FONT", fontPath);
        File fontFile = new File(fontPath);
        if (font == null)
            font = Typeface.createFromFile(fontFile);
        return font;
    }

    public static boolean fontIsPresent(Context context) {
        File fontFile = new File(fontPath);
        if (!fontFile.exists()) {
            FileUtils.copyAssets(context, fontFileName, fontPath);
        }
        return fontFile.exists();
    }

    public static void hideLauncherIcon(Context context, boolean hide) {
        int status = hide ? PackageManager.COMPONENT_ENABLED_STATE_DISABLED : PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        PackageManager p = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, MainActivity.class);
        p.setComponentEnabledSetting(componentName, status, PackageManager.DONT_KILL_APP);
        p.getComponentEnabledSetting(componentName);
    }

    public static int makeColorDarker(int baseColor, float ratio) {
        return ColorUtils.blendARGB(baseColor, Color.BLACK, ratio);
    }
}