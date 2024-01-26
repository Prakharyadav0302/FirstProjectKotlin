package com.example.firstprojectkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var recycler : RecyclerView
    lateinit var myAdapter : MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.recycler_view)
        supportActionBar?.hide()
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIInterface::class.java)

        val retrofitData = retrofitBuilder.getProductData()

        retrofitData.enqueue(object : Callback<MyData>{
            override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                var responseBody = response.body()
                var productData = responseBody?.products!!

                myAdapter = MyAdapter(this@MainActivity, productData )
                recycler.adapter = myAdapter
                recycler.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {
                Log.d(TAG, "onFailure:" + t.message)
            }
        })



    }
}

