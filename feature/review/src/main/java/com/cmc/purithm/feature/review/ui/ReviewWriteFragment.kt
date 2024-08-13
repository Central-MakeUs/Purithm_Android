package com.cmc.purithm.feature.review.ui

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.review.R
import com.cmc.purithm.feature.review.databinding.FragmentReviewWriteBinding
import com.cmc.purithm.feature.review.dialog.ReviewGuideBottomDialog
import com.cmc.purithm.feature.review.viewmodel.ReviewWriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewWriteFragment : BaseFragment<FragmentReviewWriteBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_review_write
    private val navArgs by navArgs<ReviewWriteFragmentArgs>()
    private val filterId by lazy { navArgs.filterId }
    private val viewModel by viewModels<ReviewWriteViewModel>()

    override fun initObserving() {

    }

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun initView() {
        with(binding) {
            editReview.initView(
                maxSize = 10,
                minSize = 1,
                hint = getString(R.string.content_review_hint),
                imeOption = EditorInfo.IME_ACTION_DONE,
                errorMsg = getString(R.string.content_review_write_require_text)
            )
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_DEFAULT,
                title = "",
                backClickListener = {
                    findNavController().popBackStack()
                },
                questionClickListener = {
                    ReviewGuideBottomDialog().show(
                        childFragmentManager,
                        null
                    )
                }
            )
        }
    }
}