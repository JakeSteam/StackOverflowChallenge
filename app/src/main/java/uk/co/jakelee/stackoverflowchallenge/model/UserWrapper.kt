package uk.co.jakelee.stackoverflowchallenge.model

import com.google.gson.annotations.SerializedName

data class UserWrapper(
    @SerializedName("items")
    val users: List<User>
)