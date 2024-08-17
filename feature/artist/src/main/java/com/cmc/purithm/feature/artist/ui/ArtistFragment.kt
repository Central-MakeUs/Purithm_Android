package com.cmc.purithm.feature.artist.ui

import android.util.Log
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.artist.R
import com.cmc.purithm.feature.artist.adapter.ArtistAdapter
import com.cmc.purithm.feature.artist.databinding.FragmentArtistBinding
import com.cmc.purithm.feature.artist.dialog.ArtistSortBottomDialog
import com.cmc.purithm.feature.artist.viewmodel.ArtistSideEffects
import com.cmc.purithm.feature.artist.viewmodel.ArtistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArtistFragment : BaseFragment<FragmentArtistBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_artist

    private val viewModel: ArtistViewModel by viewModels()
    private val artistAdapter: ArtistAdapter by lazy {
        ArtistAdapter(viewModel)
    }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        if (state.loading) {
                            showLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                        }
                        if (state.error != null) {
                            CommonDialogFragment.showDialog(
                                content = getString(com.cmc.purithm.design.R.string.error_common),
                                positiveText = getString(com.cmc.purithm.design.R.string.content_confirm),
                                positiveClickEvent = {
                                    requireActivity().finish()
                                },
                                fragmentManager = childFragmentManager
                            )
                        }
                        if(state.data != artistAdapter.currentList){
                            Log.d(TAG, "initObserving: update")
                            // 리스트가 다를때만 업데이트 하도록 변경
                            artistAdapter.submitList(state.data) {
                                binding.listArtist.smoothScrollToPosition(0)
                            }
                        }
                    }
                }
                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            is ArtistSideEffects.NavigateArtistFilter -> {
                                navigate(
                                    ArtistFragmentDirections.actionArtistFragmentToArtistFilterFragment(
                                        sideEffect.artistId
                                    )
                                )
                            }

                            ArtistSideEffects.ShowArtistFilterBottomSheet -> {
                                ArtistSortBottomDialog().show(
                                    childFragmentManager, null
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {
        binding.vm = viewModel
    }

    private fun setBackButtonEvent(){
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            CommonDialogFragment.showDialog(
                content = "앱을 종료하시겠습니까?",
                negativeText = getString(com.cmc.purithm.design.R.string.content_cancel),
                negativeClickEvent = {
                    CommonDialogFragment.dismissDialog()
                },
                positiveText = "종료",
                positiveClickEvent = {
                    CommonDialogFragment.dismissDialog()
                    requireActivity().finish()
                },
                fragmentManager = childFragmentManager
            )
        }
    }

    override fun initView() {
        setBackButtonEvent()
        with(binding) {
            listArtist.adapter = artistAdapter
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.ENG_TEXT,
                title = "Artists"
            )
        }
    }

    companion object{
        private const val TAG = "ArtistFragment"
    }
}