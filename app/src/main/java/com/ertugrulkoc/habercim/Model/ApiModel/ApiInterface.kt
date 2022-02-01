package com.ertugrulkoc.habercim.Model.ApiModel

import com.ertugrulkoc.habercim.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {

    @GET
    fun getNews(@Url url: String):Call<Root>
}