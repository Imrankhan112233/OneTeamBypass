package com.deephat.oneteambypass;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainActivity extends Activity implements IXposedHookLoadPackage {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("OneTeam Bypass Active");
        setContentView(tv);
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.kpitb.oneteam")) {
            XposedBridge.log("OneTeamBypass: Successfully hooked com.kpitb.oneteam!");
            String targetClass = "com.kpitb.oneteam.DeveloperModeModule";
            String[] methodsToBypass = {
                "isDevOptionsEnabled", "isAdbEnabled", "isAirplaneModeOn",
                "hasSuspiciousAccessibility", "isSystemProxySet", "isVpnActive",
                "detectEmulator", "hasNativeMockLocation", "isDebuggerAttached",
                "isMockAppSet", "isOurAppMockLocationProvider", "detectRoot",
                "detectFakeGpsApps", "detectHookFrameworks", "detectVirtualEnvironment",
                "detectAppTampering"
            };
            for (String methodName : methodsToBypass) {
                try {
                    XposedHelpers.findAndHookMethod(targetClass, lpparam.classLoader, methodName, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            param.setResult(false);
                        }
                    });
                } catch (Throwable t) {}
            }
        }
    }
}

