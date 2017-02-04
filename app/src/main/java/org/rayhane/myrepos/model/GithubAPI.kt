package org.rayhane.myrepos.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Rayhane on 03/02/2017.
 */

interface GithubAPI {

    @GET("/users/{user}")
    fun getUser(@Path("user") user: String): Call<GithubUser>

    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user: String): Call<List<GithubRepo>>

    companion object {

        val ENDPOINT = "https://api.github.com"
    }

}
