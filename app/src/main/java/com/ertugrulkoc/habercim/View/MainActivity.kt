package com.ertugrulkoc.habercim.View

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ertugrulkoc.habercim.Model.ApiModel.ApiInterface
import com.ertugrulkoc.habercim.Root
import com.ertugrulkoc.habercim.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//https://newsapi.org/v2/top-headlines?country=tr&apiKey=17020d813bfc40c8856011cd1a22762b

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val BASE_URL = "https://newsapi.org"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create(ApiInterface::class.java)
            val call =
                api.getNews("/v2/top-headlines?country=tr&apiKey=17020d813bfc40c8856011cd1a22762b")
            call.enqueue(object : Callback<Root> {
                override fun onResponse(call: Call<Root>, response: Response<Root>) {
                    val veri = response.body()
                    Log.i("cevap", ": " + veri!!.articles!!.get(0).title)
                }

                override fun onFailure(call: Call<Root>, t: Throwable) {
                    Log.i("cevap", "onFailure: " + t.localizedMessage)
                }

            })

        } catch (e: Exception) {
            Log.i("cevap", "Hata" + e.localizedMessage)
        }

    }
}