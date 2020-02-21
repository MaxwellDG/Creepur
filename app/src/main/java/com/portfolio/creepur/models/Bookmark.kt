package com.portfolio.creepur.models

import java.io.Serializable

data class Bookmark(var username: String = "", var reputation: Int = 0, var reputationName: String = "", var avatar: String = "", var pro: Boolean = false): Serializable