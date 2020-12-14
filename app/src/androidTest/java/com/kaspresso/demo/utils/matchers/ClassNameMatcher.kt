package com.kaspresso.demo.utils.matchers

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 * Created by Madhuri on 2020-12-13
 */
class ClassNameMatcher(
    private val expectedClassName: String
) : TypeSafeMatcher<String>() {

    override fun matchesSafely(item: String): Boolean =
        item == expectedClassName

    override fun describeTo(description: Description?) {
        description?.appendText("with class name $expectedClassName")
    }
}