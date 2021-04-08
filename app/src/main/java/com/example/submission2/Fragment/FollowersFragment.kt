package com.example.submission2.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.Activity.DetailUserActivity
import com.example.submission2.API.GHService
import com.example.submission2.Model.Item
import com.example.submission2.Adapter.ListUserAdapter
import com.example.submission2.databinding.FragmentFollowersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        binding.rvFollowers.layoutManager = LinearLayoutManager(context)
        binding.progressBar.visibility = View.VISIBLE

        GHService.servis.getFollowers(DetailUserActivity.DataUsername.username.toString()).enqueue(
            object : Callback<List<Item>> {

                override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.INVISIBLE
                }

                override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                    val list = response.body()
                    binding.rvFollowers.adapter = list?.let { ListUserAdapter(it) }
                    binding.progressBar.visibility = View.INVISIBLE
                }
            })
        return binding.root
    }


}