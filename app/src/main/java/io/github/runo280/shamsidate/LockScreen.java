package io.github.runo280.shamsidate;

import android.content.Context;
import android.widget.TextClock;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by runo280 on 3/13/18.
 */

public class LockScreen implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.systemui"))
            return;

        XposedHelpers.findAndHookMethod("com.android.keyguard.KeyguardStatusView",
                lpparam.classLoader,
                "getOwnerInfo",
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return Utils.getPersianDate();
                    }
                });

        XposedHelpers.findAndHookMethod("com.android.keyguard.KeyguardStatusView", lpparam.classLoader, "refreshTime", new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    TextClock clock = (TextClock) XposedHelpers.getObjectField(param.thisObject, "mClockView");
                    TextView ownerInfo = (TextView) XposedHelpers.getObjectField(param.thisObject, "mOwnerInfo");

                    Context context = clock.getContext();

                    if (Utils.fontIsPresent(context)) {
                        clock.setTypeface(Utils.getClockTypeFace(context));
                        ownerInfo.setTypeface(Utils.getClockTypeFace(context));
                    } else {
                        XposedBridge.log("Font not found!");
                    }

                } catch (Throwable t) {
                    XposedBridge.log(t);
                }
            }
        });

        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                TextView tv = (TextView) param.thisObject;

                Context context = tv.getContext();

                if (Utils.fontIsPresent(context)) {
                    tv.setTypeface(Utils.getClockTypeFace(context));
                } else {
                    XposedBridge.log("Font not found!");
                }
            }
        });
    }
}