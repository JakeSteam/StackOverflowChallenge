package uk.co.jakelee.stackoverflowchallenge.model

data class User (
    val id: Int,
    val reputation: Int,
    val created: Long,
    val avatar: String,
    val name: String,
    val badges: List<Badge>
)