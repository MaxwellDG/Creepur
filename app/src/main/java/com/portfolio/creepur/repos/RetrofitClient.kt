package com.portfolio.creepur.repos

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firebase.ui.auth.data.model.User
import com.portfolio.creepur.models.DataResponse
import com.portfolio.creepur.models.Data
import com.portfolio.creepur.models.UserAccountSignedIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Due to the size of the project, the repo and the Retrofit client are the same class //

object RetrofitClient {

    private const val BASE_URL: String = "https://api.imgur.com/"
    private var client = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    private var apiCaller: ImgurAPICalls = client.create(ImgurAPICalls::class.java)

    private val userData: MutableLiveData<Data> = MutableLiveData()



    fun getUserData(): LiveData<Data>{
        return this.userData
    }

    fun callForAnAccount(account: String, context: Context){
        val call: Call<DataResponse> = apiCaller.getAccount(account, "019800706a93797") // TODO: this needs to be hidden and accessed in an annoying way
        call.enqueue(object: Callback<DataResponse> {
            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Log.d( "TAG","There was an error with the API call: ${t.message} ${t.localizedMessage} ${t.cause} ")
                Toast.makeText(context, "There was an error with the API call", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<DataResponse>, response: retrofit2.Response<DataResponse>) {
                if(!response.isSuccessful){
                    Log.d("TAG", "API callback returned an error: ${response.code()} + ${response.message()}")
                    Toast.makeText(context, "API callback returned an error: ${response.code()} + ${response.message()}", Toast.LENGTH_SHORT).show()
                } else {
                    val dataResponse: DataResponse? = response.body()
                    userData.value = dataResponse?.data
                    Log.d("TAG", "Do we have access to the reputation number?: ${dataResponse?.data?.reputation.toString()}")
                    Log.d( "TAG","userData has: ${userData.value?.reputation.toString()}")
                }
            }
        })
    }

    fun callForAuth(username: String, context: Context){
        val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.imgur.com/oauth2/authorize?client_id=019800706a93797&response_type=token"))
        context.startActivity(intent)
        }

    fun callForNewAccessTokenWithRefreshToken(){
        // Do this function when youre done with the MVP. This needs to be done though when each user's one-month-valid accessToken expires. Set some kind of warning flag when the expires_in
        // time has finished and automatically call this function to get them a new one. Then redo the whole setup for them
    }
}