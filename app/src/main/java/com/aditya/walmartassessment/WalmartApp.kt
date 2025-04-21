package com.aditya.walmartassessment
import android.app.Application
import com.aditya.walmartassessment.data.utility.ConnectivityChecker

/**
 * Used to monitor network availability
 */
class WalmartApp: Application() {
    lateinit var connectivityChecker: ConnectivityChecker

    override fun onCreate() {
        super.onCreate()
        connectivityChecker = ConnectivityChecker(applicationContext)
        connectivityChecker.checkConnectivity()
    }
}