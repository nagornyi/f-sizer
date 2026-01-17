package com.anahorn.fukusizer

import android.view.View
import android.widget.AdapterView

/**
 * Collection of spinner listeners for the size conversion app.
 */
object SpinnerListeners {

    /**
     * Creates a clothing type spinner listener.
     *
     * @param onClothingSelected Callback when a clothing type is selected
     */
    fun createClothingSpinnerListener(
        onClothingSelected: (position: Int) -> Unit
    ): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                onClothingSelected(pos)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No action needed
            }
        }
    }

    /**
     * Creates a size spinner listener.
     *
     * @param onSizeSelected Callback when a size is selected
     */
    fun createSizeSpinnerListener(
        onSizeSelected: (position: Int) -> Unit
    ): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                onSizeSelected(pos)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No action needed
            }
        }
    }

    /**
     * Creates a drawer item click listener.
     *
     * @param onItemClick Callback when a drawer item is clicked
     */
    fun createDrawerItemClickListener(
        onItemClick: (position: Int) -> Unit
    ): AdapterView.OnItemClickListener {
        return object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                onItemClick(position)
            }
        }
    }
}

