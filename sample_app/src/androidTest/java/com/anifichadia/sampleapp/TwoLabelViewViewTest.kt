package com.anifichadia.sampleapp

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anifichadia.bootstrap.testing.ui.testframework.launchComponent
import com.anifichadia.bootstrap.testing.ui.testframework.testrule.DisableAnimationsTestRule
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Aniruddh Fichadia
 * @date 2021-05-16
 */
@RunWith(AndroidJUnit4::class)
class TwoLabelViewViewTest {

    @get:Rule
    val disableAnimationsTestRule = DisableAnimationsTestRule(true)


    @Test
    fun whenBothLabelsSet_thenBothLabelsShown() {
        val viewId = View.generateViewId()

        launchComponent(::TwoLabelViewView) {
            id = viewId
            label1Text = "label1Text"
            label2Text = "label2Text"
        }

        onView(withId(viewId))
            .check(matches(allOf(
                hasDescendant(allOf(withId(R.id.two_label_label_1), withText("label1Text"))),
                hasDescendant(allOf(withId(R.id.two_label_label_2), withText("label2Text"))),
            )))
    }

    @Test
    fun whenLabel2NotSet_thenLabel2NotShown() {
        val viewId = View.generateViewId()

        launchComponent(::TwoLabelViewView) {
            id = viewId
            label1Text = "label1Text"
            label2Text = null
        }

        onView(withId(viewId))
            .check(matches(allOf(
                hasDescendant(allOf(withId(R.id.two_label_label_1), withText("label1Text"))),
                hasDescendant(allOf(withId(R.id.two_label_label_2), not(isDisplayed()))),
            )))
    }
}
