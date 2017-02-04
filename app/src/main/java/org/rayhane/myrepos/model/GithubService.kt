package org.rayhane.myrepos.model

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable


/**
 * Created by Rayhane on 04/02/2017.
 */
interface GithubService {

    @GET("users/{username}/repos")
    fun publicRepositories(@Path("username") username: String): Observable<List<GithubRepo>>

}