package org.rayhane.myrepos.view


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import org.rayhane.myrepos.R
import org.rayhane.myrepos.model.GithubAPI
import org.rayhane.myrepos.model.GithubRepo
import org.rayhane.myrepos.model.GithubUser
import org.rayhane.myrepos.viewmodel.RepoAdapter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.android.view.OnClickEvent
import rx.android.view.ViewObservable
import rx.functions.Action1
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    internal var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: Implment mvvm

        recyclerView = findViewById(R.id.repos_list) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        val loadReposBtn = findViewById(R.id.loadRepos) as Button
        val clicksObservable = ViewObservable.clicks(loadReposBtn)

        clicksObservable
                .subscribe {
                    Log.i("Click Action", "Clicked!")
                    loadRepositories()
                }

    }

    //TODO:seperate calls logic
    fun loadRepositories() {

        val gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
                .baseUrl(GithubAPI.ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val githubUserAPI = retrofit.create(GithubAPI::class.java)

        githubUserAPI.getRepos("RayBela")
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(Action1<List<GithubRepo>> { githubRepos ->
                    for (repo in githubRepos) {
                        recyclerView!!.adapter = RepoAdapter(githubRepos, R.layout.list_item_repo, applicationContext)
                    }

                })
    }
}
