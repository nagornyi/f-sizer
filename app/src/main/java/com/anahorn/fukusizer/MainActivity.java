package com.anahorn.fukusizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    private AdView mAdView;

    public static String SIZE = "[NOT SELECTED]";
    public static String CHOSEN_SIZE = "[NOT SELECTED]";
    public static String FROM_REGION = "USA";
    public static String TO_REGION = "Japan";
    public static String CLOTHING_TYPE = "Tops";
    public static String DEPARTMENT = "Women";

    private DrawerLayout mDrawerLayout;
    private ListView mLeftDrawerList;

    private ListView mRightDrawerList;
    private Spinner clothingTypeSpinner;
    private Spinner sizeSpinner;
    private String[] mClothingTypeMen;
    private String[] mClothingTypeWomen;

    private CharSequence mTitle;
    private View mLeftDrawerView;
    private View mRightDrawerView;
    ArrayAdapter<String> adapterLeft;
    ArrayAdapter<String> adapterRight;
    ArrayAdapter<String> adapterSize;
    public static List<String> regions;
    public static List<String> sizes;

    Button convert_btn;

    ImageView sourceFlag;
    ImageView targetFlag;
    ImageView convArrow;

    public MenuItem genderSelector;
    public MenuItem directionSelector;

    public static List<String> EuropeNations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.context = getApplicationContext();
        DatabaseHandler db = new DatabaseHandler(this);

        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, initializationStatus -> {
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        View backgroundimage = findViewById(R.id.main_page);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(10);

        if(db.getAllContacts().size() == 0) Data.InitializeSizes(this);

        mTitle = getTitle();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mLeftDrawerList = findViewById(R.id.left_drawer);

        //clothing type spinner
        clothingTypeSpinner = findViewById(R.id.clothing_type_spinner);
        // Create an ArrayAdapter using the string array and a default clothingTypeSpinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.women_clothing, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the clothingTypeSpinner
        clothingTypeSpinner.setAdapter(adapter);
        clothingTypeSpinner.setOnItemSelectedListener(new ClothingSpinnerActivity());

        //size spinner
        sizeSpinner = findViewById(R.id.size_spinner);
        sizes = getListOfSizes();
        adapterSize = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sizes);
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(adapterSize);
        sizeSpinner.setOnItemSelectedListener(new SizeSpinnerActivity());

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Set the adapter for the list view
        regions = getListOfRegions();
        adapterLeft = new ArrayAdapter<>(this, R.layout.drawer_list_item, regions);
        mLeftDrawerList.setAdapter(adapterLeft);
        // Set the list's click listener
        mLeftDrawerList.setOnItemClickListener(new LeftDrawerItemClickListener());

        mRightDrawerList = findViewById(R.id.right_drawer);
        mClothingTypeMen = getResources().getStringArray(R.array.men_clothing);
        mClothingTypeWomen = getResources().getStringArray(R.array.women_clothing);

        // Set the adapter for the list view
        adapterRight = new ArrayAdapter<String>(this, R.layout.drawer_list_item, regions);
        mRightDrawerList.setAdapter(adapterRight);
        // Set the list's click listener
        mRightDrawerList.setOnItemClickListener(new RightDrawerItemClickListener());

        convert_btn = (Button) findViewById(R.id.convert_button);
        Typeface catTypeface = Typeface.createFromAsset(MainActivity.getAppContext().getAssets(),
                "fonts/DancingScript-Regular.ttf");
        convert_btn.setTypeface(catTypeface);

        if(mDrawerLayout == null || mLeftDrawerView == null || mRightDrawerView == null) {
            // Configure navigation drawer
            mDrawerLayout = findViewById(R.id.drawer_layout);
            mLeftDrawerView = findViewById(R.id.left_drawer);
            mRightDrawerView = findViewById(R.id.right_drawer);
        }

        if (savedInstanceState == null) {
            SIZE = "[NOT SELECTED]";
            CHOSEN_SIZE = "[NOT SELECTED]";
            FROM_REGION = "USA";
            TO_REGION = "Japan";
            DEPARTMENT="Women";
            CLOTHING_TYPE = "Tops";
        }

        sourceFlag = findViewById(R.id.sourceFlag);
        targetFlag = findViewById(R.id.targetFlag);
        convArrow = findViewById(R.id.arrow);

        View.OnClickListener l1 = v -> mDrawerLayout.openDrawer(mLeftDrawerView);
        sourceFlag.setOnClickListener(l1);
        View.OnClickListener l2 = v -> mDrawerLayout.openDrawer(mRightDrawerView);
        targetFlag.setOnClickListener(l2);

        EuropeNations = new ArrayList<String>();
        EuropeNations.add("France");
        EuropeNations.add("Germany");
        EuropeNations.add("Italy");
        EuropeNations.add("Spain");
        EuropeNations.add("Belgium");
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        directionSelector = menu.findItem(R.id.action_info);
        directionSelector.setIcon(R.drawable.info);
        genderSelector = menu.findItem(R.id.action_gender);
        genderSelector.setIcon(R.drawable.girl);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        int itemId = item.getItemId();
        if (itemId == R.id.action_info) {
            displayInfo();
            return true;
        } else if (itemId == R.id.womanGender) {
            changeClothingType(R.array.women_clothing);
            DEPARTMENT = "Women";
            updateDrawerItems();
            genderSelector.setIcon(R.drawable.girl);
            return true;
        } else if (itemId == R.id.manGender) {
            changeClothingType(R.array.men_clothing);
            DEPARTMENT = "Men";
            updateDrawerItems();
            genderSelector.setIcon(R.drawable.boy);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void sendMessage(View view) {
        boolean check_countries = true;
        boolean check_size = true;

        if(FROM_REGION.equals("[NOT SELECTED]") && TO_REGION.equals("[NOT SELECTED]")) {
            check_countries = false;
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(sourceFlag);
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(targetFlag);
        } else if(FROM_REGION.equals("[NOT SELECTED]")) {
            check_countries = false;
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(sourceFlag);
        } else if(TO_REGION.equals("[NOT SELECTED]")) {
            check_countries = false;
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(targetFlag);
        }

        if(CHOSEN_SIZE.equals("[NOT SELECTED]") || CHOSEN_SIZE.equals("•••")) {
            check_size = false;
        }

        if (!check_countries) {
            return;
        }
        if (!check_size) {
            highlightSize(true);
            return;
        }

        DatabaseHandler db = new DatabaseHandler(this);
        // Reading all sizes
        List<Contact> contacts = db.getAllContacts();

        String resSize = "";
        for (Contact cn : contacts) {
            if(cn.getDept().equalsIgnoreCase(DEPARTMENT) &&
                    cn.getClothing().equalsIgnoreCase(CLOTHING_TYPE) &&
                    cn.getSizes().contains(FROM_REGION+":"+CHOSEN_SIZE+","))
            {
                Pattern pattern = Pattern.compile(TO_REGION+":([^,]+)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(cn.getSizes());
                while (matcher.find()) {
                    if(!(resSize.length()==0)){resSize += " or ";}
                    resSize += matcher.group(1);
                }
            }
        }

        if(resSize.isEmpty()) resSize="size not found, sorry :(";

        TextView textView = findViewById(R.id.result);
        textView.setText(resSize);
        YoYo.with(Techniques.Landing).duration(700).playOn(textView);
    }

    public void displayInfo() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    public void changeClothingType(int resId) {
        String CLOTHING_TYPE_MEM = CLOTHING_TYPE;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                resId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clothingTypeSpinner.setAdapter(adapter);
        String[] new_clothes = getResources().getStringArray(resId);
        int new_pos = Arrays.asList(new_clothes).indexOf(CLOTHING_TYPE_MEM);
        if(new_pos != -1) {
            clothingTypeSpinner.setSelection(new_pos);
            CLOTHING_TYPE = CLOTHING_TYPE_MEM;
        } else {
            CLOTHING_TYPE = new_clothes[0];
        }
        TextView textView = findViewById(R.id.result);
        textView.setText("•••");
    }

    private class LeftDrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItemLeftPane(position);
        }
    }

    private class RightDrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItemRightPane(position);
        }
    }

    private List<String> getListOfRegions(){
        DatabaseHandler db = new DatabaseHandler(this);
        List<Contact> contacts = db.getAllContacts();
        List<String> regions = new ArrayList<String>();
        for (Contact cn : contacts) {
            if(cn.getDept().equals(DEPARTMENT) &&
                    cn.getClothing().equals(CLOTHING_TYPE))
            {
                Pattern pattern = Pattern.compile("([^,]+):([^,]+)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(cn.getSizes());
                while (matcher.find()) {
                    if(!regions.contains(matcher.group(1))) regions.add(matcher.group(1));
                }
                return regions;
            }
        }
        return null;
    }

    private List<String> getListOfSizes(){
        DatabaseHandler db = new DatabaseHandler(this);
        // Reading all sizes
        List<Contact> contacts = db.getAllContacts();
        List<String> sizes = new ArrayList<String>();
        sizes.add("•••");
        if(FROM_REGION.equalsIgnoreCase("[NOT SELECTED]")) return sizes;
        for (Contact cn : contacts) {
            if(cn.getDept().equals(DEPARTMENT) &&
                    cn.getClothing().equals(CLOTHING_TYPE))
            {
                Pattern pattern = Pattern.compile(FROM_REGION+":([^,]+)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(cn.getSizes());
                while (matcher.find()) {
                    if(!sizes.contains(matcher.group(1))) sizes.add(matcher.group(1));
                }
            }
        }
        return sizes;
    }

    private void resetConversionDirection() {
        TextView textView = findViewById(R.id.result);
        textView.setText("•••");
        String FROM_REGION_MEM = FROM_REGION;
        String TO_REGION_MEM = TO_REGION;
        FROM_REGION = "[NOT SELECTED]";
        TO_REGION = "[NOT SELECTED]";
        mLeftDrawerList.clearChoices();
        mDrawerLayout.closeDrawer(mLeftDrawerList);
        mRightDrawerList.clearChoices();
        mDrawerLayout.closeDrawer(mRightDrawerList);
        int new_from_reg_pos = regions.indexOf(FROM_REGION_MEM);
        int new_to_reg_pos = regions.indexOf(TO_REGION_MEM);
        if(new_from_reg_pos==-1){
            if(EuropeNations.contains(FROM_REGION_MEM)) {
                FROM_REGION_MEM = "Europe";
                new_from_reg_pos = regions.indexOf(FROM_REGION_MEM);
            }
        }
        if(new_to_reg_pos==-1){
            if(EuropeNations.contains(TO_REGION_MEM)) {
                TO_REGION_MEM = "Europe";
                new_to_reg_pos = regions.indexOf(TO_REGION_MEM);
            }
        }
        if(new_from_reg_pos!=-1){
            FROM_REGION = FROM_REGION_MEM;
            mLeftDrawerList.setItemChecked(new_from_reg_pos, true);
            int id = getResources().getIdentifier(FROM_REGION.toLowerCase().replaceAll("\\s", ""), "drawable", "com.anahorn.fukusizer");
            YoYo.with(Techniques.FadeIn).duration(700).playOn(sourceFlag);
            sourceFlag.setImageResource(id);
        }
        if(new_to_reg_pos!=-1){
            TO_REGION = TO_REGION_MEM;
            mRightDrawerList.setItemChecked(new_to_reg_pos, true);
            int id = getResources().getIdentifier(TO_REGION.toLowerCase().replaceAll("\\s",""), "drawable", "com.anahorn.fukusizer");
            YoYo.with(Techniques.FadeIn).duration(700).playOn(targetFlag);
            targetFlag.setImageResource(id);
        }
        SIZE = "[NOT SELECTED]";
        CHOSEN_SIZE = "[NOT SELECTED]";
        updateSizeItems();
        highlightSize(false);
        int id = R.drawable.question;
        if(FROM_REGION.equals("[NOT SELECTED]")){
            YoYo.with(Techniques.FadeIn).duration(700).playOn(sourceFlag);
            sourceFlag.setImageResource(id);
        }
        YoYo.with(Techniques.FadeIn).duration(700).playOn(convArrow);
        convArrow.setImageResource(R.drawable.arrow);
        if(TO_REGION.equals("[NOT SELECTED]")) {
            YoYo.with(Techniques.FadeIn).duration(700).playOn(targetFlag);
            targetFlag.setImageResource(id);
        }
    }

    private void updateDrawerItems(){
        regions = getListOfRegions();
        adapterLeft.clear();
        adapterLeft.addAll(regions);
        adapterLeft.notifyDataSetChanged();
        adapterRight.clear();
        adapterRight.addAll(regions);
        adapterRight.notifyDataSetChanged();
        resetConversionDirection();
    }

    private void updateSizeItems(){
        sizes = getListOfSizes();
        adapterSize.clear();
        adapterSize.addAll(sizes);
        adapterSize.notifyDataSetChanged();
        sizeSpinner.setSelection(0);
    }

    private class ClothingSpinnerActivity extends Activity implements ListView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if(DEPARTMENT.equals("Women")) CLOTHING_TYPE = mClothingTypeWomen[pos];
            if(DEPARTMENT.equals("Men")) CLOTHING_TYPE = mClothingTypeMen[pos];
            updateDrawerItems();
        }
        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    private class SizeSpinnerActivity extends Activity implements ListView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            CHOSEN_SIZE = sizes.get(pos);
            highlightSize(false);
        }
        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    private void selectItemLeftPane(int position) {
        FROM_REGION = regions.get(position);
        mLeftDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mLeftDrawerList);
        updateSizeItems();
        int id = getResources().getIdentifier(FROM_REGION.toLowerCase().replaceAll("\\s", ""), "drawable", "com.anahorn.fukusizer");
        YoYo.with(Techniques.FadeIn).duration(700).playOn(sourceFlag);
        sourceFlag.setImageResource(id);
    }

    private void selectItemRightPane(int position) {
        TO_REGION = regions.get(position);
        mRightDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mRightDrawerList);
        int id = getResources().getIdentifier(TO_REGION.toLowerCase().replaceAll("\\s",""), "drawable", "com.anahorn.fukusizer");
        YoYo.with(Techniques.FadeIn).duration(700).playOn(targetFlag);
        targetFlag.setImageResource(id);
    }

    private void highlightSize(Boolean status){
        TextView textView = findViewById(R.id.result);
        textView.setText("•••");
        TextView size_label = findViewById(R.id.size_label);
        if (status) {
            size_label.setTextColor(Color.RED);
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(size_label);
        } else {
            size_label.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
