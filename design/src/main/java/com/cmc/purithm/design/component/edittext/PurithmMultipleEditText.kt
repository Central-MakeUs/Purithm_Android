package com.cmc.purithm.design.component.edittext

import android.content.Context
import android.text.Editable
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

    fun initView(maxSize: Int, minSize: Int, hint: String = "", imeOption : Int) {
        this.maxSize = maxSize
        this.minSize = minSize
        with(binding) {
            editMain.hint = hint
            editMain.maxLines = maxSize
            editMain.imeOptions = imeOption

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
                    tvErrorMsg.visibility = View.INVISIBLE
                    tvStatus.text = String.format(
                        context.getString(R.string.content_edit_text_multiple_status),
                        s.length,
                        maxSize
                    )

                    if(s.isEmpty()){    // 입력된 텍스트가 없을 경우 background 변경
                        layoutMain.setBackgroundResource(R.drawable.bg_edit_text_default)
                    }
                    if (s.length >= maxSize) {  // 메시지 길이 초과
                        tvStatus.setTextColor(context.getColorResource(R.color.red_500))
                    } else {    // 텍스트가 한개라도 있으면 background 변경
                        tvStatus.setTextColor(context.getColorResource(R.color.blue_400))
                        layoutMain.setBackgroundResource(R.drawable.bg_edit_text_focus)
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    fun showErrorMsg() {
        with(binding){
            layoutStatus.visibility = View.GONE
            tvErrorMsg.visibility = View.VISIBLE
        }
    }

    fun getCurrentInput() = binding.editMain.text.toString()

    companion object {
        private const val TAG = "PurithmMultipleEditText"
    }
}