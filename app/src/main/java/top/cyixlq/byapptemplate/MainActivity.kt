package top.cyixlq.byapptemplate

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import top.cyixlq.network.RetrofitClient
import top.cyixlq.network.utils.jsonToObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_hello_world.setOnClickListener { getData() }
        getData()
        getVkey()
    }

    private fun getVkey() {
        val timestamp = System.currentTimeMillis()
        val tag = (timestamp + 2447630961000L).toString()
        val retrofit = Retrofit.Builder().baseUrl("http://101.132.157.5")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getVKey("F000003aAYrm3GE0Ac.flac", String(Base64.encode(tag.toByteArray(), Base64.DEFAULT)),
            "MDAzYUFZcm0zR0UwQWM=")
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                val s = response.body()
                if (s != null)
                    Toast.makeText(this@MainActivity, s, Toast.LENGTH_LONG).show()
            }
        })
    }

    @SuppressLint("CheckResult")
    fun getData() {
        RetrofitClient.create()
            .setType("BY_Discount_store")
            .addParam("store_id", 6)
            .addParam("status", 1)
            .addParam("uid", 2)
            .execute()
            .map { it.jsonToObject<List<Coupon>>() }
            .subscribeBy(
                onError = {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    Log.e("CYTAG", it.localizedMessage)
                },
                onNext = {
                    for (coupon in it) {
                        Toast.makeText(this, coupon.id.toString(), Toast.LENGTH_LONG).show()
                        Log.e("CYTAG", coupon.toString())
                    }
                }
            )
    }
}
