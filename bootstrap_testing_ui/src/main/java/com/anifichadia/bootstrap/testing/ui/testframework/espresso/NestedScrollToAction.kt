package com.anifichadia.bootstrap.testing.ui.testframework.espresso

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.Matcher


/**
 * @author Aniruddh Fichadia
 * @date 2020-10-09
 */
class NestedScrollToAction : DelegatedViewAction(ScrollToAction()) {

    override fun getConstraints(): Matcher<View> = anyOf(
        super.getConstraints(),
        allOf(
            withEffectiveVisibility(Visibility.VISIBLE),
            isDescendantOfA(
                isAssignableFrom(NestedScrollView::class.java)
            )
        )
    )
}
