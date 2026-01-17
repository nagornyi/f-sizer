package com.anahorn.fukusizer

import android.app.AlertDialog
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import androidx.core.content.edit
import androidx.core.graphics.toColorInt

class MainActivity : AppCompatActivity() {
    // Core dependencies
    private lateinit var db: DatabaseHandler
    private lateinit var prefs: SharedPreferences
    private lateinit var sizeConverter: SizeConverter
    private lateinit var regionManager: RegionManager

    // UI Components
    private lateinit var mAdView: AdView
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mLeftDrawerList: ListView
    private lateinit var mRightDrawerList: ListView
    private lateinit var clothingTypeSpinner: Spinner
    private lateinit var sizeSpinner: Spinner

    // Data
    private lateinit var mClothingTypeMen: Array<String>
    private lateinit var mClothingTypeWomen: Array<String>
    private lateinit var adapterLeft: ArrayAdapter<String>
    private lateinit var adapterRight: ArrayAdapter<String>
    private lateinit var adapterSize: ArrayAdapter<String>
    private var regions = mutableListOf<String>()
    private var sizes = mutableListOf<String>()

    // Views
    private lateinit var clothingImage: ImageView
    private lateinit var sourceFlag: ImageView
    private lateinit var targetFlag: ImageView
    private lateinit var convArrow: ImageView
    private lateinit var convertButton: Button
    lateinit var genderSelector: MenuItem
    lateinit var appInformation: MenuItem

    // Configuration
    private lateinit var europeanCountries: List<String>
    private lateinit var asianCountries: List<String>
    private lateinit var mapOfFlagIcons: Map<String, Int>
    private lateinit var mapOfWomenClothingIcons: Map<String, Int>
    private lateinit var mapOfMenClothingIcons: Map<String, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        // Get the department choice from preferences
        DEPARTMENT = prefs.getString(KEY_SELECTED_DEPARTMENT, resources.getString(R.string.department_women)) ?: resources.getString(R.string.department_women)

        // Reset the DB recreated flag on every app start (hot-reload included)
        prefs.edit { putBoolean(KEY_DB_RECREATED, false) }

        // Initialize the DatabaseHandler instance only once
        db = DatabaseHandler(this)

        europeanCountries = resources.getStringArray(R.array.european_countries).toList()
        asianCountries = resources.getStringArray(R.array.asian_countries).toList()

        // Initialize helper classes
        sizeConverter = SizeConverter(db, asianCountries)
        regionManager = RegionManager(db, asianCountries)

        mapOfFlagIcons = IconHelpers.getMapOfFlagIcons()
        mapOfWomenClothingIcons = IconHelpers.getMapOfWomenClothingIcons()
        mapOfMenClothingIcons = IconHelpers.getMapOfMenClothingIcons()

        setContentView(R.layout.activity_main)

        // Set system bars to use dark content (dark icons/text) for light background
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = true
        windowInsetsController.isAppearanceLightNavigationBars = true

        mDrawerLayout = findViewById(R.id.drawer_layout)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val backgroundImage = findViewById<View>(R.id.main_page)
        val background = backgroundImage.background
        background.alpha = 10
        val buttonPressAnimation = AnimationUtils.loadAnimation(this, R.anim.button_press)

        if (db.getAllClothingItems().isEmpty()) Data.initializeSizes(this)

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        mLeftDrawerList = findViewById(R.id.left_drawer)

        // clothing type spinner
        clothingTypeSpinner = findViewById(R.id.clothing_type_spinner)
        val listOfClothes = if (DEPARTMENT == resources.getString(R.string.department_women)) {
            R.array.women_clothing
        } else {
            R.array.men_clothing
        }
        clothingTypeSpinner.adapter = AdapterUtils.createSpinnerAdapterFromResource(this, listOfClothes)
        clothingTypeSpinner.onItemSelectedListener = SpinnerListeners.createClothingSpinnerListener { pos ->
            onClothingTypeSelected(pos)
        }

        // size spinner
        sizeSpinner = findViewById(R.id.size_spinner)
        sizes = regionManager.getSizes(
            FROM_REGION,
            DEPARTMENT,
            CLOTHING_TYPE,
            getString(R.string.result_placeholder),
            getString(R.string.unselected_placeholder)
        )
        adapterSize = AdapterUtils.createSpinnerAdapter(this, sizes)
        sizeSpinner.adapter = adapterSize
        sizeSpinner.onItemSelectedListener = SpinnerListeners.createSizeSpinnerListener { pos ->
            CHOSEN_SIZE = sizes[pos]
            highlightSize(false)
        }

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START)

        // Set the adapter for the list view
        regions = regionManager.getRegions(DEPARTMENT, CLOTHING_TYPE)
        adapterLeft = AdapterUtils.createDrawerAdapter(this, regions)
        mLeftDrawerList.adapter = adapterLeft
        mLeftDrawerList.onItemClickListener = SpinnerListeners.createDrawerItemClickListener { pos ->
            selectItemLeftPane(pos)
        }

        mRightDrawerList = findViewById(R.id.right_drawer)
        mClothingTypeMen = resources.getStringArray(R.array.men_clothing)
        mClothingTypeWomen = resources.getStringArray(R.array.women_clothing)

        // Set the adapter for the list view
        adapterRight = AdapterUtils.createDrawerAdapter(this, regions)
        mRightDrawerList.adapter = adapterRight
        mRightDrawerList.onItemClickListener = SpinnerListeners.createDrawerItemClickListener { pos ->
            selectItemRightPane(pos)
        }

        // Configure navigation drawer
        mDrawerLayout = findViewById(R.id.drawer_layout)

        if (savedInstanceState == null) {
            SIZE = getString(R.string.unselected_placeholder)
            CHOSEN_SIZE = getString(R.string.unselected_placeholder)
            FROM_REGION = "USA"
            TO_REGION = "Germany"
            if (DEPARTMENT == resources.getString(R.string.department_women)) CLOTHING_TYPE = mClothingTypeWomen[0]
            if (DEPARTMENT == resources.getString(R.string.department_men)) CLOTHING_TYPE = mClothingTypeMen[0]
        }

        sourceFlag = findViewById(R.id.sourceFlag)
        targetFlag = findViewById(R.id.targetFlag)
        convArrow = findViewById(R.id.arrow)
        clothingImage = findViewById(R.id.clothing_image)

        sourceFlag.setOnClickListener { mDrawerLayout.openDrawer(mLeftDrawerList) }
        targetFlag.setOnClickListener { mDrawerLayout.openDrawer(mRightDrawerList) }

        convertButton = findViewById(R.id.convert_button)
        convertButton.setOnClickListener { v ->
            v.startAnimation(buttonPressAnimation)
            convertSize()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu items for use in the action bar
        menuInflater.inflate(R.menu.main_activity_actions, menu)
        appInformation = menu.findItem(R.id.action_info)
        appInformation.setIcon(R.drawable.info)
        genderSelector = menu.findItem(R.id.action_gender)

        // Configure menu items with custom layout
        val womanItem = menu.findItem(R.id.womanGender)
        val manItem = menu.findItem(R.id.manGender)
        womanItem.setActionView(R.layout.custom_menu_item)
        manItem.setActionView(R.layout.custom_menu_item)
        if (DEPARTMENT == resources.getString(R.string.department_women)) {
            womanItem.isChecked = true
            genderSelector.setIcon(R.drawable.girl)
        } else {
            manItem.isChecked = true
            genderSelector.setIcon(R.drawable.boy)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        return when (item.itemId) {
            R.id.action_info -> {
                displayAppInfo()
                true
            }
            R.id.womanGender -> {
                updateClothingsForDepartment(R.array.women_clothing)
                DEPARTMENT = resources.getString(R.string.department_women)
                prefs.edit { putString(KEY_SELECTED_DEPARTMENT, DEPARTMENT) }
                genderSelector.setIcon(R.drawable.girl)
                updateDrawerItems()
                true
            }
            R.id.manGender -> {
                updateClothingsForDepartment(R.array.men_clothing)
                DEPARTMENT = resources.getString(R.string.department_men)
                prefs.edit { putString(KEY_SELECTED_DEPARTMENT, DEPARTMENT) }
                genderSelector.setIcon(R.drawable.boy)
                updateDrawerItems()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun convertSize() {
        val validation = sizeConverter.validateConversion(
            FROM_REGION,
            TO_REGION,
            CHOSEN_SIZE,
            getString(R.string.result_placeholder),
            getString(R.string.unselected_placeholder)
        )

        when (validation) {
            SizeConverter.ValidationResult.BOTH_REGIONS_MISSING -> {
                AnimationHelper.zoomIn(sourceFlag)
                AnimationHelper.zoomIn(targetFlag)
                return
            }
            SizeConverter.ValidationResult.SOURCE_REGION_MISSING -> {
                AnimationHelper.zoomIn(sourceFlag)
                return
            }
            SizeConverter.ValidationResult.TARGET_REGION_MISSING -> {
                AnimationHelper.zoomIn(targetFlag)
                return
            }
            SizeConverter.ValidationResult.SIZE_MISSING -> {
                highlightSize(true)
                return
            }
            SizeConverter.ValidationResult.SAME_REGIONS -> {
                showConversionResult("Please select a different country for the size conversion")
                return
            }
            SizeConverter.ValidationResult.VALID -> {
                val resSize = sizeConverter.calculateSize(
                    FROM_REGION, TO_REGION, CHOSEN_SIZE, DEPARTMENT, CLOTHING_TYPE
                )
                val resultMsg = if (resSize.isEmpty()) {
                    "Size not found, sorry :("
                } else {
                    "Your size: $resSize"
                }
                showConversionResult(resultMsg)
            }
        }
    }

    private fun showConversionResult(message: String) {
        val textView = findViewById<TextView>(R.id.result)
        textView.text = message
        AnimationHelper.landing(textView)
    }

    private fun displayAppInfo() {
        AlertDialog.Builder(this)
            .setTitle("About")
            .setMessage(R.string.app_about)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    fun updateClothingsForDepartment(resId: Int) {
        val clothingTypeMem = CLOTHING_TYPE
        val adapter = ArrayAdapter.createFromResource(
            this,
            resId,
            R.layout.spinner_item
        )
        adapter.setDropDownViewResource(R.layout.spinner_item)
        clothingTypeSpinner.adapter = adapter
        val newClothes = resources.getStringArray(resId)
        val newPos = newClothes.indexOf(clothingTypeMem)
        if (newPos != -1) {
            clothingTypeSpinner.setSelection(newPos)
            CLOTHING_TYPE = clothingTypeMem
        } else {
            CLOTHING_TYPE = newClothes[0]
        }
        val textView = findViewById<TextView>(R.id.result)
        textView.text = getString(R.string.result_placeholder)
    }

    private fun resetConversionDirection() {
        // clear conversion result field
        val textView = findViewById<TextView>(R.id.result)
        textView.text = getString(R.string.result_placeholder)

        // memorize previously selected regions
        val fromRegionMem = FROM_REGION
        val toRegionMem = TO_REGION
        val newFromRegPos = regionManager.findRegionPosition(fromRegionMem, regions, europeanCountries)
        val newToRegPos = regionManager.findRegionPosition(toRegionMem, regions, europeanCountries)

        // clear region selections in both drawers
        FROM_REGION = getString(R.string.unselected_placeholder)
        TO_REGION = getString(R.string.unselected_placeholder)
        mLeftDrawerList.clearChoices()
        mDrawerLayout.closeDrawer(mLeftDrawerList)
        mRightDrawerList.clearChoices()
        mDrawerLayout.closeDrawer(mRightDrawerList)

        // set new regions in both drawers (or keep the old ones if they exist in the new regions list)
        if (newFromRegPos != -1) {
            FROM_REGION = regions[newFromRegPos]
            mLeftDrawerList.setItemChecked(newFromRegPos, true)

            val id = mapOfFlagIcons[FROM_REGION] ?: R.drawable.question
            AdapterUtils.updateImageWithAnimation(sourceFlag, id)
        }
        if (newToRegPos != -1) {
            TO_REGION = regions[newToRegPos]
            mRightDrawerList.setItemChecked(newToRegPos, true)
            val id = mapOfFlagIcons[TO_REGION] ?: R.drawable.question
            AdapterUtils.updateImageWithAnimation(targetFlag, id)
        }

        // reset size field
        SIZE = getString(R.string.unselected_placeholder)
        CHOSEN_SIZE = getString(R.string.unselected_placeholder)
        updateSizeItems()
        highlightSize(false)

        // display arrow icon
        AdapterUtils.updateImageWithAnimation(convArrow, R.drawable.arrow)

        // display a question mark icon when region is not selected
        val questionId = R.drawable.question
        val unselectedPlaceholder = getString(R.string.unselected_placeholder)
        if (FROM_REGION == unselectedPlaceholder) {
            AdapterUtils.updateImageWithAnimation(sourceFlag, questionId)
        }
        if (TO_REGION == unselectedPlaceholder) {
            AdapterUtils.updateImageWithAnimation(targetFlag, questionId)
        }
    }

    private fun updateDrawerItems() {
        regions = regionManager.getRegions(DEPARTMENT, CLOTHING_TYPE)

        // add FROM regions to the left drawer
        AdapterUtils.updateAdapter(adapterLeft, regions)

        // add TO regions to the right drawer
        AdapterUtils.updateAdapter(adapterRight, regions)

        resetConversionDirection()
    }

    private fun updateSizeItems() {
        sizes = regionManager.getSizes(
            FROM_REGION,
            DEPARTMENT,
            CLOTHING_TYPE,
            getString(R.string.result_placeholder),
            getString(R.string.unselected_placeholder)
        )
        AdapterUtils.updateAdapter(adapterSize, sizes)
        sizeSpinner.setSelection(0)
    }

    private fun onClothingTypeSelected(pos: Int) {
        var image = R.drawable.fedora
        if (DEPARTMENT == resources.getString(R.string.department_women)) {
            CLOTHING_TYPE = mClothingTypeWomen[pos]
            image = mapOfWomenClothingIcons[CLOTHING_TYPE] ?: R.drawable.fedora
        }
        if (DEPARTMENT == resources.getString(R.string.department_men)) {
            CLOTHING_TYPE = mClothingTypeMen[pos]
            image = mapOfMenClothingIcons[CLOTHING_TYPE] ?: R.drawable.fedora
        }
        updateDrawerItems()
        AdapterUtils.updateImageWithAnimation(clothingImage, image)
    }

    private fun selectItemLeftPane(position: Int) {
        FROM_REGION = regions[position]
        mLeftDrawerList.setItemChecked(position, true)
        mDrawerLayout.closeDrawer(mLeftDrawerList)
        updateSizeItems()
        val id = mapOfFlagIcons[FROM_REGION] ?: R.drawable.question
        AdapterUtils.updateImageWithAnimation(sourceFlag, id)
    }

    private fun selectItemRightPane(position: Int) {
        TO_REGION = regions[position]
        mRightDrawerList.setItemChecked(position, true)
        mDrawerLayout.closeDrawer(mRightDrawerList)
        val id = mapOfFlagIcons[TO_REGION] ?: R.drawable.question
        AdapterUtils.updateImageWithAnimation(targetFlag, id)
    }

    private fun highlightSize(status: Boolean) {
        val textView = findViewById<TextView>(R.id.result)
        textView.text = getString(R.string.result_placeholder)
        val sizeLabel = findViewById<TextView>(R.id.size_label)
        if (status) {
            sizeLabel.setTextColor("#ff4500".toColorInt())
            AnimationHelper.zoomIn(sizeLabel)
        } else {
            sizeLabel.setTextColor(Color.BLACK)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    companion object {
        var SIZE = ""
        var CHOSEN_SIZE = ""
        var FROM_REGION = "USA"
        var TO_REGION = "Germany"
        var CLOTHING_TYPE = "Tops"
        var DEPARTMENT: String = ""

        const val PREFS_NAME = "ApplicationPrefs"
        const val KEY_DB_RECREATED = "db_recreated"
        const val KEY_SELECTED_DEPARTMENT = "selected_department"
    }
}

