package uk.co.jakelee.stackoverflowchallenge

import android.content.Intent
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import org.hamcrest.CoreMatchers.`is`
import uk.co.jakelee.stackoverflowchallenge.adapter.UserListAdapter

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var mActivityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    private var server: MockWebServer? = null

    @Before
    fun setUp() {
        server = MockWebServer()
        server!!.start()
        ApiConstants.BASE_URL = server!!.url("/").toString()
    }

    @Test
    fun badResponseTest() {
        // server returns error
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
        mActivityRule.launchActivity(Intent())
        onView(withId(R.id.search_field)).perform(typeText("test"))
        onView(withId(R.id.search_button)).perform(click())
        onView(withId(R.id.user_list)).check(RecyclerViewItemCountAssertion(2))

        // Given the user is looking at a list of results
        // When one is tapped
        // Then the profile activity should be opened
        onView(withId(R.id.user_list)).perform(RecyclerViewActions.actionOnItemAtPosition<UserListAdapter.ViewHolder>(0, click()))
        Intents.init()
        intended(hasComponent(UserActivity::class.java!!.getClassName()))
    }

    @After
    fun tearDown() {
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

    private val expectedUsername = "username1"
    private val successfulResponse = "{  \n" +
            "   \"items\":[  \n" +
            "      {  \n" +
            "         \"badge_counts\":{  \n" +
            "            \"bronze\":0,\n" +
            "            \"silver\":0,\n" +
            "            \"gold\":0\n" +
            "         },\n" +
            "         \"account_id\":9123924,\n" +
            "         \"is_employee\":false,\n" +
            "         \"last_modified_date\":1483458528,\n" +
            "         \"last_access_date\":1477396052,\n" +
            "         \"reputation_change_year\":0,\n" +
            "         \"reputation_change_quarter\":0,\n" +
            "         \"reputation_change_month\":0,\n" +
            "         \"reputation_change_week\":0,\n" +
            "         \"reputation_change_day\":0,\n" +
            "         \"reputation\":6,\n" +
            "         \"creation_date\":1472832455,\n" +
            "         \"user_type\":\"registered\",\n" +
            "         \"user_id\":6788436,\n" +
            "         \"link\":\"https://stackoverflow.com/users/6788436/1whispers\",\n" +
            "         \"profile_image\":\"https://lh4.googleusercontent.com/-Ehl6AMXza6s/AAAAAAAAAAI/AAAAAAAAAA0/a8Vr8qFAhGE/photo.jpg?sz=128\",\n" +
            "         \"display_name\":\"username1\"\n" +
            "      },\n" +
            "      {  \n" +
            "         \"badge_counts\":{  \n" +
            "            \"bronze\":0,\n" +
            "            \"silver\":0,\n" +
            "            \"gold\":0\n" +
            "         },\n" +
            "         \"account_id\":2748769,\n" +
            "         \"is_employee\":false,\n" +
            "         \"last_modified_date\":1381548945,\n" +
            "         \"last_access_date\":1504702805,\n" +
            "         \"reputation_change_year\":0,\n" +
            "         \"reputation_change_quarter\":0,\n" +
            "         \"reputation_change_month\":0,\n" +
            "         \"reputation_change_week\":0,\n" +
            "         \"reputation_change_day\":0,\n" +
            "         \"reputation\":1,\n" +
            "         \"creation_date\":1368156891,\n" +
            "         \"user_type\":\"registered\",\n" +
            "         \"user_id\":2368582,\n" +
            "         \"location\":\"chennai\",\n" +
            "         \"website_url\":\"http://www.twitter.com/iamvasi\",\n" +
            "         \"link\":\"https://stackoverflow.com/users/2368582/1vashist-marichamy\",\n" +
            "         \"profile_image\":\"https://i.stack.imgur.com/lOixV.jpg?s=128&g=1\",\n" +
            "         \"display_name\":\"1Vashist Marichamy\"\n" +
            "      }\n" +
            "   ],\n" +
            "   \"has_more\":true,\n" +
            "   \"quota_max\":300,\n" +
            "   \"quota_remaining\":233\n" +
            "}"

}
