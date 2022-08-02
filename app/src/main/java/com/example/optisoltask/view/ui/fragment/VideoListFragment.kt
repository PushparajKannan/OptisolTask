package com.example.optisoltask.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.optisoltask.R
import com.example.optisoltask.databinding.FragmentVideoListBinding
import com.example.optisoltask.view.adapter.VideosListAdapter
import com.example.optisoltask.view.ui.activity.MainActivity
import com.example.optisoltask.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoListFragment : Fragment() {

    /**
     * PARENT ACTIVITY CONTEXT
     **/
    private val activityContext: MainActivity by lazy {
        val activity = requireNotNull(activity)
        (activity as MainActivity)
    }

    /**
     * SHARED VIEW_MODEL LOGIC
     **/
    private val viewModel: MainActivityViewModel by lazy {
        activityContext.viewModel
    }

    private val adapter: VideosListAdapter by lazy {
        VideosListAdapter()
    }

    private lateinit var binding: FragmentVideoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvVideoList.adapter = adapter

        fetchFromServer()
    }

    private fun fetchFromServer() {
        lifecycleScope.launch {
            viewModel.fetchVideosPaging().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}