package com.cmc.purithm.feature.mypage.ui

import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.bindingAdapters.ImageBindingAdapters.setProfile
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.FragmentEditProfileBinding
import com.cmc.purithm.feature.mypage.dialog.SetProfileImgDialog
import com.cmc.purithm.feature.mypage.viewmodel.EditProfileSideEffects
import com.cmc.purithm.feature.mypage.viewmodel.EditProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_edit_profile
    private val navArgs by navArgs<EditProfileFragmentArgs>()
    private val username by lazy { navArgs.username }
    private val profile by lazy { navArgs.profile ?: "" }
    private val viewModel by viewModels<EditProfileViewModel>()

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.state.collect { state ->
                        if(state.loading){
                            showLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                        }
                        if(state.profileUrl.isEmpty()){
                            with(binding.imgProfile){
                                setImageResource(com.cmc.purithm.design.R.drawable.ic_profile_default_img)
                                scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
                                layoutParams = FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.WRAP_CONTENT,
                                    FrameLayout.LayoutParams.WRAP_CONTENT,
                                    android.view.Gravity.CENTER
                                )
                            }
                        }
                    }
                }
                launch {
                    viewModel.sideEffects.collect { sideEffect ->
                        when(sideEffect){
                            EditProfileSideEffects.StartGallery -> {
                                startActivityForResult(Intent(Intent.ACTION_PICK).apply {
                                    type = "image/*"
                                }, REQUEST_IMG_CODE)
                            }
                            EditProfileSideEffects.Success -> {
                                (activity as NavigationAction).popBackStackAfterProfileEdit()
                                showSnackBar(binding.root, getString(R.string.content_success_update_profile))
                            }
                            EditProfileSideEffects.ShowSetProfileImgDialog -> {
                                SetProfileImgDialog(
                                    clickEvent = { type ->
                                        if(type == "clear"){
                                            viewModel.clearProfile()
                                        } else {
                                            viewModel.startGallery()
                                        }
                                    }
                                ).show(childFragmentManager, null)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {
        with(binding){
            vm = viewModel
        }
    }

    override fun initView() {
        viewModel.initInfo(username, profile)
        with(binding) {
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_TEXT,
                title = "프로필 편집",
                content = "등록",
                contentClickListener = {
                    viewModel.updateProfile()
                },
                backClickListener = {
                    findNavController().popBackStack()
                }
            )

            editNickname.initView(
                maxSize = USER_NAME_MAX_SIZE,
                minSize = USER_NAME_MIN_SIZE,
                title = "닉네임",
                description = "국문 또는 영문으로 띄어쓰기 없이 작성해주세요.",
                hint = "닉네임을 입력해주세요",
                errorDescription = "최소 ${USER_NAME_MIN_SIZE}자 이상 입력해주세요",
                textChangeListener = { content ->
                    viewModel.setContent(content)
                }
            )
            editNickname.setText(username)
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

    companion object {
        private const val USER_NAME_MAX_SIZE = 20
        private const val USER_NAME_MIN_SIZE = 1
        private const val REQUEST_IMG_CODE = 1
    }
}