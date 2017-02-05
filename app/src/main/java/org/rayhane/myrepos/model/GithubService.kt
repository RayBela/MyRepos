package org.rayhane.myrepos.model


import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable



/**
 * Created by Rayhane on 03/02/2017.
 * API calls To Github Service
 */

interface GithubService {

    @GET("/users/{user}")
    fun getUser(@Path("user") user: String): Observable<GithubUser>

    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user: String): Observable<List<GithubRepo>>

}

