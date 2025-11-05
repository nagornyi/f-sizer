package com.anahorn.fukusizer

import android.content.Context
import org.json.JSONObject

object Data {
    fun initializeSizes(context: Context) {
        try {
            // Load JSON from raw resource
            val inputStream = context.resources.openRawResource(R.raw.clothing_sizes)
            val sizesData = inputStream.available()
            val buffer = ByteArray(sizesData)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)

            // Parse JSON
            val obj = JSONObject(json)
            val sizes = obj.getJSONObject("sizes")

            // Initialize database
            val db = DatabaseHandler(context)

            // Iterate through departments
            val depts = sizes.keys()
            while (depts.hasNext()) {
                val dept = depts.next()
                val deptObj = sizes.getJSONObject(dept)

                // Iterate through clothing types
                val clothingTypes = deptObj.keys()
                while (clothingTypes.hasNext()) {
                    val clothing = clothingTypes.next()
                    val sizeArray = deptObj.getJSONArray(clothing)

                    // Add each size entry
                    for (i in 0 until sizeArray.length()) {
                        val size = sizeArray.getString(i)
                        val clothingItem = Clothing(dept, clothing, size)
                        db.addClothingItem(clothingItem)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

