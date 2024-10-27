package com.anahorn.fukusizer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        LinearLayout page = findViewById(R.id.info_page);

        TextView textView1 = new TextView(this);
        textView1.setTextSize(18);
        textView1.setGravity(Gravity.LEFT);
        textView1.setSingleLine(false);
        textView1.setTextColor(Color.BLACK);
        textView1.setText("USA Women's Size Standards");
        page.addView(textView1);

        final ImageView imageView1 = new ImageView(this);
        imageView1.setImageResource(R.drawable.usa_women_size_standards);
        imageView1.setAdjustViewBounds(true);
        page.addView(imageView1);

        TextView textView2 = new TextView(this);
        textView2.setTextSize(18);
        textView2.setGravity(Gravity.LEFT);
        textView2.setSingleLine(false);
        textView2.setTextColor(Color.BLACK);
        textView2.setText("USA Men's Size Standards");
        textView2.setPadding(0, 24, 0,0);
        page.addView(textView2);

        final ImageView imageView2 = new ImageView(this);
        imageView2.setImageResource(R.drawable.usa_men_size_standards);
        imageView2.setAdjustViewBounds(true);
        page.addView(imageView2);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
