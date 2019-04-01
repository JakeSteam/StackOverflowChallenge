package uk.co.jakelee.stackoverflowchallenge

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.jakelee.stackoverflowchallenge.adapter.UserListAdapter
import uk.co.jakelee.stackoverflowchallenge.api.StackOverflowService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.base_url))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(StackOverflowService::class.java)

        search_button.setOnClickListener { _ ->
            if (!search_field.text.isEmpty())
                service.getUsers(
                    searchTerm = search_field.text.toString(),
                    results = resources.getInteger(R.integer.num_results)
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        user_list.adapter = UserListAdapter(it.users)
                    }, {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    })
        }
    }
}
