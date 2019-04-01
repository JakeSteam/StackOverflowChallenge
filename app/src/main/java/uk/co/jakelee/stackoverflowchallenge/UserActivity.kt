package uk.co.jakelee.stackoverflowchallenge

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user.*
import uk.co.jakelee.stackoverflowchallenge.model.User
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent?.extras?.getParcelable<User>(USER_EXTRA) != null) {
            val user = intent!!.extras!!.getParcelable<User>(USER_EXTRA)!!
            Picasso.get()
                .load(user.avatar)
                .placeholder(R.drawable.placeholder)
                .into(user_image)
            populateProfileFields(user)
        } else {
            finish()
        }
    }

    private fun populateProfileFields(it: User) {
        user_name.text = it.name
        user_reputation.text = String.format(getString(R.string.user_reputation), it.reputation)
        user_badges.text = String.format(
            getString(R.string.user_badges),
            it.badges.gold,
            it.badges.silver,
            it.badges.bronze
        )

        if (it.location.isNullOrBlank()) {
            user_location.visibility = View.GONE
        } else {
            user_location.text = it.location
        }

        if (it.age == null || it.age <= 0) {
            user_age.visibility = View.GONE
        } else {
            user_age.text = String.format(getString(R.string.user_age), it.age)
        }

        val formatter = SimpleDateFormat(getString(R.string.user_registered_format), Locale.ENGLISH)
        val dateConverted = TimeUnit.SECONDS.toMillis(it.created)
        user_creation_date.text = String.format(getString(R.string.user_registered), formatter.format(Date(dateConverted)))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val USER_EXTRA = "uk.co.jakelee.stackoverflowchallenge.user"
    }
}