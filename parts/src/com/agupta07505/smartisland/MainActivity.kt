/*
 * Smart Island (2026)
 * © Animesh Gupta — github.com/agupta07505
 * Licensed under the GNU GPL v3 License
 * Do not remove or alter this notice. - Per GPL-3.0 Section 4 & Section 5
 */

package com.agupta07505.smartisland

import com.agupta07505.smartisland.di.SmartIslandRepositories
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.agupta07505.smartisland.data.INotificationRepository
import com.agupta07505.smartisland.data.SmartIslandSettingsRepository
import com.agupta07505.smartisland.ui.SmartIslandHomeScreen
import com.agupta07505.smartisland.ui.SmartIslandTheme
import com.agupta07505.smartisland.util.SystemServiceRecovery
import com.agupta07505.smartisland.util.ShizukuManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
class MainActivity : ComponentActivity() {
    val settingsRepository: SmartIslandSettingsRepository by lazy { SmartIslandRepositories.settingsRepository(applicationContext) }
    val notificationRepository: INotificationRepository by lazy { SmartIslandRepositories.notificationRepository(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemServiceRecovery.requestRecovery(this)

        setContent {
            SmartIslandTheme {
                SmartIslandHomeScreen(
                    repository = settingsRepository,
                    notificationRepository = notificationRepository
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        SystemServiceRecovery.requestRecovery(this)
        CoroutineScope(Dispatchers.IO).launch { ShizukuManager.autoGrantAllPermissions(applicationContext) }
    }
}
