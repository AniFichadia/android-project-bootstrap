package com.anifichadia.app.shared

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle


/**
 * @author Aniruddh Fichadia
 * @date 2018-08-29
 */
object IntentFactory {
    fun canHandleIntent(context: Context, intent: Intent): Boolean {
        return intent.resolveActivity(context.packageManager) != null
    }


    fun createDialerIntent(phoneNumber: String): Intent {
        return Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
    }

    fun createEmailIntent(email: String): Intent {
        return Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("mailto:$email")
        }
    }

    fun createBrowserIntent(uri: String): Intent = createViewUriIntent(Uri.parse(uri))

    fun createViewUriIntent(uri: Uri): Intent = Intent(Intent.ACTION_VIEW, uri)


    fun launch(context: Context, intent: Intent, args: Bundle? = null): Boolean {
        return if (canHandleIntent(context, intent)) {
            context.startActivity(intent, args)
            true
        } else {
            false
        }
    }
}
