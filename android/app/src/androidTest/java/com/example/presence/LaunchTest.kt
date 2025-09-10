package com.example.presence

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun appLaunches_showsTitle() {
        // This text exists in MainActivity’s top bar in the starter repo
        rule.onNodeWithText("Friends Near You").assertExists()
    }
}
