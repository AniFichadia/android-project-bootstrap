package com.anifichadia.bootstrap.testing.ui.testframework

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity

/**
 * @author Aniruddh Fichadia
 * @date 2021-05-16
 */
class UiComponentTestActivity : AppCompatActivity() {

    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        container = LinearLayout(this).apply {
            layoutParams = MarginLayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.MATCH_PARENT).apply {}
            orientation = LinearLayout.VERTICAL
        }
        setContentView(container)
    }

    fun addViewToTest(view: View) {
        container.addView(view)
    }
}

fun <ViewT : View> launchComponent(
    createView: (context: Context) -> ViewT,
    configure: ViewT.() -> Unit = {}
) = launchActivity<UiComponentTestActivity>(
    intent = Intent(
        ApplicationProvider.getApplicationContext(),
        UiComponentTestActivity::class.java,
    )
).apply {
    onActivity { activity ->
        val view = createView(activity).apply(configure)
        activity.addViewToTest(view)
    }
}
