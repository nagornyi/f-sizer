package com.anahorn.fukusizer

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ImageView

/**
 * Utility for updating adapters.
 */
object AdapterUtils {

    /**
     * Updates an ArrayAdapter with new data.
     */
    fun <T> updateAdapter(adapter: ArrayAdapter<T>, newData: List<T>) {
        adapter.clear()
        adapter.addAll(newData)
        adapter.notifyDataSetChanged()
    }

    /**
     * Creates a standard spinner adapter.
     */
    fun createSpinnerAdapter(
        context: Context,
        data: List<String>
    ): ArrayAdapter<String> {
        return ArrayAdapter(context, R.layout.spinner_item, data.toMutableList()).apply {
            setDropDownViewResource(R.layout.spinner_item)
        }
    }

    /**
     * Creates a drawer list adapter.
     */
    fun createDrawerAdapter(
        context: Context,
        data: List<String>
    ): ArrayAdapter<String> {
        return ArrayAdapter(context, R.layout.drawer_list_item, data.toMutableList())
    }

    /**
     * Creates a spinner adapter from resources.
     */
    fun createSpinnerAdapterFromResource(
        context: Context,
        resourceId: Int
    ): ArrayAdapter<CharSequence> {
        return ArrayAdapter.createFromResource(
            context,
            resourceId,
            R.layout.spinner_item
        ).apply {
            setDropDownViewResource(R.layout.spinner_item)
        }
    }

    /**
     * Updates an image view with animation.
     */
    fun updateImageWithAnimation(
        imageView: ImageView,
        resourceId: Int,
        animate: Boolean = true
    ) {
        if (animate) {
            AnimationHelper.fadeIn(imageView)
        }
        imageView.setImageResource(resourceId)
    }
}

