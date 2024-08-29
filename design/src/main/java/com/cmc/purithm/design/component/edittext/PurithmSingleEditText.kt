package com.cmc.purithm.design.component.edittext

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.cmc.purithm.design.R
import com.cmc.purithm.design.databinding.ViewEditTextSingleBinding
import com.cmc.purithm.design.util.Util.getColorResource
import kotlin.math.max

class PurithmSingleEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding by lazy {
        initBinding(context)
    }

    private fun initBinding(context: Context): ViewEditTextSingleBinding {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return ViewEditTextSingleBinding.inflate(layoutInflater, this, true)
    }


    fun initView(
        maxSize: Int,
        minSize: Int,
        title: String,
        description: String,
        hint: String,
        errorDescription: String,
        textChangeListener: (String) -> Unit
    ) {
        with(binding) {
            tvTitle.text = title
            tvDesc.text = description
            tvMaxCount.text = String.format(
                context.getString(R.string.content_edit_text_single_status),
                maxSize
            )

            editMain.hint = hint
            editMain.filters = arrayOf(InputFilter.LengthFilter(maxSize))
            editMain.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.tvDesc.visibility = View.VISIBLE
                    binding.tvMaxCount.visibility = View.VISIBLE
                    binding.tvCount.visibility = View.VISIBLE
                } else {
                    binding.tvDesc.visibility = View.GONE
                    binding.tvMaxCount.visibility = View.GONE
                    binding.tvCount.visibility = View.GONE
                }
            }
            editMain.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    Log.d(TAG, "onTextChanged: on")
                    if (s.isEmpty()) {
                        editMain.setBackgroundResource(R.drawable.bg_edit_text_default)
                        tvDesc.visibility = View.GONE
                        tvMaxCount.visibility = View.GONE
                        tvCount.visibility = View.GONE
                    } else if (s.length < minSize) {
                        showErrorMsg(errorDescription)
                    } else {
                        if(editMain.hasFocus()){
                            tvDesc.visibility = View.VISIBLE
                            tvMaxCount.visibility = View.VISIBLE
                            tvCount.visibility = View.VISIBLE
                        }
                        setDefault(description)
                    }
                    tvCount.text = "${s.length}"
                    textChangeListener(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    fun setText(text: String) {
        binding.editMain.setText(text)
    }

    fun getText(): String {
        return binding.editMain.text.toString()
    }

    fun showErrorMsg(errorMsg: String) {
        with(binding) {
            tvDesc.visibility = View.VISIBLE
            tvMaxCount.visibility = View.VISIBLE
            tvCount.visibility = View.VISIBLE
            editMain.setBackgroundResource(R.drawable.bg_edit_text_error)
            tvDesc.setTextColor(context.getColorResource(R.color.red_500))
            tvDesc.text = errorMsg

            tvCount.setTextColor(context.getColorResource(R.color.red_500))
        }
    }

    fun setDefault(description: String){
        with(binding) {
            editMain.setBackgroundResource(R.drawable.bg_edit_text)
            tvDesc.setTextColor(context.getColorResource(R.color.grey_300))
            tvDesc.text = description

            tvCount.setTextColor(context.getColorResource(R.color.blue_400))
        }
    }

    companion object {
        private const val TAG = "PurithmSingleEditText"
    }
}