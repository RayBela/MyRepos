package org.rayhane.myrepos.model

/**
 * Created by Rayhane on 03/02/2017.
 */

class GithubUser(var name: String, var email: String) {

    override fun toString(): String {
        return name + "" + email
    }


}
