package com.example.submission2.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import coil.load
import coil.transform.CircleCropTransformation
import com.example.submission2.API.GHService
import com.example.submission2.Adapter.PagerAdapter
import com.example.submission2.R
import com.example.submission2.Model.ResponseDetail
import com.example.submission2.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailUserBinding

    companion object {

        @StringRes

        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val errorMessage = resources.getString(R.string.er_msg)
        binding.bahasaIv.setOnClickListener(this)
        binding.backBtn.setOnClickListener(this)
        binding.shareBtn.setOnClickListener(this)
        binding.progressBar.visibility = View.GONE

        val username = intent.extras?.get("username")
        DataUsername.username = username.toString()
        val servis = GHService.servis.getDetail(username as String)

        servis.enqueue(object : Callback<ResponseDetail> {

            override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                Toast.makeText(this@DetailUserActivity, errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<ResponseDetail>,
                response: retrofit2.Response<ResponseDetail>
            ) {

                val dataResponse: ResponseDetail = response.body()!!
                binding.avatar.load(dataResponse.avatar_url) {
                    transformations(CircleCropTransformation())
                }
                binding.tvname.text = dataResponse.name
                binding.jfollowerTv.text = dataResponse.followers.toString()
                binding.jfollowingTv.text = dataResponse.following.toString()
                binding.nameTv.text = dataResponse.login
                binding.locationTv.text = dataResponse.location
                binding.companyTv.text = dataResponse.company
            }

        })

        val sectionsPagerAdapter = PagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    object DataUsername {
        var username: String? = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back_btn -> {
                binding.progressBar.visibility = View.VISIBLE
                finish()
            }
            R.id.bahasa_iv -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.share_btn -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Github User Detail\nName: ${binding.tvname.text}\nUsername: ${binding.nameTv.text}\nLocation: ${binding.locationTv.text}\nWork at: ${binding.companyTv.text}"
                )
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
        }
    }
}