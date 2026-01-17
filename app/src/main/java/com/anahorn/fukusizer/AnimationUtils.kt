package com.anahorn.fukusizer

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.ScaleAnimation

/**
 * Utility object for creating native Android animations.
 */
object AnimationHelper {

    /**
     * Creates a zoom-in animation with overshoot effect.
     * The view scales from 50% to 100% size with a bounce.
     *
     * @param view The view to animate
     * @param duration Animation duration in milliseconds (default: 700ms)
     */
    fun zoomIn(view: View, duration: Long = 700) {
        val scaleAnimation = ScaleAnimation(
            0.5f, 1.0f,  // From 50% to 100% scale X
            0.5f, 1.0f,  // From 50% to 100% scale Y
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,  // Pivot X at center
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f   // Pivot Y at center
        ).apply {
            this.duration = duration
            interpolator = OvershootInterpolator()
        }
        view.startAnimation(scaleAnimation)
    }

    /**
     * Creates a fade-in animation.
     * The view's alpha transitions from 0% to 100% opacity.
     *
     * @param view The view to animate
     * @param duration Animation duration in milliseconds (default: 700ms)
     */
    fun fadeIn(view: View, duration: Long = 700) {
        val fadeAnimation = AlphaAnimation(0.0f, 1.0f).apply {
            this.duration = duration
            interpolator = DecelerateInterpolator()
        }
        view.startAnimation(fadeAnimation)
    }

    /**
     * Creates a landing animation with combined fade-in and scale effects.
     * The view fades in while scaling from 80% to 100% with an overshoot bounce.
     *
     * @param view The view to animate
     * @param duration Animation duration in milliseconds (default: 700ms)
     */
    fun landing(view: View, duration: Long = 700) {
        val animationSet = AnimationSet(true).apply {
            // Fade in
            addAnimation(AlphaAnimation(0.0f, 1.0f))

            // Scale with overshoot for landing effect
            addAnimation(ScaleAnimation(
                0.8f, 1.0f,
                0.8f, 1.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
            ))

            this.duration = duration
            interpolator = OvershootInterpolator()
        }
        view.startAnimation(animationSet)
    }
}

