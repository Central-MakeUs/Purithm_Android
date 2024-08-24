package com.cmc.purithm.design.component.appbar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.cmc.purithm.design.R
import com.cmc.purithm.design.databinding.ViewAppbarBinding
import com.cmc.purithm.design.util.Util.dp

class PurithmAppbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by lazy {
        initView(context)
    }

    private fun initView(context: Context): ViewAppbarBinding {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return ViewAppbarBinding.inflate(inflater, this, true)
    }

    fun setAppBar(
        type: PurithmAppbarType,
        title: String = "",
        content : String = "",
        likeCnt: Int = 0,
        likeState: Boolean = false,
        backClickListener: (() -> Unit)? = null,
        likeClickListener: (() -> Unit)? = null,
        questionClickListener: (() -> Unit)? = null,
        registrationClickListener: (() -> Unit)? = null,
        settingClickListener: (() -> Unit)? = null,
        contentClickListener: (() -> Unit)? = null
    ) {
        Log.d(TAG, "setAppBar: start")
        Log.d(TAG, "setAppBar: type = $type")
        Log.d(TAG, "setAppBar: title = $title")

        clearAppbar()
        when (type) {
            PurithmAppbarType.ENG_DEFAULT -> setEngDefaultAppbar(
                title,
                likeState,
                likeClickListener
            )

            PurithmAppbarType.ENG_LIKE -> setEngLikeAppbar(
                title,
                likeCnt,
                likeState,
                likeClickListener
            )

            PurithmAppbarType.KR_DEFAULT -> setKrDefaultAppbar(title, questionClickListener)
            PurithmAppbarType.KR_BUTTON -> setKrButtonAppbar(title, registrationClickListener)
            PurithmAppbarType.KR_BACK -> setKrBackAppbar(title)
            PurithmAppbarType.ENG_TEXT -> setEngTextAppbar(title)
            PurithmAppbarType.ENG_SETTING -> setEngSettingAppbar(settingClickListener)
            PurithmAppbarType.KR_TEXT -> setKrTextAppbar(title, content, contentClickListener)
        }
        binding.btnBack.setOnClickListener {
            Log.d(TAG, "setAppBar: back on")
            backClickListener?.invoke()
        }
        Log.d(TAG, "setAppBar: end")
    }

    fun setLikeCnt(cnt: Int) {
        with(binding.tvLikeCnt) {
            visibility = View.VISIBLE
            text = cnt.toString()
        }
    }

    private fun clearAppbar() {
        with(binding) {
            with(tvTitleEn) {
                text = ""
                visibility = View.GONE
            }
            with(tvTitleKr) {
                text = ""
                visibility = View.GONE
            }
            with(tvRegistration) {
                text = ""
                visibility = View.GONE
            }
            with(tvLikeCnt) {
                text = ""
                visibility = View.GONE
            }
            with(tvContent) {
                text = ""
                visibility = View.GONE
                setOnClickListener(null)
            }
            with(btnLike) {
                setOnClickListener(null)
                visibility = View.GONE
            }
            with(btnQuestion) {
                setOnClickListener(null)
                visibility = View.GONE
            }
            with(btnSetting) {
                setOnClickListener(null)
                visibility = View.GONE
            }
            with(btnBack) {
                setOnClickListener(null)
                visibility = View.GONE
                setImageResource(R.drawable.ic_arrow_left)
            }
        }
    }

    private fun setKrBackAppbar(
        title: String,
    ) {
        setAppbarTopMargin(TOP_MARGIN_KR)
        with(binding) {
            tvTitleKr.visibility = View.VISIBLE
            tvTitleKr.text = title

            btnBack.visibility = View.VISIBLE
        }
    }

    private fun setKrTextAppbar(
        title: String,
        content: String,
        contentClickListener: (() -> Unit)?
    ) {
        setAppbarTopMargin(TOP_MARGIN_KR)
        with(binding) {
            tvTitleKr.visibility = View.VISIBLE
            tvTitleKr.text = title

            tvContent.visibility = View.VISIBLE
            tvContent.text = content
            tvContent.setOnClickListener {
                contentClickListener?.invoke()
            }

            btnBack.visibility = View.VISIBLE
        }
    }

    private fun setEngDefaultAppbar(
        title: String,
        likeState: Boolean,
        likeClickListener: (() -> Unit)?
    ) {
        setAppbarTopMargin(TOP_MARGIN_EN)
        with(binding) {
            tvTitleEn.visibility = View.VISIBLE
            tvTitleEn.text = title
            btnLike.setImageResource(
                if (likeState) {
                    R.drawable.ic_like_pressed_appbar
                } else {
                    R.drawable.ic_like_unpressed_appbar
                }
            )
            btnLike.visibility = View.VISIBLE
            btnLike.setOnClickListener {
                likeClickListener?.invoke()
            }
            btnBack.visibility = View.VISIBLE
            btnBack.setImageResource(R.drawable.ic_cancel)
        }
    }

    /**
     * 좋아요 요청 후, 데이터를 전체로 불러오는 것이 아닌 좋아요 상태만 요청하도록 변경
     * */
    fun setLike(like: Boolean) {
        if (like) {
            binding.btnLike.setImageResource(R.drawable.ic_like_pressed_appbar)
        } else {
            binding.btnLike.setImageResource(R.drawable.ic_like_unpressed_appbar)
        }
    }

    private fun setEngLikeAppbar(
        title: String,
        likeCnt: Int,
        likeState: Boolean,
        likeClickListener: (() -> Unit)?
    ) {
        setAppbarTopMargin(TOP_MARGIN_EN)
        with(binding) {
            tvTitleEn.visibility = View.VISIBLE
            tvTitleEn.text = title

            setLikeCnt(likeCnt)
            btnLike.setImageResource(
                if (likeState) {
                    R.drawable.ic_like_pressed_appbar
                } else {
                    R.drawable.ic_like_unpressed_appbar
                }
            )
            btnLike.visibility = View.VISIBLE
            btnLike.setOnClickListener {
                likeClickListener?.invoke()
            }
            btnBack.visibility = View.VISIBLE
        }
    }

    private fun setKrDefaultAppbar(
        title: String,
        questionClickListener: (() -> Unit)?
    ) {
        setAppbarTopMargin(TOP_MARGIN_KR)
        with(binding) {
            tvTitleKr.visibility = View.VISIBLE
            tvTitleKr.text = title

            btnQuestion.visibility = View.VISIBLE
            btnQuestion.setOnClickListener {
                questionClickListener?.invoke()
            }
            btnBack.visibility = View.VISIBLE
        }
    }

    private fun setEngSettingAppbar(
        settingClickListener: (() -> Unit)?
    ) {
        setAppbarTopMargin(TOP_MARGIN_EN)
        with(binding) {
            tvTitleEn.visibility = View.VISIBLE
            btnSetting.visibility = View.VISIBLE
            btnSetting.setOnClickListener {
                settingClickListener?.invoke()
            }
            btnBack.visibility = View.GONE
        }
    }

    private fun setKrButtonAppbar(
        title: String,
        registrationClickListener: (() -> Unit)?
    ) {
        setAppbarTopMargin(TOP_MARGIN_KR)
        with(binding) {
            tvTitleKr.visibility = View.VISIBLE
            tvTitleKr.text = title

            tvRegistration.visibility = View.VISIBLE
            tvRegistration.setOnClickListener {
                registrationClickListener?.invoke()
            }
            btnBack.visibility = View.VISIBLE
        }
    }

    private fun setEngTextAppbar(
        title: String
    ) {
        setAppbarTopMargin(TOP_MARGIN_EN)
        with(binding) {
            tvTitleEn.visibility = View.VISIBLE
            tvTitleEn.text = title

            btnBack.visibility = View.GONE
        }
    }

    private fun setAppbarTopMargin(top: Int) {
        Log.d(TAG, "setAppbarTopMargin: on")
        val constraintSet = ConstraintSet().apply {
            clone(binding.layoutMain)
        }
        constraintSet.connect(
            R.id.layout_appbar,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            top.dp
        )
        constraintSet.connect(
            R.id.layout_appbar,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            R.id.layout_appbar,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            R.id.layout_appbar,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.applyTo(binding.layoutMain)
    }


    enum class PurithmAppbarType {
        ENG_DEFAULT, ENG_LIKE, ENG_TEXT, ENG_SETTING, KR_DEFAULT, KR_BUTTON, KR_BACK, KR_TEXT
    }

    companion object {
        private const val TAG = "PurithmAppbar"

        private const val TOP_MARGIN_KR = 74
        private const val TOP_MARGIN_EN = 50
    }
}