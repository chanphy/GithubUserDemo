package com.mftest.githubuser.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mftest.githubuser.ui.adapter.UserListAdapter
import com.mftest.githubuser.ui.viewmodel.GithubUserViewModel
import com.mftest.githubuser.databinding.FragmentUserListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [UserListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserListFragment : Fragment() {
    private lateinit var binding: FragmentUserListBinding
    private val viewModel by viewModels<GithubUserViewModel>()
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init adapter
        adapter = UserListAdapter { user ->
            // Move to the UserDetailFragment
            Log.d("UserDetailFragment", "Send username: ${user.login}")
            val action = UserListFragmentDirections.actionToUserDetailFragment(user.login)
            findNavController().navigate(action)
        }

        binding.userList.adapter = adapter
        binding.userList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.userList.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
        }

        binding.searchInput.addTextChangedListener() { query ->
            viewModel.filterUsers(query.toString())
        }

        // Load more users
        binding.userList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMoreUsers()
                }
            }
        })

        // Init Users
        viewModel.loadMoreUsers()
    }
}