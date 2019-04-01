package uk.co.jakelee.stackoverflowchallenge.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("user_id")
    val id: Int,

    @SerializedName("reputation")
    val reputation: Int,

    @SerializedName("creation_date")
    val created: Long,

    @SerializedName("profile_image")
    val avatar: String,

    @SerializedName("display_name")
    val name: String
    //val badges: List<Badge>
)