package com.mftest.githubuser.ui.viewmodel

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mftest.githubuser.datasourse.remote.GithubApiImpl
import com.mftest.githubuser.datasourse.remote.entities.UserItem
import kotlinx.coroutines.launch

class GithubUserViewModel : ViewModel() {
    private val _userList = MutableLiveData<List<UserItem>>()
    val userList: LiveData<List<UserItem>> = _userList

    private val githubApiImpl = GithubApiImpl()
    private var curPage = 0
    private val perPage = 10
    private var allUsers = mutableListOf<UserItem>()

    fun loadMoreUsers() {
        viewModelScope.launch {
            val newUsers = githubApiImpl.fetchUsers(curPage * perPage, perPage)
            allUsers.addAll(newUsers)
            curPage++
            _userList.value = allUsers
        }
    }

    // Filter users locally
    fun filterUsers(query: String) {
        _userList.value = if (query.isEmpty()) {
            allUsers
        } else {
            allUsers.filter { it.login.contains(query, ignoreCase = true) }
        }
    }

    // Online Search
    fun searchUsers(query: String) {
        viewModelScope.launch {
            try {
                val users = githubApiImpl.searchUsers(query, curPage * perPage, perPage)
                allUsers.addAll(users)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}