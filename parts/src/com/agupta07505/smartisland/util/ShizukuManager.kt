/*
 * Smart Island (2026)
 * (c) Animesh Gupta - github.com/agupta07505
 * Licensed under the GNU GPL v3 License
 * Do not remove or alter this notice. - Per GPL-3.0 Section 4 & Section 5
 */

package com.agupta07505.smartisland.util

import android.content.Context
import android.provider.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** System build: no Shizuku. Same API, backed by com.agupta07505.smartisland.util.SystemGrant. */
object ShizukuManager {
    fun isInstalled(context: Context): Boolean = false
    fun isBinderAvailable(): Boolean = false
    fun hasPermission(): Boolean = false
    fun requestPermission(requestCode: Int = 1001) {}

    internal fun mergeColonSeparated(currentList: String, newEntry: String): String {
        val list = currentList.split(':').filter { it.isNotBlank() }.toMutableList()
        if (!list.contains(newEntry)) {
            list.add(newEntry)
        }
        return list.joinToString(":")
    }

    internal fun getMergedAccessibilityServices(context: Context, serviceComponent: String): String {
        val current = runCatching {
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
        }.getOrNull().orEmpty()
        return mergeColonSeparated(current, serviceComponent)
    }

    internal fun getMergedNotificationListeners(context: Context, listenerComponent: String): String {
        val current = runCatching {
            Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        }.getOrNull().orEmpty()
        return mergeColonSeparated(current, listenerComponent)
    }

    suspend fun autoGrantAllPermissions(context: Context): Result<String> =
        withContext(Dispatchers.IO) { SystemGrant.grantAll(context) }

    suspend fun grantNotificationListener(context: Context): Result<String> =
        withContext(Dispatchers.IO) { SystemGrant.grantAll(context) }

    suspend fun grantAccessibility(context: Context): Result<String> =
        withContext(Dispatchers.IO) { SystemGrant.grantAll(context) }

    suspend fun grantOemAutostartAndKillProtection(context: Context): Result<String> =
        Result.success("Handled by system config")
}
