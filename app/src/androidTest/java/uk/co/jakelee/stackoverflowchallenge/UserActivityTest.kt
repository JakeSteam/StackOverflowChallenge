package uk.co.jakelee.stackoverflowchallenge

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.StringContains.containsString
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.jakelee.stackoverflowchallenge.model.BadgeList
import uk.co.jakelee.stackoverflowchallenge.model.User

@RunWith(AndroidJUnit4::class)
class UserActivityTest {

    @get:Rule
    var userActivityRule = ActivityTestRule<UserActivity>(UserActivity::class.java, true, false)

    @Test
    fun noIntentPassesTest() {
        val intent = Intent()
        userActivityRule.launchActivity(intent)
        assertTrue(userActivityRule.getActivity().isFinishing())
    }

    @Test
    fun displayMinimalUserTest() {
        val intent = Intent()
        intent.putExtra(UserActivity.USER_EXTRA, expectedMinimalUser)
        userActivityRule.launchActivity(intent)

        //onView(withId(R.id.user_image)).check()
        onView(withId(R.id.user_name)).check(matches(withText(expectedMinimalUser.name)))
        onView(withId(R.id.user_reputation)).check(matches(withText(containsString(expectedMinimalUser.reputation.toString()))))
        onView(withId(R.id.user_badges)).check(matches(withText(containsString(expectedMinimalUser.badges.gold.toString()))))
        onView(withId(R.id.user_badges)).check(matches(withText(containsString(expectedMinimalUser.badges.silver.toString()))))
        onView(withId(R.id.user_badges)).check(matches(withText(containsString(expectedMinimalUser.badges.bronze.toString()))))
        onView(withId(R.id.user_location)).check(matches(not(isDisplayed())))
        onView(withId(R.id.user_age)).check(matches(not(isDisplayed())))
    }

    @Test
    fun displayPopulatedUserTest() {
        val intent = Intent()
        intent.putExtra(UserActivity.USER_EXTRA, expectedPopulatedUser)
        userActivityRule.launchActivity(intent)

        //onView(withId(R.id.user_image)).check()
        onView(withId(R.id.user_name)).check(matches(withText(expectedPopulatedUser.name)))
        onView(withId(R.id.user_reputation)).check(matches(withText(containsString(expectedPopulatedUser.reputation.toString()))))
        onView(withId(R.id.user_badges)).check(matches(withText(containsString(expectedPopulatedUser.badges.gold.toString()))))
        onView(withId(R.id.user_badges)).check(matches(withText(containsString(expectedPopulatedUser.badges.silver.toString()))))
        onView(withId(R.id.user_badges)).check(matches(withText(containsString(expectedPopulatedUser.badges.bronze.toString()))))
        onView(withId(R.id.user_location)).check(matches(withText(expectedPopulatedUser.location)))
        onView(withId(R.id.user_age)).check(matches(withText(containsString(expectedPopulatedUser.age.toString()))))
    }

    val expectedMinimalUser = User(
        id = 321,
        reputation = 10,
        created = 99999999,
        avatar = "https://via.placeholder.com/400x400",
        location = null,
        age = null,
        name = "User 1",
        badges = BadgeList(99, 88, 77)
    )

    val expectedPopulatedUser = User(
        id = 123,
        reputation = 1000,
        created = 99999,
        location = "Earth",
        age = 50,
        avatar = "https://via.placeholder.com/400x400",
        name = "User 2",
        badges = BadgeList(11, 22, 33)
    )
}
