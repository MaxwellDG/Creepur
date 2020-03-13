package com.portfolio.creepur.repos

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.portfolio.creepur.R
import com.portfolio.creepur.core.MainApplication
import com.portfolio.creepur.models.DataResponse
import com.portfolio.creepur.models.Data
import com.portfolio.creepur.models.DataImages
import com.portfolio.creepur.models.DataResponseImages
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private const val BASE_URL: String = "https://api.imgur.com/"
    private var client = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    private var apiCaller: ImgurAPICalls = client.create(ImgurAPICalls::class.java)

    private val userData: MutableLiveData<Data> = MutableLiveData()
    private val userImages: MutableLiveData<ArrayList<DataImages>> = MutableLiveData()



    fun getUserData(): LiveData<Data>{
        return this.userData
    }

    fun callForAnAccount(account: String, context: Context){
        val call: Call<DataResponse> = apiCaller.getAccount(account, context.resources.getString(R.string.client_id))
        call.enqueue(object: Callback<DataResponse> {
            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Log.d( "TAG","There was an error with the API call: ${t.message} ${t.localizedMessage} ${t.cause} ")
            }
            override fun onResponse(call: Call<DataResponse>, response: retrofit2.Response<DataResponse>) {
                if(!response.isSuccessful){
                    Log.d("TAG", "API callback returned an error: ${response.code()} + ${response.message()}")
                } else {
                    val dataResponse: DataResponse? = response.body()
                    userData.postValue( dataResponse?.data )
                }
            }
        })
    }

    fun getUserImages(): LiveData<ArrayList<DataImages>>{
        return this.userImages
    }

    fun callForAnAccountsImages(account: String, context: Context){
        val call: Call<DataResponseImages> = apiCaller.getImages(account, 0, context.resources.getString(R.string.client_id))
        call.enqueue(object: Callback<DataResponseImages> {
            override fun onFailure(call: Call<DataResponseImages>, t: Throwable) {
                Log.d( "TAG","There was an error with the API call: ${t.message} ${t.localizedMessage} ${t.cause} ")
            }
            override fun onResponse(call: Call<DataResponseImages>, response: Response<DataResponseImages>) {
                val dataResponse: DataResponseImages? = response.body()
                if (dataResponse?.data != null && dataResponse.data != null){
                    val list: ArrayList<DataImages> = arrayListOf()
                    dataResponse.data!!.forEach { thing ->
                        val fullImage = DataImages(thing!!.account_url, thing.title, thing.link, thing.ups, thing.downs)
                        list.add(fullImage)
                    }
                    userImages.postValue(list)
                }
            }
        })
    }

    fun callForAuth(username: String, context: Context){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.imgur.com/oauth2/authorize?client_id=019800706a93797&response_type=token"))
        context.startActivity(intent)
        }

    fun callForNewAccessTokenWithRefreshToken(){
        // Do this function when youre done with the MVP. This needs to be done though when each user's one-month-valid accessToken expires. Set some kind of warning flag when the expires_in
        // time has finished and automatically call this function to get them a new one. Then redo the whole setup for them
    }
}