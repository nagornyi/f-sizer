package com.anahorn.fukusizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    private DatabaseHandler db;
    private SharedPreferences prefs;
    private AdView mAdView;

    public static String SIZE = "[NOT SELECTED]";
    public static String CHOSEN_SIZE = "[NOT SELECTED]";
    public static String INT_REGION = "International";
    public static String FROM_REGION = "USA";
    public static String TO_REGION = "Germany";
    public static String CLOTHING_TYPE = "Tops";
    public static String DEPARTMENT;

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

    ImageView clothingImage;
    ImageView sourceFlag;
    ImageView targetFlag;
    ImageView convArrow;
    Button convertButton;

    public MenuItem genderSelector;
    public MenuItem appInformation;

    public static List<String> EuropeanCountries;
    public static List<String> AsianCountries;
    public static Map<String, Integer> mapOfFlagIcons;
    public static Map<String, Integer> mapOfWomenClothingIcons;
    public static Map<String, Integer> mapOfMenClothingIcons;

    public static final String PREFS_NAME = "ApplicationPrefs";
    public static final String KEY_DB_RECREATED = "db_recreated";
    public static final String KEY_SELECTED_DEPARTMENT = "selected_department";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Get the department choice from preferences
        DEPARTMENT = prefs.getString(KEY_SELECTED_DEPARTMENT, context.getResources().getString(R.string.department_women));

        // Reset the DB recreated flag on every app start (hot-reload included)
        prefs.edit().putBoolean(KEY_DB_RECREATED, false).apply();
        // Initialize the DatabaseHandler instance only once
        db = new DatabaseHandler(this);

        EuropeanCountries = Arrays.asList(context.getResources().getStringArray(R.array.european_countries));
        AsianCountries = Arrays.asList(context.getResources().getStringArray(R.array.asian_countries));
        mapOfFlagIcons = IconHelpers.getMapOfFlagIcons();
        mapOfWomenClothingIcons = IconHelpers.getMapOfWomenClothingIcons();
        mapOfMenClothingIcons = IconHelpers.getMapOfMenClothingIcons();

        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, initializationStatus -> {});

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        View backgroundimage = findViewById(R.id.main_page);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(10);
        Animation buttonPressAnimation = AnimationUtils.loadAnimation(this, R.anim.button_press);

        if(db.getAllClothingItems().isEmpty()) Data.InitializeSizes(this);

        mTitle = getTitle();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mLeftDrawerList = findViewById(R.id.left_drawer);

        // clothing type spinner
        clothingTypeSpinner = findViewById(R.id.clothing_type_spinner);
        int listOfClothes;
        if (DEPARTMENT.equals(context.getResources().getString(R.string.department_women))) {
            listOfClothes = R.array.women_clothing;
        } else {
            listOfClothes = R.array.men_clothing;
        }
        // Create an ArrayAdapter using the string array and clothingTypeSpinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                listOfClothes, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the clothingTypeSpinner
        clothingTypeSpinner.setAdapter(adapter);
        clothingTypeSpinner.setOnItemSelectedListener(new ClothingSpinnerActivity());

        // size spinner
        sizeSpinner = findViewById(R.id.size_spinner);
        sizes = getListOfSizes();
        adapterSize = new ArrayAdapter<>(this, R.layout.spinner_item, sizes);
        adapterSize.setDropDownViewResource(R.layout.spinner_item);
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
        adapterRight = new ArrayAdapter<>(this, R.layout.drawer_list_item, regions);
        mRightDrawerList.setAdapter(adapterRight);
        // Set the list's click listener
        mRightDrawerList.setOnItemClickListener(new RightDrawerItemClickListener());

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
            TO_REGION = "Germany";
            if (DEPARTMENT.equals(context.getResources().getString(R.string.department_women))) CLOTHING_TYPE = mClothingTypeWomen[0];
            if (DEPARTMENT.equals(context.getResources().getString(R.string.department_men))) CLOTHING_TYPE = mClothingTypeMen[0];
        }

        sourceFlag = findViewById(R.id.sourceFlag);
        targetFlag = findViewById(R.id.targetFlag);
        convArrow = findViewById(R.id.arrow);
        clothingImage = findViewById(R.id.clothing_image);

        View.OnClickListener l1 = v -> mDrawerLayout.openDrawer(mLeftDrawerView);
        sourceFlag.setOnClickListener(l1);
        View.OnClickListener l2 = v -> mDrawerLayout.openDrawer(mRightDrawerView);
        targetFlag.setOnClickListener(l2);

        convertButton = findViewById(R.id.convert_button);
        convertButton.setOnClickListener(v -> {
            v.startAnimation(buttonPressAnimation);
            convertSize(v);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        appInformation = menu.findItem(R.id.action_info);
        appInformation.setIcon(R.drawable.info);
        genderSelector = menu.findItem(R.id.action_gender);

        // Configure menu items with custom layout
        MenuItem womanItem = menu.findItem(R.id.womanGender);
        MenuItem manItem = menu.findItem(R.id.manGender);
        womanItem.setActionView(R.layout.custom_menu_item);
        manItem.setActionView(R.layout.custom_menu_item);
        if (DEPARTMENT.equals(context.getResources().getString(R.string.department_women))) {
            womanItem.setChecked(true);
            genderSelector.setIcon(R.drawable.girl);
        } else {
            manItem.setChecked(true);
            genderSelector.setIcon(R.drawable.boy);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        int itemId = item.getItemId();
        if (itemId == R.id.action_info) {
            displayAppInfo();
            return true;
        } else if (itemId == R.id.womanGender) {
            updateClothingsForDepartment(R.array.women_clothing);
            DEPARTMENT = context.getResources().getString(R.string.department_women);
            prefs.edit().putString(KEY_SELECTED_DEPARTMENT, DEPARTMENT).apply();
            genderSelector.setIcon(R.drawable.girl);
            updateDrawerItems();
            return true;
        } else if (itemId == R.id.manGender) {
            updateClothingsForDepartment(R.array.men_clothing);
            DEPARTMENT = context.getResources().getString(R.string.department_men);
            prefs.edit().putString(KEY_SELECTED_DEPARTMENT, DEPARTMENT).apply();
            genderSelector.setIcon(R.drawable.boy);
            updateDrawerItems();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void convertSize(View view) {
        boolean check_countries = true;
        boolean check_size = true;

        if (FROM_REGION.equals("[NOT SELECTED]") && TO_REGION.equals("[NOT SELECTED]")) {
            check_countries = false;
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(sourceFlag);
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(targetFlag);
        } else if (FROM_REGION.equals("[NOT SELECTED]")) {
            check_countries = false;
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(sourceFlag);
        } else if (TO_REGION.equals("[NOT SELECTED]")) {
            check_countries = false;
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(targetFlag);
        }

        if (CHOSEN_SIZE.equals("[NOT SELECTED]") || CHOSEN_SIZE.equals("•••")) {
            check_size = false;
        }

        if (!check_countries) {
            return;
        }

        if (!check_size) {
            highlightSize(true);
            return;
        }

        TextView textView = findViewById(R.id.result);
        String resultMsg;
        if (FROM_REGION.equals(TO_REGION)) {
            resultMsg = "Please select a different country for the size conversion";
        } else {
            String resSize = calculateSize();
            if(resSize.isEmpty()) {
                resultMsg = "Size not found, sorry :(";
            } else {
                resultMsg = String.format("Your %s size %s would be %s in %s",
                        FROM_REGION, CHOSEN_SIZE, resSize, TO_REGION);
            }
        }

        textView.setText(resultMsg);
        YoYo.with(Techniques.Landing).duration(700).playOn(textView);
    }

    private String calculateSize() {
        // Getting all clothings
        List<Clothing> clothings = db.getAllClothingItems();
        String resSize = "";
        for (Clothing cn : clothings) {
            if((cn.getDept().equals(DEPARTMENT) ||
                    cn.getDept().equals("Unisex")) &&
                    cn.getClothing().equals(CLOTHING_TYPE) &&
                    (cn.getSizes().contains(FROM_REGION+":"+CHOSEN_SIZE+",") ||
                            cn.getSizes().contains(INT_REGION +":"+CHOSEN_SIZE+",")))
            {
                Pattern pattern = Pattern.compile(TO_REGION+":([^,]+)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(cn.getSizes());
                while (matcher.find()) {
                    // add "or" divider if there are sizes in the list already
                    if(!(resSize.isEmpty())) {
                        resSize += " or ";
                    }
                    resSize += matcher.group(1);
                }
                // add international size for non-asian countries
                if(!AsianCountries.contains(TO_REGION) && !CHOSEN_SIZE.contains("Int. size")) {
                    Pattern patternInt = Pattern.compile(INT_REGION+":([^,]+)", Pattern.CASE_INSENSITIVE);
                    Matcher matcherInt = patternInt.matcher(cn.getSizes());
                    if (matcherInt.find()) {
                        if(!(resSize.isEmpty())) {
                            resSize += " or ";
                        }
                        resSize += matcherInt.group(1);
                    }
                }
            }
        }
        return resSize;
    }

    private void displayAppInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About")
                .setMessage(R.string.app_about)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateClothingsForDepartment(int resId) {
        String CLOTHING_TYPE_MEM = CLOTHING_TYPE;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                resId, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        clothingTypeSpinner.setAdapter(adapter);
        String[] new_clothes = getResources().getStringArray(resId);
        int new_pos = Arrays.asList(new_clothes).indexOf(CLOTHING_TYPE_MEM);
        if (new_pos != -1) {
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

    private List<String> getListOfRegions() {
        List<Clothing> clothings = db.getAllClothingItems();
        List<String> regions = new ArrayList<>();
        for (Clothing cn : clothings) {
            if((cn.getDept().equals(DEPARTMENT) || cn.getDept().equals("Unisex")) &&
                    cn.getClothing().equals(CLOTHING_TYPE))
            {
                Pattern pattern = Pattern.compile("([^,]+):([^,]+)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(cn.getSizes());
                while (matcher.find()) {
                    if(!regions.contains(matcher.group(1))
                            && !matcher.group(1).equals(INT_REGION)) regions.add(matcher.group(1));
                }
                return regions;
            }
        }
        return null;
    }

    private List<String> getListOfSizes(){
        // Reading all sizes
        List<Clothing> clothings = db.getAllClothingItems();
        List<String> sizes = new ArrayList<>();
        sizes.add("•••");
        if(FROM_REGION.equals("[NOT SELECTED]")) return sizes;
        for (Clothing cn : clothings) {
            if((cn.getDept().equals(DEPARTMENT) || cn.getDept().equals("Unisex")) &&
                    cn.getClothing().equals(CLOTHING_TYPE))
            {
                Pattern pattern;
                // No international alpha sizes for Asian countries
                if (AsianCountries.contains(FROM_REGION))
                {
                    pattern = Pattern.compile("("+FROM_REGION+")"+":([^,]+)", Pattern.CASE_INSENSITIVE);
                } else {
                    pattern = Pattern.compile("("+FROM_REGION+"|"+ INT_REGION +")"+":([^,]+)", Pattern.CASE_INSENSITIVE);
                }
                Matcher matcher = pattern.matcher(cn.getSizes());
                while (matcher.find()) {
                    if(!sizes.contains(matcher.group(2))) sizes.add(matcher.group(2));
                }
            }
        }
        return sizes;
    }

    private void resetConversionDirection() {
        // clear conversion result field
        TextView textView = findViewById(R.id.result);
        textView.setText("•••");

        // memorize previously selected regions
        String FROM_REGION_MEM = FROM_REGION;
        String TO_REGION_MEM = TO_REGION;
        int new_from_reg_pos = regions.indexOf(FROM_REGION_MEM);
        int new_to_reg_pos = regions.indexOf(TO_REGION_MEM);

        // clear region selections in both drawers
        FROM_REGION = "[NOT SELECTED]";
        TO_REGION = "[NOT SELECTED]";
        mLeftDrawerList.clearChoices();
        mDrawerLayout.closeDrawer(mLeftDrawerList);
        mRightDrawerList.clearChoices();
        mDrawerLayout.closeDrawer(mRightDrawerList);

        // if previously selected region is not found in regions list, then
        // if it's a European country, replace it with Europe
        if (new_from_reg_pos == -1) {
            if(EuropeanCountries.contains(FROM_REGION_MEM)) {
                FROM_REGION_MEM = "Europe";
                new_from_reg_pos = regions.indexOf(FROM_REGION_MEM);
            }
        }
        if (new_to_reg_pos == -1) {
            if(EuropeanCountries.contains(TO_REGION_MEM)) {
                TO_REGION_MEM = "Europe";
                new_to_reg_pos = regions.indexOf(TO_REGION_MEM);
            }
        }

        int id;
        // set new regions in both drawers (or keep the old ones if they exist in the new regions list)
        if (new_from_reg_pos != -1) {
            FROM_REGION = FROM_REGION_MEM;
            mLeftDrawerList.setItemChecked(new_from_reg_pos, true);

            id = mapOfFlagIcons.get(FROM_REGION);
            YoYo.with(Techniques.FadeIn).duration(700).playOn(sourceFlag);
            sourceFlag.setImageResource(id);
        }
        if (new_to_reg_pos != -1) {
            TO_REGION = TO_REGION_MEM;
            mRightDrawerList.setItemChecked(new_to_reg_pos, true);
            id = mapOfFlagIcons.get(TO_REGION);
            YoYo.with(Techniques.FadeIn).duration(700).playOn(targetFlag);
            targetFlag.setImageResource(id);
        }

        // reset size field
        SIZE = "[NOT SELECTED]";
        CHOSEN_SIZE = "[NOT SELECTED]";
        updateSizeItems();
        highlightSize(false);

        // display arrow icon
        YoYo.with(Techniques.FadeIn).duration(700).playOn(convArrow);
        convArrow.setImageResource(R.drawable.arrow);

        // display a question mark icon when region is not selected
        id = R.drawable.question;
        if(FROM_REGION.equals("[NOT SELECTED]")){
            YoYo.with(Techniques.FadeIn).duration(700).playOn(sourceFlag);
            sourceFlag.setImageResource(id);
        }
        if(TO_REGION.equals("[NOT SELECTED]")) {
            YoYo.with(Techniques.FadeIn).duration(700).playOn(targetFlag);
            targetFlag.setImageResource(id);
        }
    }

    private void updateDrawerItems(){
        regions = getListOfRegions();

        // add FROM regions to the left drawer
        adapterLeft.clear();
        adapterLeft.addAll(regions);
        adapterLeft.notifyDataSetChanged();

        // add TO regions to the right drawer
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
            int image = R.drawable.fedora;
            if(DEPARTMENT.equals(context.getResources().getString(R.string.department_women))) {
                CLOTHING_TYPE = mClothingTypeWomen[pos];
                image = mapOfWomenClothingIcons.get(CLOTHING_TYPE);
            }
            if(DEPARTMENT.equals(context.getResources().getString(R.string.department_men))) {
                CLOTHING_TYPE = mClothingTypeMen[pos];
                image = mapOfMenClothingIcons.get(CLOTHING_TYPE);
            }
            updateDrawerItems();
            YoYo.with(Techniques.FadeIn).duration(700).playOn(clothingImage);
            clothingImage.setImageResource(image);
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
        int id = mapOfFlagIcons.get(FROM_REGION);
        YoYo.with(Techniques.FadeIn).duration(700).playOn(sourceFlag);
        sourceFlag.setImageResource(id);
    }

    private void selectItemRightPane(int position) {
        TO_REGION = regions.get(position);
        mRightDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mRightDrawerList);
        int id = mapOfFlagIcons.get(TO_REGION);
        YoYo.with(Techniques.FadeIn).duration(700).playOn(targetFlag);
        targetFlag.setImageResource(id);
    }

    private void highlightSize(Boolean status){
        TextView textView = findViewById(R.id.result);
        textView.setText("•••");
        TextView size_label = findViewById(R.id.size_label);
        if (status) {
            size_label.setTextColor(Color.parseColor("#ff4500"));
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
