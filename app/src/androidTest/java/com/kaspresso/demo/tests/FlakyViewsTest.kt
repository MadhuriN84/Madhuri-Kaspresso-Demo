package com.kaspresso.demo.tests

import android.Manifest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.eakurnikov.kaspressosample.R
import com.kaspresso.demo.screens.FlakyScreen
import com.kaspresso.demo.screens.MainScreen
import com.kaspresso.demo.screens.SecondScreen
import com.eakurnikov.kaspressosample.view.main.MainActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

/**
 * Created by Madhuri on 2020-12-13
 */
@RunWith(AndroidJUnit4::class)
class FlakyViewsTest : TestCase() {

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Test
    fun flakyViewsTest() {
        before {
            activityTestRule.launchActivity(null)
            /**
             * Some action to prepare the state
             */
        }.after {
            /**
             * Some action to revert the state
             */
        }.run {
            step("Open Flaky screen") {
                MainScreen {
                    title.hasText(R.string.main_title)
                    title.hasTextColor(R.color.colorPrimary)

                    toFlakyScreenBtn {
                        isVisible()
                        hasText(R.string.flaky_screen)
                        isClickable()
                        click()
                    }
                }
            }

            step("Check ScrollView screen is visible") {
                FlakyScreen {
                    scrollView.isVisible()
                }
            }

            step("Check flaky text view is visible") {
                FlakyScreen {

                    compose(timeoutMs = 5000) {
                        or(flakyTextView) {
                            isVisible()
                            hasText("2")
                        }
                        or(flakyTextView) {
                            isVisible()
                            hasText("Flaky TextView")
                        }

                    }
                }

            }

            step("Check flaky text view's text") {
                FlakyScreen {
                    flakySafely(timeoutMs = TimeUnit.SECONDS.toMillis(4)) {
                        flakyTextView.hasText(R.string.flaky_textview_text_end)
                    }
                }
            }

            step("Check flaky button is visible") {
                FlakyScreen {
                    flakyBtn {
                        isVisible()
                        hasText(R.string.flaky_btn_text_start)
                    }
                }
            }

            step("Check flaky button's text") {
                FlakyScreen {
                    flakySafely(timeoutMs = TimeUnit.SECONDS.toMillis(5)) {
                        flakyBtn {
                            hasText(R.string.flaky_btn_text_end)
                            click()
                        }
                    }
                }
            }

            step("Check success") {
                SecondScreen {
                    title {
                        isVisible()
                        hasTextColor(R.color.colorPrimary)
                        hasText(R.string.success)
                    }
                }
            }
        }
    }
}

//            Espresso.onView(ViewMatchers.withId(R.id.tv_flaky_2))
//                .check(
//                    ViewAssertions.matches(
//                        ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
//                    )
//                )
//                .check(
//                    ViewAssertions.matches(
//                        ViewMatchers.withText(R.string.flaky_textview_text_start)
//                    )
//                )
//                .check(
//                    ViewAssertions.matches(
//                        ViewMatchers.withText(R.string.flaky_textview_text_end)
//                    )
//                )