package com.anifichadia.sampleapp

import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import com.facebook.testing.screenshot.ScreenshotRunner


/**
 * @author Aniruddh Fichadia
 * @date 2021-05-23
 */
class AppTestRunner : AndroidJUnitRunner() {

    override fun onCreate(args: Bundle?) {
        ScreenshotRunner.onCreate(this, args)
        super.onCreate(args)
    }

    override fun finish(resultCode: Int, results: Bundle?) {
        ScreenshotRunner.onDestroy()
        super.finish(resultCode, results)
    }
}
