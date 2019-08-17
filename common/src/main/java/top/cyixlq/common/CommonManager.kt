package top.cyixlq.common

import android.app.Application
import java.lang.RuntimeException

object CommonManager {

    private var application: Application? = null

    fun init(application: Application) {
        this.application = application
    }

    fun getApplication() : Application {
        application?.let {
            return it
        }
        throw RuntimeException("You must init CommonManager")
    }
}