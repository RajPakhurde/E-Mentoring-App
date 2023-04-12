package com.rajpakhurde.e_mentoringapp.data

class DataClass {
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var userMode: String? = null
    var profileImage: String? = null

    constructor(name: String?, email: String?, password: String?, profileImage: String?, userMode: String?) {
        this.name = name
        this.email = email
        this.password = password
        this.userMode = userMode
        this.profileImage = profileImage

    }

    constructor() {

    }
}