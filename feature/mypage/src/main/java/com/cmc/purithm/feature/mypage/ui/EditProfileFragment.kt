package com.cmc.purithm.feature.mypage.ui

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.FragmentEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_edit_profile
    private val navArgs by navArgs<EditProfileFragmentArgs>()
    private val username by lazy { navArgs.username }
    private val profile by lazy { navArgs.profile }

    override fun initObserving() {

    }

    override fun initBinding() {
        with(binding){
            this.profile = this@EditProfileFragment.profile
        }
    }

    override fun initView() {
        with(binding) {
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_TEXT,
                title = "프로필 편집",
                content = "등록",
                contentClickListener = {

                },
                backClickListener = {
                    findNavController().popBackStack()
                }
            )
        }
    }
}