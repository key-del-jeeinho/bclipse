package com.bclipse.monolith.application.user.util

object UserPermission {
    fun isAdmin(userId: String): Boolean {
        return userId == "raul0411"
    }
}