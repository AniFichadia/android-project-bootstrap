package com.anifichadia.sampleapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import com.anifichadia.bootstrap.app.ui.contentDescriptionDelegate
import com.anifichadia.bootstrap.app.ui.textDelegate

/**
 * @author Aniruddh Fichadia
 * @date 2021-05-16
 */
class TwoLabelViewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_two_label, this, true)
    }

    private val label1: TextView = findViewById(R.id.two_label_label_1)
    var label1Text by label1.textDelegate()
    var label1ContentDescription by label1.contentDescriptionDelegate()

    private val label2: TextView = findViewById(R.id.two_label_label_2)
    var label2Text by label2.textDelegate(hideWhenBlank = true)
    var label2ContentDescription by label2.contentDescriptionDelegate()

    init {
        context.withStyledAttributes(attrs, R.styleable.TwoLabelView, defStyleRes) {
            label1Text = getString(R.styleable.TwoLabelView_label1Text)
            label1ContentDescription = getString(R.styleable.TwoLabelView_label1ContentDescription)

            label2Text = getString(R.styleable.TwoLabelView_label2Text)
            label2ContentDescription = getString(R.styleable.TwoLabelView_label2ContentDescription)
        }
    }
}
