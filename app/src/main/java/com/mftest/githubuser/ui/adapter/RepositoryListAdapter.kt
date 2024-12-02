package com.mftest.githubuser.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.mftest.githubuser.databinding.FragmentRepositoryItemBinding
import com.mftest.githubuser.datasourse.remote.entities.RepositoryItem

class RepositoryListAdapter(
    private val onClick: (RepositoryItem) -> Unit
) : ListAdapter<RepositoryItem, RepositoryListAdapter.RepositoryViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = FragmentRepositoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RepositoryViewHolder(private val binding: FragmentRepositoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(repo: RepositoryItem) {
            binding.repoName.text = repo.name
            binding.repoLanguage.text = repo.language
            binding.repoStars.text = "★ ${repo.stargazers_count}"
            binding.repoDescription.text = repo.description
            itemView.setOnClickListener { onClick(repo) }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<RepositoryItem>() {
        override fun areItemsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem) =
            oldItem == newItem
    }

}