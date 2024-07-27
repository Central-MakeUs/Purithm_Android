package com.cmc.purithm.design.component.appbar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
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
        likeCnt : Int = 0,
        backClickListener: (() -> Unit),
        searchClickListener: (() -> Unit)? = null,
        likeClickListener: (() -> Unit)? = null,
        questionClickListener: (() -> Unit)? = null,
        registrationClickListener: (() -> Unit)? = null,
    ) {
        Log.d(TAG, "setAppBar: start")
        Log.d(TAG, "setAppBar: type = $type")
        Log.d(TAG, "setAppBar: title = $title")
        when (type) {
            PurithmAppbarType.ENG_DEFAULT -> setEngDefaultAppbar(
                title,
                searchClickListener,
                likeClickListener
            )
            PurithmAppbarType.ENG_LIKE -> setEngLikeAppbar(title, likeCnt, likeClickListener)
            PurithmAppbarType.KR_DEFAULT -> setKrDefaultAppbar(title, questionClickListener)
            PurithmAppbarType.KR_BUTTON -> setKrButtonAppbar(title, registrationClickListener)
        }
        binding.btnBack.setOnClickListener {
            backClickListener()
        }
        Log.d(TAG, "setAppBar: end")
    }

    fun setLikeCnt(cnt : Int) {
        with(binding.tvLikeCnt){
            visibility = View.VISIBLE
            text = cnt.toString()
        }
    }

    private fun clearAppbar() {
        with(binding) {
            tvTitleEn.visibility = View.GONE
            tvTitleKr.visibility = View.GONE

            btnSearch.visibility = View.GONE
            btnLike.visibility = View.GONE
            btnQuestion.visibility = View.GONE

            tvRegistration.visibility = View.GONE
            tvLikeCnt.visibility = View.GONE
        }
    }

    private fun setEngDefaultAppbar(
        title: String,
        searchClickListener: (() -> Unit)?,
        likeClickListener: (() -> Unit)?
    ) {
        clearAppbar()
        setAppbarTopMargin(40)
        with(binding) {
            tvTitleEn.visibility = View.VISIBLE
            tvTitleEn.text = title

            btnSearch.visibility = View.VISIBLE
            btnSearch.setOnClickListener {
                searchClickListener?.invoke()
            }
            btnLike.visibility = View.VISIBLE
            btnLike.setOnClickListener {
                likeClickListener?.invoke()
            }
        }
    }

    private fun setEngLikeAppbar(
        title: String,
        likeCnt : Int,
        likeClickListener: (() -> Unit)?
    ) {
        clearAppbar()
        setAppbarTopMargin(40)
        with(binding) {
            tvTitleEn.visibility = View.VISIBLE
            tvTitleEn.text = title

            setLikeCnt(likeCnt)

            btnLike.visibility = View.VISIBLE
            btnLike.setOnClickListener {
                likeClickListener?.invoke()
            }
        }
    }

    private fun setKrDefaultAppbar(
        title: String,
        questionClickListener: (() -> Unit)?
    ) {
        clearAppbar()
        setAppbarTopMargin(64)
        with(binding) {
            tvTitleKr.visibility = View.VISIBLE
            tvTitleKr.text = title

            btnQuestion.visibility = View.VISIBLE
            btnQuestion.setOnClickListener {
                questionClickListener?.invoke()
            }
        }
    }

    private fun setKrButtonAppbar(
        title: String,
        registrationClickListener: (() -> Unit)?
    ) {
        clearAppbar()
        setAppbarTopMargin(64)
        with(binding) {
            tvTitleKr.visibility = View.VISIBLE
            tvTitleKr.text = title

            tvRegistration.visibility = View.VISIBLE
            tvRegistration.setOnClickListener {
                registrationClickListener?.invoke()
            }
        }
    }

    private fun setAppbarTopMargin(top: Int) {
        val layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        ).apply {
            setMargins(
                0,
                top.dp,
                0,
                10.dp
            )
        }
        binding.layoutAppbar.layoutParams = layoutParams
    }
    enum class PurithmAppbarType {
        ENG_DEFAULT, ENG_LIKE, KR_DEFAULT, KR_BUTTON
    }
    companion object {
        private const val TAG = "PurithmAppbar"
    }
}