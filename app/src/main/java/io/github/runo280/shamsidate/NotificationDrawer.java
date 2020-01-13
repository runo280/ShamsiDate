package io.github.runo280.shamsidate;

import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by runo280
 */
public class NotificationDrawer implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.systemui"))
            return;
        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.DateView",
                lpparam.classLoader,
                "updateClock", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        TextView textView = (TextView) param.thisObject;
                        String date = (String) XposedHelpers.getObjectField(param.thisObject, "mLastText");
                        textView.setText(String.format("%s ◼ %s", date, Utils.getPersianDateShort()));
                    }
                });
    }
}