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

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent?.extras?.getParcelable<User>(USER_EXTRA)?.let {
            Picasso.get().load(it.avatar).into(user_image)

            title = it.name
            user_name.text = it.name
            user_reputation.text = "${it.reputation} reputation"
            user_badges.text = String.format(
                "%d gold, %d silver, %d bronze badges",
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
                user_age.text = "${it.age} years old"
            }

            val formatter = SimpleDateFormat("dd/MM/yyyy")
            user_creation_date.text = "Registered on ${formatter.format(Date(it.created * 1000))}"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val USER_EXTRA = "uk.co.jakelee.stackoverflowchallenge.user"
    }
}