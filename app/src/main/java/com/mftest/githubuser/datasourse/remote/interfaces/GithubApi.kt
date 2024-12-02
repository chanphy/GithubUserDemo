package com.mftest.githubuser.datasourse.remote.interfaces

import com.mftest.githubuser.datasourse.remote.entities.RepositoryItem
import com.mftest.githubuser.datasourse.remote.entities.RepositoryResponse
import com.mftest.githubuser.datasourse.remote.entities.UserResponse
import com.mftest.githubuser.datasourse.remote.entities.UserDetail
import com.mftest.githubuser.datasourse.remote.entities.UserItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    // https://api.github.com/users?since=1000&per_page=5
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<UserItem>

    // https://api.github.com/search/users?q=abc&since=10000000&per_page=5
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<UserItem>

    // https://api.github.com/users/mojombo
    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserDetail

    // https://api.github.com/users/mojombo/repos
    @GET("users/{username}/repos")
    suspend fun getRepositories(@Path("username") username: String): List<RepositoryItem>
}
