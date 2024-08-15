package com.cmc.purithm.feature.artist.ui

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
                    viewModel.state.collectLatest { state ->
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
                        artistAdapter.submitList(state.data)
                        binding.listArtist.scrollToPosition(0)
                    }
                }
                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            is ArtistSideEffects.NavigateArtistFilter -> {
                                navigate(
                                    ArtistFragmentDirections.actionArtistFragmentToArtistFilterFragment(
                                        sideEffect.artistId,
                                        sideEffect.artistName,
                                        sideEffect.artistProfile,
                                        sideEffect.description
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

    override fun initView() {
        with(binding) {
            listArtist.adapter = artistAdapter
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.ENG_TEXT,
                title = "Artists"
            )
        }
    }
}