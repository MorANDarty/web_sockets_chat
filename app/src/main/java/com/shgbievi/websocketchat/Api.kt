package com.shgbievi.websocketchat

import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query


interface Api {

    @POST("/login")
    fun login(
        @Query("username") username:String,
        @Query("device_id") deviceId:String)
    :Single<LoginResp>

}
