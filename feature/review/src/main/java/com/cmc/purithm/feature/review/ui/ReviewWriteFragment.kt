package com.cmc.purithm.feature.review.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.design.util.Util.dp
import com.cmc.purithm.feature.review.R
import com.cmc.purithm.feature.review.databinding.FragmentReviewWriteBinding
import com.cmc.purithm.feature.review.dialog.ReviewGuideBottomDialog
import com.cmc.purithm.feature.review.dialog.ReviewSuccessDialog
import com.cmc.purithm.feature.review.view.ReviewPictureView
import com.cmc.purithm.feature.review.viewmodel.ReviewWriteSideEffect
import com.cmc.purithm.feature.review.viewmodel.ReviewWriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class ReviewWriteFragment : BaseFragment<FragmentReviewWriteBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_review_write
    private var isKeyBoardVisible = false
    private var lastHeightDiff = 0
    private val navArgs by navArgs<ReviewWriteFragmentArgs>()
    private val filterId by lazy { navArgs.filterId }
    private val thumbnail by lazy { navArgs.thumbnail }
    private val filterName by lazy { navArgs.filterName }
    private val navigateType by lazy { navArgs.navigateType }
    private val viewModel by viewModels<ReviewWriteViewModel>()
    private val registeredPictureList = mutableListOf<String>()
    private val viewTreeObserver = ViewTreeObserver.OnGlobalLayoutListener {
        val rootView = requireActivity().window.decorView.findViewById<View>(android.R.id.content)
        Log.d(TAG, "add: on!")
        val heightDiff = rootView.rootView.height - rootView.height
        if(lastHeightDiff == 0){
            Log.d(TAG, "add: diff setting")
            Log.d(TAG, "add: value = $heightDiff")
            lastHeightDiff = heightDiff
        }

        if(heightDiff > lastHeightDiff){
            Log.d(TAG, "add: keypad on")
            isKeyBoardVisible = true
        } else {
            if(isKeyBoardVisible){
                isKeyBoardVisible = false
                Log.d(TAG, "add: keypad down")
                if(binding.editReview.getFocus()){
                    Log.d(TAG, "add: edit text focus on")
                    binding.editReview.removeFocus()
                    val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.editReview.windowToken, 0)
                }
            }
        }
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
                        state.pictures.forEachIndexed { index, s ->
                            // 이미 추가된 리스트는 더 추가하지 않음
                            if (registeredPictureList.contains(s)) {
                                return@forEachIndexed
                            }
                            registeredPictureList.add(s)
                            val newView = ReviewPictureView(requireContext()).apply {
                                layoutParams = LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                ).apply {
                                    marginEnd = 20.dp
                                }
                                setInfo(
                                    url = s,
                                    index = index,
                                    deleteEvent = {
                                        registeredPictureList.remove(s)
                                        viewModel.deleteImgUrl(s)
                                        binding.layoutImg.removeView(this)
                                    })
                            }
                            binding.layoutImg.addView(newView, 0)
                        }
                    }
                }

                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            ReviewWriteSideEffect.StartGallery -> {
                                startActivityForResult(Intent(Intent.ACTION_PICK).apply {
                                    type = "image/*"
                                }, REQUEST_IMG_CODE)
                            }

                            is ReviewWriteSideEffect.ShowReviewSuccessDialog -> ReviewSuccessDialog(
                                afterDelayEvent = {
                                    navigate(
                                        ReviewWriteFragmentDirections.actionReviewWriteFragmentToReviewHistoryFragment(
                                            navigateType = navigateType,
                                            isWrite = true,
                                            reviewId = sideEffect.reviewId,
                                            filterId = filterId,
                                            thumbnail = thumbnail,
                                            filterName = filterName
                                        )
                                    )
                                }
                            ).show(childFragmentManager, null)
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
        requireActivity().window.decorView.viewTreeObserver.addOnGlobalLayoutListener(viewTreeObserver)
        viewModel.setInitInfo(filterId, thumbnail)
        with(binding) {
            btnTermOfServiceAgreement.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setAgree(isChecked)
            }
            btnTermOfServiceIntroduceAgreement.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setIntroduceAgree(isChecked)
            }
            seekbarReview.setChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    viewModel.setPureDegree(progress * 20)
                    binding.editReview.removeFocus()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
            editReview.initView(
                maxSize = TEXT_MAX_SIZE,
                minSize = TEXT_MIN_SIZE,
                hint = String.format(getString(R.string.content_review_write_require_text), TEXT_MIN_SIZE),
                imeOption = EditorInfo.IME_ACTION_DONE,
                errorMsg = String.format(
                    getString(R.string.content_review_write_require_text),
                    TEXT_MIN_SIZE
                ),
                textChangeListener = { content ->
                    viewModel.setContent(content)
                }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMG_CODE) {
            data?.data?.let {
                val file = getFileFromUri(it)
                viewModel.requestUploadUrl(file ?: return)
            }
        }
    }

    private fun getFileFromUri(uri: Uri): File? {
        val fileName = getFileName(uri) ?: return null
        val file = File(requireContext().cacheDir, fileName)

        try {
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            return file
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            requireContext().contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex)
                    }
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                if (cut != null) {
                    result = result?.substring(cut + 1)
                }
            }
        }
        return result
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(viewTreeObserver)
    }

    companion object {
        private const val TAG = "ReviewWriteFragment"
        private const val TEXT_MAX_SIZE = 100
        private const val TEXT_MIN_SIZE = 20
        private const val REQUEST_IMG_CODE = 1
    }
}