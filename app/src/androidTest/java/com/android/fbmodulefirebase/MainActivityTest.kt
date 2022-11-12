package com.android.fbmodulefirebase

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
internal class MainActivityTest{

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun checkActivityVisibility(){
        onView(withId(R.id.layout_mainactivity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkTextVisibility(){
        onView(withId(R.id.textView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.idEditDate))
            .check(matches(isDisplayed()))

        onView(withId(R.id.club_house))
            .check(matches(isDisplayed()))

        onView(withId(R.id.book_slot_button))
            .check(matches(isDisplayed()))

        onView(withId(R.id.radio_group))
            .check(matches(isDisplayed()))

        onView(withId(R.id.radio_group1))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tennis_court))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rbslot1))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rbslot2))
            .check(matches(isDisplayed()))
    }

    @Test
    fun buttonClickTest(){
        onView(withId(R.id.book_slot_button))
            .perform(click())
    }

    @Test
    fun editDateViewTest(){
        onView(withId(R.id.idEditDate))
            .perform(click())
    }

}