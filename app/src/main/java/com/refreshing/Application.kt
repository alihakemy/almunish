package com.refreshing

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class Application :Application() {
    private val localizationDelegate = LocalizationApplicationDelegate()
    override fun attachBaseContext(base: Context) {
        localizationDelegate.setDefaultLanguage(base, Locale.ENGLISH)
        super.attachBaseContext(localizationDelegate.attachBaseContext(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localizationDelegate.onConfigurationChanged(this)
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationDelegate.getResources(baseContext, super.getResources())
    }
}