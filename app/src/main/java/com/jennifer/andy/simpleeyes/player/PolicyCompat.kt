package com.jennifer.andy.simpleeyes.player

import android.content.Context
import android.view.Window


/**
 * Author:  andy.xwt
 * Date:    2020/3/9 15:07
 * Description:
 */

private const val PHONE_WINDOW_CLASS_NAME = "com.android.internal.policy.PhoneWindow"
private const val POLICY_MANAGER_CLASS_NAME = "com.android.internal.policy.PolicyManager"


class PolicyCompat {

    /**
     * 通过反射拿不到phoneWindow，当该方法失败时，通过[makeNewWindow]
     */
    fun createPhoneWindow(context: Context): Window {
        return try {
            val clazz = Class.forName(PHONE_WINDOW_CLASS_NAME)
            val c = clazz.getConstructor(Context::class.java)
            c.newInstance(context) as Window
        } catch (e: Exception) {
            makeNewWindow(context)
        }
    }

    private fun makeNewWindow(context: Context): Window {
        return try {
            val clazz = Class.forName(POLICY_MANAGER_CLASS_NAME)
            val m = clazz.getMethod("makeNewWindow", Context::class.java)
            m.invoke(null, context) as Window
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }
}
