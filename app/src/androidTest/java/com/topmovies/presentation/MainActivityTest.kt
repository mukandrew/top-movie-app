package com.topmovies.presentation

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.topmovies.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@LargeTest
@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {


    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        onView(withId(R.id.movieListRecyclerView)).perform(swipeUp())
        Thread.sleep(500L)
        onView(withId(R.id.movieListRecyclerView)).perform(swipeDown())
        Thread.sleep(500L)
        val linearLayout = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.movieListRecyclerView),
                        childAtPosition(
                            withId(R.id.fragmentMovieList),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())
        Thread.sleep(500L)
        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navegar para cima"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
