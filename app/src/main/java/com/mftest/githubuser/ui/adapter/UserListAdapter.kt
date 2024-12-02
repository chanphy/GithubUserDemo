package com.mftest.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mftest.githubuser.datasourse.remote.entities.UserItem
import com.mftest.githubuser.databinding.FragmentUserItemBinding

class UserListAdapter(private val onUserClick: (UserItem) -> Unit) :
    ListAdapter<UserItem, UserListAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            FragmentUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: FragmentUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItem) {
            binding.userName.text = user.login
            Glide.with(binding.root.context)
                .load(user.avatar_url)
                .circleCrop()
                .into(binding.userAvatar)
            binding.root.setOnClickListener { onUserClick(user) }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<UserItem>() {
        override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
            oldItem == newItem
    }
}
