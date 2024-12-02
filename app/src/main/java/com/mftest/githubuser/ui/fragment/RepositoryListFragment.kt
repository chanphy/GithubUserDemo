package com.mftest.githubuser.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mftest.githubuser.databinding.FragmentRepositoryListBinding
import com.mftest.githubuser.ui.adapter.RepositoryListAdapter
import com.mftest.githubuser.ui.viewmodel.GithubUserRepositoryViewModel

/**
 * A fragment representing a list of Items.
 */
class RepositoryListFragment : Fragment() {
    private val args: RepositoryListFragmentArgs by navArgs() // Safe Args Auto-generate
    private lateinit var binding: FragmentRepositoryListBinding
    private val viewModel by viewModels<GithubUserRepositoryViewModel>()
    private lateinit var adapter: RepositoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the username param from UserListFragment.
        Log.d("UserDetailFragment", "Get username: ${args.username}")
        val username = args.username

        adapter = RepositoryListAdapter { repo ->
            // Move to the WebView
            val action =
                RepositoryListFragmentDirections.actionToRepositoryWebViewFragment(repo.html_url)
            findNavController().navigate(action)
        }
        binding.repoList.adapter = adapter
        binding.repoList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.userDetail.observe(viewLifecycleOwner) { user ->
            Glide.with(this)
                .load(user.avatar_url)
                .circleCrop()
                .into(binding.userAvatar)
            binding.userName.text = user.login.toString()
            binding.fullName.text = "(${user.name})"
            binding.followersAndFollowing.text =
                "${user.followers} Followers | ${user.following} Following"
        }

        viewModel.repositories.observe(viewLifecycleOwner) { repos ->
            adapter.submitList(repos)
        }

        // Load more users
        binding.repoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadUserRepositories(username)
                }
            }
        })

        viewModel.loadUserDetails(username)
        viewModel.loadUserRepositories(username)
    }
}