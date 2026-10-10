/*
 * Smart Island (2026)
 * (c) Animesh Gupta - github.com/agupta07505
 * Licensed under the GNU GPL v3 License
 * Do not remove or alter this notice. - Per GPL-3.0 Section 4 & Section 5
 */

package com.agupta07505.smartisland.di

import android.content.Context
import com.agupta07505.smartisland.data.INotificationHistoryRepository
import com.agupta07505.smartisland.data.INotificationRepository
import com.agupta07505.smartisland.data.NotificationHistoryRepository
import com.agupta07505.smartisland.data.SmartIslandNotificationRepository
import com.agupta07505.smartisland.data.SmartIslandSettingsRepository

/** Plain singletons replacing Hilt (the app is built by Soong, there is no kapt). */
object SmartIslandRepositories {
    @Volatile private var settings: SmartIslandSettingsRepository? = null
    @Volatile private var notifications: INotificationRepository? = null
    @Volatile private var history: INotificationHistoryRepository? = null

    fun settingsRepository(context: Context): SmartIslandSettingsRepository =
        settings ?: synchronized(this) {
            settings ?: SmartIslandSettingsRepository(context.applicationContext).also { settings = it }
        }

    fun notificationRepository(context: Context): INotificationRepository =
        notifications ?: synchronized(this) {
            notifications ?: SmartIslandNotificationRepository().also { notifications = it }
        }

    fun historyRepository(context: Context): INotificationHistoryRepository =
        history ?: synchronized(this) {
            history ?: NotificationHistoryRepository(context.applicationContext).also { history = it }
        }
}
