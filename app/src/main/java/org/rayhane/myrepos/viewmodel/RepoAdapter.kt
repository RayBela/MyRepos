package org.rayhane.myrepos.viewmodel

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.rayhane.myrepos.R
import org.rayhane.myrepos.model.GithubRepo

/**
 * Created by Rayhane on 04/02/2017.
 * RecyclerView Adapter
 */

class RepoAdapter(private val repos: List<GithubRepo>,
                  private val rowLayout: Int) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    //TODO:implment data bindings here

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        internal var reposLayout: LinearLayout
        internal var repoName: TextView
        internal var repoDescription: TextView


        init {
            reposLayout = itemView.findViewById(R.id.repos_layout) as LinearLayout
            repoName = itemView.findViewById(R.id.repo_name) as TextView
            repoDescription = itemView.findViewById(R.id.repo_description) as TextView
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {

        holder.repoName.text = repos[position].name
        holder.repoDescription.text = repos[position].description

    }

    override fun getItemCount(): Int { return repos.size }

}
