package uk.co.jakelee.stackoverflowchallenge.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import uk.co.jakelee.stackoverflowchallenge.model.UserWrapper

interface StackOverflowService {
    @GET("2.2/users")
    fun getUsers(
        @Query("inname") searchTerm: String,
        @Query("max") max: Int = 20,
        @Query("sort") sort: String = "name",
        @Query("order") order: String = "desc",
        @Query("site") site: String = "stackoverflow",
        @Header("key") key: String = ""
    ): Observable<UserWrapper>
}