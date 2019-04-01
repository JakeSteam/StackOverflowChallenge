package uk.co.jakelee.stackoverflowchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.jakelee.stackoverflowchallenge.api.StackOverflowService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_button.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(StackOverflowService::class.java)
            service.getUsers("abc")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    // pass to recyclerview
                    val a = 123
                }
        }
    }
}
