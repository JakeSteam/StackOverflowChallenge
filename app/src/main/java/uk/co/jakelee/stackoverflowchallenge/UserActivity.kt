package uk.co.jakelee.stackoverflowchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_user.*
import uk.co.jakelee.stackoverflowchallenge.model.User

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        intent?.extras?.getParcelable<User>(USER_EXTRA)?.let {
            user_name.text = it.name
            user_reputation.text = it.reputation.toString()
            user_badges.text = it.badges.toString()
            user_location.text = it.location
            user_age.text = it.age.toString()
            user_creation_date.text = it.created.toString()
        }
    }


    companion object {
        val USER_EXTRA = "uk.co.jakelee.stackoverflowchallenge.user"
    }
}