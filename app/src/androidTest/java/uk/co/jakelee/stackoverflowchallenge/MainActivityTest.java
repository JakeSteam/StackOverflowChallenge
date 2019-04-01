package uk.co.jakelee.stackoverflowchallenge;

import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, true, false);
    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        //super.setUp();
        server = new MockWebServer();
        server.start();
        ApiConstants.BASE_URL = server.url("/").toString();
    }

    @Test
    public void testQuoteIsShown() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(response));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.search_field)).perform(typeText("test"));
        onView(withId(R.id.search_button)).perform(click());
        onView(withId(R.id.user_list)).check(new RecyclerViewItemCountAssertion(2));
    }


    public class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    private String response = "{  \n" +
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
            "         \"display_name\":\"1whispers\"\n" +
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
            "}";

}
