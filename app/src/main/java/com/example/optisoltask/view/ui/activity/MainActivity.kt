package com.example.optisoltask.view.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.optisoltask.R
import com.example.optisoltask.databinding.ActivityMainBinding
import com.example.optisoltask.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    internal val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController



        binding.btnFeeds.setOnClickListener {
            navController.navigate(
                R.id.roomListFragment,
                null,
                NavOptions.Builder().setLaunchSingleTop(true).build()
            )
        }

        binding.btnVideos.setOnClickListener {
            navController.navigate(
                R.id.videoListFragment,
                null,
                NavOptions.Builder().setLaunchSingleTop(true).build()
            )
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.videoListFragment -> {
                    viewModel.isVideoList.value = true
                }
                R.id.roomListFragment -> {
                    viewModel.isVideoList.value = false
                }
            }

        }

    }

    override fun onBackPressed() {
        if (Navigation.findNavController(this, R.id.nav_host_fragment)
                .currentDestination!!.id == R.id.videoListFragment
        ) {
            finish()
            return
        }
        super.onBackPressed()
    }
}