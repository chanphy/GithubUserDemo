package com.mftest.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mftest.githubuser.datasourse.remote.GithubApiImpl
import com.mftest.githubuser.datasourse.remote.entities.RepositoryItem
import com.mftest.githubuser.datasourse.remote.entities.UserDetail
import kotlinx.coroutines.launch

class GithubUserRepositoryViewModel : ViewModel() {
    private val githubApiImpl = GithubApiImpl()
    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    private val _repositories = MutableLiveData<List<RepositoryItem>>()
    val repositories: LiveData<List<RepositoryItem>> = _repositories
    private var curPage = 0
    private val perPage = 10
    private var allRepositories = mutableListOf<RepositoryItem>()

    fun loadUserDetails(username: String) {
        viewModelScope.launch {
            try {
                val user = githubApiImpl.getUserDetail(username)
                _userDetail.value = user
            } catch (e: Exception) {
                // TODO
            }
        }
    }

    fun loadUserRepositories(username: String) {
        viewModelScope.launch {
            try {
                val repos = githubApiImpl.getRepositories(username)
                    .filter { !it.fork } // Filter off the forked repositories
                allRepositories.addAll(repos)
                curPage++
                _repositories.value = allRepositories
            } catch (e: Exception) {
                Log.e("loadUserRepositories", "exception: $e")
                // TODO
            }
        }
    }

}