package com.portfolio.creepur.models

import com.google.gson.annotations.SerializedName


data class Data (@SerializedName("url") val username: String? = null, var bio: String? = null,
                 var reputation: Int? = null, var reputation_name: String? = null, var id: Int? = null,
                 var pro_expiration: Boolean? = null, var created: Long? = null, var avatar: String? = null)