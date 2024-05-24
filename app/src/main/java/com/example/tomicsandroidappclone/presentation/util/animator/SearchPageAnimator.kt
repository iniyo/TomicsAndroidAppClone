package com.example.tomicsandroidappclone.presentation.util.animator

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import com.example.tomicsandroidappclone.databinding.SearchLayoutBinding

class SearchPageAnimator(private val binding: SearchLayoutBinding) {

    fun animateSearchLayout() {
        val searchLayout = binding.searchLayout

        searchLayout.post {
            val screenHeight = searchLayout.height.toFloat()
            searchLayout.translationY = screenHeight

            val animator = ObjectAnimator.ofFloat(searchLayout, "translationY", 0f)
            animator.duration = 300

            val animatorSet = AnimatorSet()
            animatorSet.play(animator)
            animatorSet.start()
        }
    }
}