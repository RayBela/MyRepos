package org.rayhane.myrepos.view


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button

import com.google.gson.GsonBuilder

import org.rayhane.myrepos.R
import org.rayhane.myrepos.model.GithubService
import org.rayhane.myrepos.model.GithubRepo
import org.rayhane.myrepos.viewmodel.RepoAdapter

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import rx.android.schedulers.AndroidSchedulers
import rx.android.view.ViewObservable
import rx.functions.Action1
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {


    var USER = "RayBela"
    var ENDPOINT = "https://api.github.com"
    var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: Implment mvvm
        //TODO:seperate logic and view

        recyclerView = findViewById(R.id.repos_list) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        val loadReposBtn = findViewById(R.id.loadRepos) as Button
        val clicksObservable = ViewObservable.clicks(loadReposBtn)

        clicksObservable.subscribe { loadRepositories() }

    }

    /**
     *
     */
    fun loadRepositories() {

        val githubAPI = createService().create(GithubService::class.java)

        githubAPI.getRepos(USER)
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(Action1<List<GithubRepo>> { githubRepos ->
                    for (repo in githubRepos) {
                        recyclerView!!.adapter = RepoAdapter(githubRepos, R.layout.list_item_repo)
                    }
                })
    }

    /**
     *
     */
    fun createService(): Retrofit {

        val gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return retrofit
    }
}
