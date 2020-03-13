package com.portfolio.creepur.repos

import com.portfolio.creepur.models.DataResponse
import com.portfolio.creepur.models.DataResponseImages
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImgurAPICalls {

    // get account information of any account
    @GET("3/account/{accountName}/authorize?")
    fun getAccount(@Path("accountName") accountName: String, @Query("client_id")client_id: String): Call<DataResponse>

    //authorize a login
    @GET("oauth2/authorize?client_id=YOUR_CLIENT_ID&response_type=REQUESTED_RESPONSE_TYPE&state=APPLICATION_STATE")
    fun getAuth(@Query("client_id") client_id: String, @Query("response_type") responseType: String, @Query("state") state: String): okhttp3.Call

    //get most recent Images posted to a gallery
    @GET("3/account/{username}/submissions/{page}/authorize?")
    fun getImages(@Path("username") username: String, @Path("page")page: Int, @Query("client_id")client_id: String): Call<DataResponseImages>



    //To-Do: Finish the 2 below
    // get most recent favorites
    @GET("/3/account/{username}/favorites/{page}")
    fun getFavourites(@Path("username") username: String, @Path("page") page: Int, @Query("accessToken") accessToken: String)

    // get best comments made by user
    @GET("3/account/{username}/comments/best/0")
    fun getComments(@Path("username") username: String, @Query("accessToken") accessToken: String)
}