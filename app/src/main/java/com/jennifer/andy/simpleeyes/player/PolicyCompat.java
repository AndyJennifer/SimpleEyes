package com.jennifer.andy.simpleeyes.player;

import android.content.Context;
import android.view.Window;

import java.lang.reflect.Constructor;

/**
 * Author:  andy.xwt
 * Date:    2018/1/9 22:32
 * Description:PhoneWindow代理类
 */


public class PolicyCompat {

    private static final String PHONE_WINDOW_CLASS_NAME = "com.android.internal.policy.PhoneWindow";

    public static Window createPhoneWindow(Context context) {
        try {
            Class<?> clazz = Class.forName(PHONE_WINDOW_CLASS_NAME);
            Constructor c = clazz.getConstructor(Context.class);
            return (Window) c.newInstance(context);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
