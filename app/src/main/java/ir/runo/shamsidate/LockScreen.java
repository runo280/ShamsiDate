package ir.runo.shamsidate;

import de.robv.android.xposed.IXposedHookLoadPackage;
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

        //XposedBridge.log("we are in lockscreen module");

        XposedHelpers.findAndHookMethod("com.android.keyguard.KeyguardStatusView",
                lpparam.classLoader,
                "getOwnerInfo",
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        //XposedBridge.log("we are in lockscreen");
                        return Date.getPersianDate();
                    }
                });

    }
}
