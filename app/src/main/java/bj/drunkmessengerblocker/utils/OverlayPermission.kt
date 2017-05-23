package bj.drunkmessengerblocker.utils

import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import bj.drunkmessengerblocker.service.MyAccessibilityService

/**
 * Created by Josh Laird on 23/05/2017.
 */
object OverlayPermission {
    // To check if service is enabled
    @JvmStatic fun isAccessibilitySettingsOn(mContext: Context): Boolean {
        var accessibilityEnabled = 0
        val service = mContext.packageName + "/" + MyAccessibilityService::class.java.canonicalName
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.applicationContext.contentResolver,
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED)
            Log.v(mContext.packageName, "accessibilityEnabled = " + accessibilityEnabled)
        } catch (e: Settings.SettingNotFoundException) {
            Log.e(mContext.packageName, "Error finding setting, default accessibility to not found: " + e.message)
        }

        val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')

        if (accessibilityEnabled == 1) {
            Log.v(mContext.packageName, "***ACCESSIBILITY IS ENABLED*** -----------------")
            val settingValue = Settings.Secure.getString(
                    mContext.applicationContext.contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue)
                while (mStringColonSplitter.hasNext()) {
                    val accessibilityService = mStringColonSplitter.next()

                    Log.v(mContext.packageName, "-------------- > accessibilityService :: $accessibilityService $service")
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        Log.v(mContext.packageName, "We've found the correct setting - accessibility is switched on!")
                        return true
                    }
                }
            }
        } else {
            Log.v(mContext.packageName, "***ACCESSIBILITY IS DISABLED***")
        }

        return false
    }
}