package com.anifichadia.bootstrap.testing.ui.testframework.espresso.matcher

import android.content.res.Resources
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-16
 */
class RecyclerViewSizeMatcher private constructor(
    private val size: Int
) : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("RecyclerView with expected size: $size")
    }

    override fun matchesSafely(item: RecyclerView): Boolean {
        return item.adapter?.itemCount == size
    }


    companion object {
        fun recyclerViewWithSize(size: Int) = RecyclerViewSizeMatcher(size)
    }
}


/**
 * Based on https://github.com/dannyroa/espresso-samples/blob/master/RecyclerView/app/src/androidTest/java/com/dannyroa/espresso_samples/recyclerview/RecyclerViewMatcher.java
 *
 * @author Aniruddh Fichadia
 * @date 2020-10-16
 */
class RecyclerViewPositionMatcher private constructor(
    @IdRes private val recyclerViewId: Int,
    private val position: Int,
    @IdRes private val targetViewId: Int = -1
) : TypeSafeMatcher<View>() {

    private var resources: Resources? = null


    override fun describeTo(description: Description) {
        var idDescription = recyclerViewId.toString()
        val resources = this.resources
        if (resources != null) {
            idDescription = try {
                resources.getResourceName(recyclerViewId)
            } catch (e: Resources.NotFoundException) {
                "$recyclerViewId (resource name not found)"
            }
        }
        description.appendText("RecyclerView with id: $idDescription with item at position: $position")
    }

    override fun matchesSafely(item: View): Boolean {
        resources = item.resources

        val recyclerView = item.rootView.findViewById<View>(recyclerViewId) as? RecyclerView
        if (recyclerView != null) {
            val childView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView
            if (childView != null) {
                return if (targetViewId == -1) {
                    item === childView
                } else {
                    val targetView = childView.findViewById<View>(targetViewId)
                    item === targetView
                }
            }
        }

        return false
    }


    companion object {
        fun atRecyclerViewPosition(@IdRes recyclerViewId: Int, position: Int, @IdRes targetViewId: Int = -1) =
            RecyclerViewPositionMatcher(recyclerViewId, position, targetViewId)
    }
}
