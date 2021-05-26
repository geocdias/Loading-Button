package com.geocdias.loadingbutton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.geocdias.loadingbutton.databinding.LoadingButtonBinding

class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    sealed class LoadingButtonState(val isEnabled: Boolean, val progressVisibility: Int) {
        object Idle : LoadingButtonState(true, View.GONE)
        object Loading : LoadingButtonState(false, View.VISIBLE)
        object Complete : LoadingButtonState(false, View.GONE)
    }

    private var titleBtn: String? = null
    private var loadingTitle: String? = null
    private var completeTitle: String? = null
    private val binding = LoadingButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private var state: LoadingButtonState = LoadingButtonState.Idle
        set(value) {
            field = value
            refreashState()
        }

    init {
        setupLayout(attrs)
        refreashState()
    }

    private fun setupLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.LoadingButton)
            val titleId = attributes.getResourceId(R.styleable.LoadingButton_loading_button_idle_title, 0)
            val loadingTitleId = attributes.getResourceId(R.styleable.LoadingButton_loading_button_loading_title, 0)
            val completeTitleId = attributes.getResourceId(R.styleable.LoadingButton_loading_button_complete_title, 0)

            if (titleId != 0) {
                titleBtn = context.getString(titleId)
            }

            if (loadingTitleId != 0) {
                loadingTitle = context.getString(loadingTitleId)
            }

            if (completeTitleId != 0) {
                completeTitle = context.getString(completeTitleId)
            }

            attributes.recycle()
        }
    }

    private fun refreashState() {
        isEnabled = state.isEnabled
        isClickable = state.isEnabled

        binding.progressBtn.visibility = state.progressVisibility

        binding.titleBtn.run {
            isEnabled = state.isEnabled
            isClickable = state.isEnabled
        }

        when (state) {
            LoadingButtonState.Idle -> {
                setBackgroundResource(R.drawable.button_state_idle)
                binding.titleBtn.text = titleBtn
            }
            LoadingButtonState.Loading -> {
                setBackgroundResource(R.drawable.button_state_disable)
                binding.titleBtn.text = loadingTitle
            }
            LoadingButtonState.Complete -> {
                setBackgroundResource(R.drawable.button_state_complete)
                binding.titleBtn.text = completeTitle
            }
        }
    }

    fun loading() {
        state = LoadingButtonState.Loading
        refreashState()
    }

    fun idle() {
        state = LoadingButtonState.Idle
        refreashState()
    }

    fun complete() {
        state = LoadingButtonState.Complete
        refreashState()
    }
}
