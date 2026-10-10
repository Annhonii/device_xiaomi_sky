/*
 * Smart Island (2026)
 * (c) Animesh Gupta - github.com/agupta07505
 * Licensed under the GNU GPL v3 License
 * Do not remove or alter this notice. - Per GPL-3.0 Section 4 & Section 5
 *
 * System-app variant: when the app is installed as a privileged app and holds
 * WRITE_SECURE_SETTINGS, it enables its own accessibility service and
 * notification listener directly, so Shizuku is not needed.
 */

package com.agupta07505.smartisland.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import com.agupta07505.smartisland.service.SmartIslandNotificationListenerService
import com.agupta07505.smartisland.service.SmartIslandOverlayService

object SystemGrant {

    fun isAvailable(context: Context): Boolean =
        context.checkSelfPermission(Manifest.permission.WRITE_SECURE_SETTINGS) ==
            PackageManager.PERMISSION_GRANTED

    fun grantAll(context: Context): Result<String> = runCatching {
        val pkg = context.packageName
        val accessibilityClass = "$pkg/${SmartIslandOverlayService::class.java.name}"
        val notificationClass = "$pkg/${SmartIslandNotificationListenerService::class.java.name}"
        val resolver = context.contentResolver

        Settings.Secure.putString(
            resolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
            ShizukuManager.getMergedAccessibilityServices(context, accessibilityClass)
        )
        Settings.Secure.putInt(resolver, Settings.Secure.ACCESSIBILITY_ENABLED, 1)
        Settings.Secure.putString(
            resolver,
            "enabled_notification_listeners",
            ShizukuManager.getMergedNotificationListeners(context, notificationClass)
        )
        "System permissions granted"
    }
}
