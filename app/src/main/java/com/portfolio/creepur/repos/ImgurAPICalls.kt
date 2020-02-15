package com.portfolio.creepur.repos

import com.portfolio.creepur.models.DataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImgurAPICalls {

    @GET("3/account/{accountName}/authorize?")
    fun getAccount(@Path("accountName") accountName: String, @Query("client_id")client_id: String): Call<DataResponse>

    @GET("oauth2/authorize?client_id=YOUR_CLIENT_ID&response_type=REQUESTED_RESPONSE_TYPE&state=APPLICATION_STATE")
    fun getAuth(@Query("client_id") client_id: String, @Query("response_type") responseType: String, @Query("state") state: String): okhttp3.Call
}