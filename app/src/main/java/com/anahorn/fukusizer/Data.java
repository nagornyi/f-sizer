package com.anahorn.fukusizer;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Iterator;

public class Data {
    public static void InitializeSizes(Context context) {
        try {
            // Load JSON from raw resource
            InputStream is = context.getResources().openRawResource(R.raw.clothing_sizes);
            int sizes_data = is.available();
            byte[] buffer = new byte[sizes_data];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            // Parse JSON
            JSONObject obj = new JSONObject(json);
            JSONObject sizes = obj.getJSONObject("sizes");

            // Initialize database
            DatabaseHandler db = new DatabaseHandler(context);

            // Iterate through departments
            Iterator<String> depts = sizes.keys();
            while(depts.hasNext()) {
                String dept = depts.next();
                JSONObject deptObj = sizes.getJSONObject(dept);

                // Iterate through clothing types
                Iterator<String> clothingTypes = deptObj.keys();
                while(clothingTypes.hasNext()) {
                    String clothing = clothingTypes.next();
                    JSONArray sizeArray = deptObj.getJSONArray(clothing);

                    // Add each size entry
                    for(int i = 0; i < sizeArray.length(); i++) {
                        String size = sizeArray.getString(i);
                        Clothing clothingItem = new Clothing(dept, clothing, size);
                        db.addClothingItem(clothingItem);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
