package io.github.runo280.shamsidate;

import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.os.Build;

import java.util.Date;
import java.util.Locale;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by runo280
 */
public class LockScreen implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.systemui"))
            return;
        String methodName = Build.VERSION.SDK_INT == Build.VERSION_CODES.Q ? "getFormattedDateLocked" : "getFormattedDate";
        XposedHelpers.findAndHookMethod("com.android.systemui.keyguard.KeyguardSliceProvider",
                lpparam.classLoader, methodName,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        DateFormat dateFormat = (DateFormat) XposedHelpers.getObjectField(param.thisObject, "mDateFormat");
                        String str = (String) XposedHelpers.getObjectField(param.thisObject, "mDatePattern");
                        Date date = (Date) XposedHelpers.getObjectField(param.thisObject, "mCurrentTime");
                        if (dateFormat == null) {
                            dateFormat = DateFormat.getInstanceForSkeleton(str, Locale.getDefault());
                            dateFormat.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
                        }
                        date.setTime(System.currentTimeMillis());
                        return String.format("%s %s %s", dateFormat.format(date), "◼", Utils.getPersianDateShort());
                    }
                });
    }
}