package com.danilatyukov.linkedmoney.service

import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.data.remote.request.StatisticRequestObject
import com.danilatyukov.linkedmoney.data.remote.request.UserRequestObject
import com.danilatyukov.linkedmoney.data.vo.StatisticVO
import com.danilatyukov.linkedmoney.data.vo.UserVO
import io.reactivex.Observable
import okhttp3.OkHttpClient

import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface MessageApiService {
    companion object Factory {
        private var service: MessageApiService? = null
        fun getInstance(): MessageApiService{
            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)


            if (service == null) {
                val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .baseUrl(App.baseUrl)
                    .build()
                service = retrofit.create(MessageApiService::class.java)
            }
            return service as MessageApiService
        }
    }

    @POST("login")
    @Headers("Content-Type: application/json")
    fun login(@Body user: UserRequestObject): Observable<retrofit2.Response<ResponseBody>>

    @POST("users/registrations")
    fun createUser(@Body user: UserRequestObject): Observable<UserVO>

    @GET("users/details")
    fun echoDetails(@Header("Authorization") authorization: String) : Observable<UserVO>

    @POST("statistics/create")
    fun createStatistic(
        @Body statisticRequestObject: StatisticRequestObject,
        @Header ("Authorization") authorization: String?
    ): Observable<UserVO>

    @GET("statistics/last")
    fun lastStatistic (@Header("Authorization") authorization: String): Observable<StatisticVO>


}