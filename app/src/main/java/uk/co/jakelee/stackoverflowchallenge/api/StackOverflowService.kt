package uk.co.jakelee.stackoverflowchallenge.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import uk.co.jakelee.stackoverflowchallenge.model.UserWrapper

interface StackOverflowService {
    @GET("2.2/users")
    fun getUsers(
        @Query("inname") searchTerm: String,
        @Query("pagesize") results: Int,
        @Query("sort") sort: String = "name",
        @Query("order") order: String = "desc",
        @Query("site") site: String = "stackoverflow"
    ): Observable<UserWrapper>

    companion object {
        var BASIC_URL = "https://api.stackexchange.com/"
    }
}