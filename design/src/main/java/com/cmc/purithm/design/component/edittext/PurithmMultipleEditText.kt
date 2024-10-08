package com.cmc.purithm.design.component.edittext

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.cmc.purithm.design.R
import com.cmc.purithm.design.databinding.ViewEditTextMultipleBinding
import com.cmc.purithm.design.util.Util.getColorResource

class PurithmMultipleEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by lazy {
        initBinding(context)
    }

    private var maxSize = 0
    private var minSize = 0

    private fun initBinding(context: Context): ViewEditTextMultipleBinding {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return ViewEditTextMultipleBinding.inflate(inflater, this, true)
    }

    fun initView(
        maxSize: Int,
        minSize: Int,
        hint: String = "",
        imeOption: Int,
        errorMsg: String,
        textChangeListener: (String) -> Unit,
        focusChangeListener : (Boolean) -> Unit
    ) {
        this.maxSize = maxSize
        this.minSize = minSize

        with(binding) {
            tvErrorMsg.text = errorMsg
            tvStatus.text = String.format(
                context.getString(R.string.content_edit_text_multiple_status),
                maxSize,
                minSize
            )

            editMain.hint = hint
            editMain.filters = arrayOf(InputFilter.LengthFilter(maxSize))
            editMain.imeOptions = imeOption
            editMain.setOnFocusChangeListener { _, hasFocus ->
                focusChangeListener(hasFocus)
                if(hasFocus) {
                    layoutMain.setBackgroundResource(R.drawable.bg_edit_text_focus)
                } else {
                    layoutMain.setBackgroundResource(R.drawable.bg_edit_text_unfocus)
                    val inputSize = editMain.text.toString().length
                    if(inputSize > 0) {
                        if (inputSize < minSize) {
                            showErrorMsg()
                        } else {
                            showDefault()
                        }
                    } else {
                        layoutStatus.visibility = View.GONE
                        tvErrorMsg.visibility = View.GONE
                    }
                }
            }
            editMain.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    // 기본 설정
                    layoutStatus.visibility = View.VISIBLE
                    tvErrorMsg.visibility = View.GONE
                    editMain.setBackgroundResource(R.color.white)
                    tvTextCount.text = "${s.length}"
                    textChangeListener(s.toString())

                    if (s.isEmpty()) {    // 입력된 텍스트가 없을 경우 background 변경
                        layoutMain.setBackgroundResource(R.drawable.bg_edit_text_default)
                        editMain.setBackgroundResource(R.drawable.shape_white_trans_60_background)
                        return
                    }
                    if (s.length < minSize){ // 최소 입력이 안됐을 때
                        tvTextCount.setTextColor(context.getColorResource(R.color.red_500))
                        layoutMain.setBackgroundResource(R.drawable.bg_edit_text_error)
                        return
                    }
                    if (s.length >= maxSize) {  // 메시지 길이 초과
                        tvStatus.setTextColor(context.getColorResource(R.color.red_500))
                        editMain.setSelection(maxSize)
                        showErrorMsg()
                        return
                    } else {    // 텍스트가 한개라도 있으면 background 변경
                        tvTextCount.setTextColor(context.getColorResource(R.color.blue_400))
                        layoutMain.setBackgroundResource(R.drawable.bg_edit_text_focus)
                        editMain.setBackgroundResource(R.drawable.shape_white_background)
                        showDefault()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun showErrorMsg() {
        with(binding) {
            layoutStatus.visibility = View.GONE
            tvErrorMsg.visibility = View.VISIBLE
        }
    }

    private fun showDefault() {
        with(binding) {
            layoutStatus.visibility = View.VISIBLE
            tvErrorMsg.visibility = View.GONE
        }
    }

    fun getFocus() = binding.editMain.hasFocus()
    fun removeFocus() = binding.editMain.clearFocus()

    companion object {
        private const val TAG = "PurithmMultipleEditText"
    }
}