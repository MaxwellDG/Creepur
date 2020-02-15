package com.portfolio.creepur.models

import java.io.Serializable

data class UserAccountSignedIn( var acccessToken: String? = null,
                                var expiryDate: String? = null,
                                var tokenType: String? = null,
                                var refreshToken: String? = null,
                                var userName: String? = null,
                                var accountId: String? = null,
                                var bookmarks: ArrayList<Bookmark>? = null) : Serializable