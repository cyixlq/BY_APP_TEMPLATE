package top.cyixlq.byapptemplate

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import top.cyixlq.network.RetrofitClient
import top.cyixlq.network.utils.jsonToObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_hello_world.setOnClickListener { getData() }
        getData()
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
