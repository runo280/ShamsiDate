package io.github.runo280.shamsidate;

import android.content.Context;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
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

        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P)
            setDateApi27(lpparam);
        else
            setDateApi28(lpparam);*/


        XposedHelpers.findAndHookMethod("com.android.keyguard.KeyguardStatusView", lpparam.classLoader, "refreshTime", new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    TextClock clock = (TextClock) XposedHelpers.getObjectField(param.thisObject, "mClockView");
                    TextView ownerInfo = (TextView) XposedHelpers.getObjectField(param.thisObject, "mOwnerInfo");

                    Context context = clock.getContext();

                    if (Utils.fontIsPresent(context)) {
                        clock.setTypeface(Utils.getFarsiTypeFace(context));
                    } else {
                        XposedBridge.log("Font not found!");
                    }

                } catch (Throwable t) {
                    XposedBridge.log(t);
                }
            }
        });

        XposedHelpers.findAndHookMethod("com.android.keyguard.KeyguardStatusView", lpparam.classLoader, "onFinishInflate", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                View[] mVisibleInDoze = (View[]) XposedHelpers.getObjectField(param.thisObject, "mVisibleInDoze");
                mVisibleInDoze = new View[]{};
            }
        });

        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                TextView tv = (TextView) param.thisObject;

                Context context = tv.getContext();

                if (Utils.fontIsPresent(context)) {
                    tv.setTypeface(Utils.getFarsiTypeFace(context));
                } else {
                    XposedBridge.log("Font not found!");
                }
            }
        });

        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.DateView", lpparam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                TextView tv = (TextView) param.thisObject;

                Context context = tv.getContext();

                if (Utils.fontIsPresent(context)) {
                    tv.setTypeface(Utils.getTypeFace(context));
                } else {
                    XposedBridge.log("Font not found!");
                }

                String dateEn = (String) XposedHelpers.getObjectField(param.thisObject, "mLastText");

                tv.setText(dateEn + " \uD83D\uDCC5 " + Utils.getPersianDateShort());


            }
        });
    }

    /*private void setDateApi27(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod("com.android.keyguard.KeyguardStatusView",
                lpparam.classLoader,
                "getOwnerInfo",
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return Utils.getPersianDate();
                    }
                });
    }

    private void setDateApi28(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod("com.android.keyguard.KeyguardStatusView",
                lpparam.classLoader,
                "updateOwnerInfo", new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        TextView owner = (TextView) XposedHelpers.getObjectField(param.thisObject, "mOwnerInfo");
                        owner.setText(Utils.getPersianDate());
                        return null;
                    }
                });
    }*/
}