package com.suitmedia.suitmediatest.ui.user.thirdscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.suitmedia.suitmediatest.data.remote.response.UserDetail
import com.suitmedia.suitmediatest.databinding.ItemUserBinding

class UserListAdapter(
    private val fragment: ThirdScreenFragment
)
    : PagingDataAdapter<UserDetail, UserListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener {
                fragment.saveSelected(data.firstName + " " + data.lastName)
            }
        }
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserDetail) {
            val fullName = data.firstName + " " + data.lastName

            binding.fullNameTextView.text = fullName
            binding.emailTextView.text = data.email
            Glide.with(binding.root)
                .load(data.avatar)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.profileImage)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserDetail>() {
            override fun areItemsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}