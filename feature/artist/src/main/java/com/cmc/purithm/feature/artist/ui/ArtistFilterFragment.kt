package com.cmc.purithm.feature.artist.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.artist.R
import com.cmc.purithm.feature.artist.adapter.ArtistFilterAdapter
import com.cmc.purithm.feature.artist.databinding.FragmentArtistFilterBinding
import com.cmc.purithm.feature.artist.dialog.ArtistFilterLockBottomDialog
import com.cmc.purithm.feature.artist.dialog.ArtistFilterSortBottomDialog
import com.cmc.purithm.feature.artist.viewmodel.ArtistFilterSideEffects
import com.cmc.purithm.feature.artist.viewmodel.ArtistFilterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArtistFilterFragment : BaseFragment<FragmentArtistFilterBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_artist_filter
    private val viewModel: ArtistFilterViewModel by viewModels()
    private val navArgs by navArgs<ArtistFilterFragmentArgs>()
    private val artistDescription by lazy { navArgs.artistDescription }
    private val artistId by lazy { navArgs.artistId }
    private val artistName by lazy { navArgs.artistName }
    private val artistProfile by lazy { navArgs.artistProfile }
    private val artistFilterAdapter by lazy { ArtistFilterAdapter(viewModel) }

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
                        artistFilterAdapter.submitData(state.dataList)
                    }
                }

                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            is ArtistFilterSideEffects.NavigateToFilter -> {
                                (activity as NavigationAction).navigateFilterItem(sideEffect.id)
                            }

                            ArtistFilterSideEffects.ShowArtistFilterLockBottomSheet -> {
                                ArtistFilterLockBottomDialog().show(childFragmentManager, null)
                            }

                            ArtistFilterSideEffects.ShowArtistFilterSortBottomSheet -> {
                                ArtistFilterSortBottomDialog().show(childFragmentManager, null)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {
        with(binding) {
            vm = viewModel
            artistName = this@ArtistFilterFragment.artistName
            artistDescription = this@ArtistFilterFragment.artistDescription
            artistProfile = this@ArtistFilterFragment.artistProfile
        }
    }

    override fun initView() {
        viewModel.initArtistId(artistId)
        viewModel.setPageAdapterLoadStateListener(artistFilterAdapter)
        with(binding) {
            listArtistFilter.adapter = artistFilterAdapter
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_BACK,
                title = "",
                backClickListener = {
                    findNavController().popBackStack()
                }
            )
        }
    }
}