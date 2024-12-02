package com.mftest.githubuser.datasourse.remote

import com.mftest.githubuser.datasourse.remote.entities.RepositoryItem
import com.mftest.githubuser.datasourse.remote.entities.UserDetail
import com.mftest.githubuser.datasourse.remote.entities.UserItem
import com.mftest.githubuser.datasourse.remote.utils.RetrofitInstance

class GithubApiImpl {
    private val api = RetrofitInstance.api

    suspend fun fetchUsers(since: Int, perPage: Int): List<UserItem> {
        val response = api.getUsers(since, perPage)
        return response
    }

    suspend fun searchUsers(query: String, since: Int, perPage: Int): List<UserItem> {
        val response = api.searchUsers(query, since, perPage)
        return response
    }

    suspend fun getUserDetail(username: String): UserDetail {
        val response = api.getUserDetail(username)
        return response
    }

    suspend fun getRepositories(username: String): List<RepositoryItem> {
        val response = api.getRepositories(username)
        return response
    }

}