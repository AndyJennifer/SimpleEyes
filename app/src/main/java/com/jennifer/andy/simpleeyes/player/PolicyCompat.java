package com.jennifer.andy.simpleeyes.player;

import android.content.Context;
import android.view.Window;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Author:  andy.xwt
 * Date:    2018/1/9 22:32
 * Description:PhoneWindow代理类
 */


public class PolicyCompat {

    private static final String PHONE_WINDOW_CLASS_NAME = "com.android.internal.policy.PhoneWindow";
    private static final String POLICY_MANAGER_CLASS_NAME = "com.android.internal.policy.PolicyManager";

    /**
     * 6.0通过反射拿不到phoneWindow
     */
    public static Window createPhoneWindow(Context context) {
        try {
            Class<?> clazz = Class.forName(PHONE_WINDOW_CLASS_NAME);
            Constructor c = clazz.getConstructor(Context.class);
            return (Window) c.newInstance(context);
        } catch (Exception e) {
           return  makeNewWindow(context);
        }
    }

    private static Window makeNewWindow(Context context) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(POLICY_MANAGER_CLASS_NAME);
            Method m = clazz.getMethod("makeNewWindow", Context.class);
            return (Window) m.invoke(null, context);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
