package org.rayhane.myrepos.model

/**
 * Created by Rayhane on 03/02/2017.
 */

class GithubRepo(var name: String, var url: String, var description: String) {

    override fun toString(): String {
        return name + " " + url + "" + description
    }


}
