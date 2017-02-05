package org.rayhane.myrepos.view


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
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
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), Callback<GithubUser> {

    internal var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.repos_list) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this)

    }

    fun onClick(view: View) {
        val gson = GsonBuilder()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(GithubAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val githubUserAPI = retrofit.create(GithubAPI::class.java)

        val callRepos = githubUserAPI.getRepos("RayBela")
        //asynchronous call
        callRepos.enqueue(repos as Callback<List<GithubRepo>>?)

    }


    internal var repos: Callback<*> = object : Callback<List<GithubRepo>> {
        override fun onResponse(call: Call<List<GithubRepo>>, response: Response<List<GithubRepo>>) {
            if (response.isSuccessful) {
                val repos = response.body()
                recyclerView!!.adapter = RepoAdapter(repos, R.layout.list_item_repo, applicationContext)
            } else {
                Toast.makeText(this@MainActivity, "Error code " + response.code(), Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
            Toast.makeText(this@MainActivity, "Did not work " + t.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
        val code = response.code()
        if (code == 200) {
            val user = response.body()
            Log.i("User call:", "Got the user: " + user.email)
        } else {
            Toast.makeText(this, "Did not work: " + code.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onFailure(call: Call<GithubUser>, t: Throwable) {
        Toast.makeText(this, "Nope", Toast.LENGTH_LONG).show()

    }
}


