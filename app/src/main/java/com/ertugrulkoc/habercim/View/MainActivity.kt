package com.ertugrulkoc.habercim.View

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ertugrulkoc.habercim.Model.ApiModel.ApiInterface
import com.ertugrulkoc.habercim.Model.ApiModel.Rss
import com.ertugrulkoc.habercim.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val BASE_URL = "https://www.trthaber.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
            val api = retrofit.create(ApiInterface::class.java)
            val call = api.getNews("sondakika_articles.rss")
//            val response = call.execute()
//            val apiModel = response.body()
            call.enqueue(object : Callback<Rss> {
                override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
                    Log.i("cevap", response.body().toString())
                }

                override fun onFailure(call: Call<Rss>, t: Throwable) {
                    Log.i("cevap", "onFailure: "+t.localizedMessage)
                }

            })

        } catch (e: Exception) {
            Log.i("cevap", "Hata" + e.localizedMessage)
        }

    }
}