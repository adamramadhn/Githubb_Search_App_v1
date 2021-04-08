package com.example.submission2.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.*
import com.example.submission2.API.GHService
import com.example.submission2.Adapter.ListUserAdapter
import com.example.submission2.Model.Item
import com.example.submission2.Model.Response
import com.example.submission2.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val errorMessage = resources.getString(R.string.er_msg)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bahasaIv.setOnClickListener(this)
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.progressBar.visibility = View.INVISIBLE

        binding.searchEt.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.progressBar.visibility = View.VISIBLE
                val servis = GHService.servis.getUsers(query.toString())
                servis.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(
                        call: Call<Response>,
                        response: retrofit2.Response<Response>
                    ) {
                        val dataResponse: Response = response.body()!!
                        val newList: List<Item> = dataResponse.items
                        val rvAdapter = ListUserAdapter(newList)
                        binding.rvUser.adapter = rvAdapter

                        rvAdapter.setOnItemClickCallback(object :
                            ListUserAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: Item) {
                                val pindah =
                                    Intent(this@MainActivity, DetailUserActivity::class.java)
                                pindah.putExtra("username", data.login)
                                startActivity(pindah)
                            }
                        })


                        binding.progressBar.visibility = View.GONE
                    }
                })

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    override fun onClick(v: View?) {
        val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(mIntent)
    }
}