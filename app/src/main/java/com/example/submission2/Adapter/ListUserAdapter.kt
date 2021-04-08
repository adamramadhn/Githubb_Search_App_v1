package com.example.submission2.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.Model.Item
import com.example.submission2.databinding.ItemListBinding

class ListUserAdapter(private val listUser: List<Item>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {


    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            ItemListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(usr: Item) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(usr.avatar_url)
                    .apply(RequestOptions().override(300, 300))
                    .into(avatarImv)
                usernameTv.text = usr.login

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(usr) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Item)
    }
}