package uk.co.jakelee.stackoverflowchallenge

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.jakelee.stackoverflowchallenge.adapter.UserListAdapter
import uk.co.jakelee.stackoverflowchallenge.api.StackOverflowService


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var mainActivityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    private var server: MockWebServer? = null

    @Before
    fun setUp() {
        server = MockWebServer()
        server!!.start()
        StackOverflowService.BASIC_URL = server!!.url("/").toString()
        Intents.init()
    }

    @Test
    fun badRequestTest() {
        val errorText = "Search"
        server!!.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(errorText)
        )

        // Given a user is on the main activity
        // When an invalid search is performed
        // Then no results should be shown
        mainActivityRule.launchActivity(Intent())
        onView(withId(R.id.search_field)).perform(typeText("test"))
        onView(withId(R.id.search_button)).perform(click())
        onView(withId(R.id.user_list)).check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun fullFlowTest() {
        server!!.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(successfulResponse)
        )

        // Given a user is on the main activity
        // When a search is performed
        // Then the correct number of results should be displayed
        mainActivityRule.launchActivity(Intent())
        onView(withId(R.id.search_field)).perform(typeText("test"))
        onView(withId(R.id.search_button)).perform(click())
        onView(withId(R.id.user_list)).check(RecyclerViewItemCountAssertion(2))

        // Given the user is looking at a list of results
        // When one is tapped
        // Then the user activity should be opened
        onView(withId(R.id.user_list)).perform(RecyclerViewActions.actionOnItemAtPosition<UserListAdapter.ViewHolder>(0, click()))
        intended(hasComponent(UserActivity::class.java!!.getName()))
    }

    @After
    fun tearDown() {
        Intents.release()
        server!!.shutdown()
    }

    // Converted from https://stackoverflow.com/a/37339656/608312
    inner class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            assertThat(adapter!!.itemCount, `is`(expectedCount))
        }
    }

    private val successfulResponse = "{  \n" +
            "   \"items\":[  \n" +
            "      {  \n" +
            "         \"badge_counts\":{  \n" +
            "            \"bronze\":1,\n" +
            "            \"silver\":2,\n" +
            "            \"gold\":3\n" +
            "         },\n" +
            "         \"account_id\":123456,\n" +
            "         \"is_employee\":false,\n" +
            "         \"last_modified_date\":1,\n" +
            "         \"last_access_date\":1,\n" +
            "         \"reputation_change_year\":0,\n" +
            "         \"reputation_change_quarter\":0,\n" +
            "         \"reputation_change_month\":0,\n" +
            "         \"reputation_change_week\":0,\n" +
            "         \"reputation_change_day\":0,\n" +
            "         \"reputation\":100,\n" +
            "         \"creation_date\":1,\n" +
            "         \"user_type\":\"registered\",\n" +
            "         \"user_id\":123456,\n" +
            "         \"link\":\"https://stackoverflow.com/users/0/test\",\n" +
            "         \"profile_image\":\"https://via.placeholder.com/400x400\",\n" +
            "         \"display_name\":\"username1\"\n" +
            "      },\n" +
            "      {  \n" +
            "         \"badge_counts\":{  \n" +
            "            \"bronze\":1,\n" +
            "            \"silver\":2,\n" +
            "            \"gold\":3\n" +
            "         },\n" +
            "         \"account_id\":1234567,\n" +
            "         \"is_employee\":false,\n" +
            "         \"last_modified_date\":1,\n" +
            "         \"last_access_date\":1,\n" +
            "         \"reputation_change_year\":0,\n" +
            "         \"reputation_change_quarter\":0,\n" +
            "         \"reputation_change_month\":0,\n" +
            "         \"reputation_change_week\":0,\n" +
            "         \"reputation_change_day\":0,\n" +
            "         \"reputation\":101,\n" +
            "         \"creation_date\":1,\n" +
            "         \"user_type\":\"registered\",\n" +
            "         \"user_id\":1234567,\n" +
            "         \"link\":\"https://stackoverflow.com/users/1/test\",\n" +
            "         \"profile_image\":\"https://via.placeholder.com/400x400\",\n" +
            "         \"display_name\":\"username2\"\n" +
            "      }\n" +
            "   ],\n" +
            "   \"has_more\":true,\n" +
            "   \"quota_max\":300,\n" +
            "   \"quota_remaining\":300\n" +
            "}"

}
