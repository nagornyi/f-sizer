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
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.regex.Pattern
import androidx.core.content.edit
import androidx.core.graphics.toColorInt

class MainActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHandler
    private lateinit var prefs: SharedPreferences
    private lateinit var mAdView: AdView

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mLeftDrawerList: ListView
    private lateinit var mRightDrawerList: ListView
    private lateinit var clothingTypeSpinner: Spinner
    private lateinit var sizeSpinner: Spinner
    private lateinit var mClothingTypeMen: Array<String>
    private lateinit var mClothingTypeWomen: Array<String>
    private var mTitle: CharSequence? = null
    private lateinit var adapterLeft: ArrayAdapter<String>
    private lateinit var adapterRight: ArrayAdapter<String>
    private lateinit var adapterSize: ArrayAdapter<String>
    private var regions = mutableListOf<String>()
    private var sizes = mutableListOf<String>()

    private lateinit var clothingImage: ImageView
    private lateinit var sourceFlag: ImageView
    private lateinit var targetFlag: ImageView
    private lateinit var convArrow: ImageView
    private lateinit var convertButton: Button

    lateinit var genderSelector: MenuItem
    lateinit var appInformation: MenuItem

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

        mTitle = title
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        mLeftDrawerList = findViewById(R.id.left_drawer)

        // clothing type spinner
        clothingTypeSpinner = findViewById(R.id.clothing_type_spinner)
        val listOfClothes = if (DEPARTMENT == resources.getString(R.string.department_women)) {
            R.array.women_clothing
        } else {
            R.array.men_clothing
        }

        // Create an ArrayAdapter using the string array and clothingTypeSpinner layout
        val adapter = ArrayAdapter.createFromResource(
            this,
            listOfClothes,
            R.layout.spinner_item
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item)
        // Apply the adapter to the clothingTypeSpinner
        clothingTypeSpinner.adapter = adapter
        clothingTypeSpinner.onItemSelectedListener = ClothingSpinnerActivity()

        // size spinner
        sizeSpinner = findViewById(R.id.size_spinner)
        sizes = getListOfSizes()
        adapterSize = ArrayAdapter(this, R.layout.spinner_item, sizes)
        adapterSize.setDropDownViewResource(R.layout.spinner_item)
        sizeSpinner.adapter = adapterSize
        sizeSpinner.onItemSelectedListener = SizeSpinnerActivity()

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START)

        // Set the adapter for the list view
        regions = getListOfRegions()
        adapterLeft = ArrayAdapter(this, R.layout.drawer_list_item, regions)
        mLeftDrawerList.adapter = adapterLeft
        // Set the list's click listener
        mLeftDrawerList.onItemClickListener = LeftDrawerItemClickListener()

        mRightDrawerList = findViewById(R.id.right_drawer)
        mClothingTypeMen = resources.getStringArray(R.array.men_clothing)
        mClothingTypeWomen = resources.getStringArray(R.array.women_clothing)

        // Set the adapter for the list view
        adapterRight = ArrayAdapter(this, R.layout.drawer_list_item, regions)
        mRightDrawerList.adapter = adapterRight
        // Set the list's click listener
        mRightDrawerList.onItemClickListener = RightDrawerItemClickListener()

        // Configure navigation drawer
        mDrawerLayout = findViewById(R.id.drawer_layout)

        if (savedInstanceState == null) {
            SIZE = "[NOT SELECTED]"
            CHOSEN_SIZE = "[NOT SELECTED]"
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
        var checkCountries = true
        var checkSize = true

        if (FROM_REGION == "[NOT SELECTED]" && TO_REGION == "[NOT SELECTED]") {
            checkCountries = false
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(sourceFlag)
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(targetFlag)
        } else if (FROM_REGION == "[NOT SELECTED]") {
            checkCountries = false
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(sourceFlag)
        } else if (TO_REGION == "[NOT SELECTED]") {
            checkCountries = false
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(targetFlag)
        }

        if (CHOSEN_SIZE == "[NOT SELECTED]" || CHOSEN_SIZE == "•••") {
            checkSize = false
        }

        if (!checkCountries) {
            return
        }

        if (!checkSize) {
            highlightSize(true)
            return
        }

        val textView = findViewById<TextView>(R.id.result)
        val resultMsg = if (FROM_REGION == TO_REGION) {
            "Please select a different country for the size conversion"
        } else {
            val resSize = calculateSize()
            if (resSize.isEmpty()) {
                "Size not found, sorry :("
            } else {
                "Your size: $resSize"
            }
        }

        textView.text = resultMsg
        YoYo.with(Techniques.Landing).duration(700).playOn(textView)
    }

    private fun calculateSize(): String {
        // Getting all clothings
        val clothings = db.getAllClothingItems()
        var resSize = ""
        for (cn in clothings) {
            if ((cn.dept == DEPARTMENT || cn.dept == "Unisex") &&
                cn.clothing == CLOTHING_TYPE &&
                (cn.sizes.contains("$FROM_REGION:$CHOSEN_SIZE,") ||
                        cn.sizes.contains("$INT_REGION:$CHOSEN_SIZE,"))
            ) {
                val pattern = Pattern.compile("$TO_REGION:([^,]+)", Pattern.CASE_INSENSITIVE)
                val matcher = pattern.matcher(cn.sizes)
                while (matcher.find()) {
                    // add "or" divider if there are sizes in the list already
                    if (resSize.isNotEmpty()) {
                        resSize += " or "
                    }
                    resSize += matcher.group(1)
                }
                // add international size for non-asian countries
                if (!asianCountries.contains(TO_REGION) && !CHOSEN_SIZE.contains("Int. size")) {
                    val patternInt = Pattern.compile("$INT_REGION:([^,]+)", Pattern.CASE_INSENSITIVE)
                    val matcherInt = patternInt.matcher(cn.sizes)
                    if (matcherInt.find()) {
                        if (resSize.isNotEmpty()) {
                            resSize += " or "
                        }
                        resSize += matcherInt.group(1)
                    }
                }
            }
        }
        return resSize
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
        textView.text = "•••"
    }

    private inner class LeftDrawerItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            selectItemLeftPane(position)
        }
    }

    private inner class RightDrawerItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            selectItemRightPane(position)
        }
    }

    private fun getListOfRegions(): MutableList<String> {
        val clothings = db.getAllClothingItems()
        val regions = mutableListOf<String>()
        for (cn in clothings) {
            if ((cn.dept == DEPARTMENT || cn.dept == "Unisex") &&
                cn.clothing == CLOTHING_TYPE
            ) {
                val pattern = Pattern.compile("([^,]+):([^,]+)", Pattern.CASE_INSENSITIVE)
                val matcher = pattern.matcher(cn.sizes)
                while (matcher.find()) {
                    val region = matcher.group(1) ?: continue
                    if (!regions.contains(region) && region != INT_REGION) {
                        regions.add(region)
                    }
                }
                return regions
            }
        }
        return regions
    }

    private fun getListOfSizes(): MutableList<String> {
        // Reading all sizes
        val clothings = db.getAllClothingItems()
        val sizes = mutableListOf<String>()
        sizes.add("•••")
        if (FROM_REGION == "[NOT SELECTED]") return sizes
        for (cn in clothings) {
            if ((cn.dept == DEPARTMENT || cn.dept == "Unisex") &&
                cn.clothing == CLOTHING_TYPE
            ) {
                val pattern = if (asianCountries.contains(FROM_REGION)) {
                    // No international alpha sizes for Asian countries
                    Pattern.compile("($FROM_REGION):([^,]+)", Pattern.CASE_INSENSITIVE)
                } else {
                    Pattern.compile("($FROM_REGION|$INT_REGION):([^,]+)", Pattern.CASE_INSENSITIVE)
                }
                val matcher = pattern.matcher(cn.sizes)
                while (matcher.find()) {
                    val size = matcher.group(2) ?: continue
                    if (!sizes.contains(size)) sizes.add(size)
                }
            }
        }
        return sizes
    }

    private fun resetConversionDirection() {
        // clear conversion result field
        val textView = findViewById<TextView>(R.id.result)
        textView.text = "•••"

        // memorize previously selected regions
        val fromRegionMem = FROM_REGION
        val toRegionMem = TO_REGION
        var newFromRegPos = regions.indexOf(fromRegionMem)
        var newToRegPos = regions.indexOf(toRegionMem)

        // clear region selections in both drawers
        FROM_REGION = "[NOT SELECTED]"
        TO_REGION = "[NOT SELECTED]"
        mLeftDrawerList.clearChoices()
        mDrawerLayout.closeDrawer(mLeftDrawerList)
        mRightDrawerList.clearChoices()
        mDrawerLayout.closeDrawer(mRightDrawerList)

        // if previously selected region is not found in regions list, then
        // if it's a European country, replace it with Europe
        if (newFromRegPos == -1) {
            if (europeanCountries.contains(fromRegionMem)) {
                newFromRegPos = regions.indexOf("Europe")
            }
        }
        if (newToRegPos == -1) {
            if (europeanCountries.contains(toRegionMem)) {
                newToRegPos = regions.indexOf("Europe")
            }
        }

        // set new regions in both drawers (or keep the old ones if they exist in the new regions list)
        if (newFromRegPos != -1) {
            FROM_REGION = regions[newFromRegPos]
            mLeftDrawerList.setItemChecked(newFromRegPos, true)

            val id = mapOfFlagIcons[FROM_REGION] ?: R.drawable.question
            YoYo.with(Techniques.FadeIn).duration(700).playOn(sourceFlag)
            sourceFlag.setImageResource(id)
        }
        if (newToRegPos != -1) {
            TO_REGION = regions[newToRegPos]
            mRightDrawerList.setItemChecked(newToRegPos, true)
            val id = mapOfFlagIcons[TO_REGION] ?: R.drawable.question
            YoYo.with(Techniques.FadeIn).duration(700).playOn(targetFlag)
            targetFlag.setImageResource(id)
        }

        // reset size field
        SIZE = "[NOT SELECTED]"
        CHOSEN_SIZE = "[NOT SELECTED]"
        updateSizeItems()
        highlightSize(false)

        // display arrow icon
        YoYo.with(Techniques.FadeIn).duration(700).playOn(convArrow)
        convArrow.setImageResource(R.drawable.arrow)

        // display a question mark icon when region is not selected
        val questionId = R.drawable.question
        if (FROM_REGION == "[NOT SELECTED]") {
            YoYo.with(Techniques.FadeIn).duration(700).playOn(sourceFlag)
            sourceFlag.setImageResource(questionId)
        }
        if (TO_REGION == "[NOT SELECTED]") {
            YoYo.with(Techniques.FadeIn).duration(700).playOn(targetFlag)
            targetFlag.setImageResource(questionId)
        }
    }

    private fun updateDrawerItems() {
        regions = getListOfRegions()

        // add FROM regions to the left drawer
        adapterLeft.clear()
        adapterLeft.addAll(regions)
        adapterLeft.notifyDataSetChanged()

        // add TO regions to the right drawer
        adapterRight.clear()
        adapterRight.addAll(regions)
        adapterRight.notifyDataSetChanged()

        resetConversionDirection()
    }

    private fun updateSizeItems() {
        sizes = getListOfSizes()
        adapterSize.clear()
        adapterSize.addAll(sizes)
        adapterSize.notifyDataSetChanged()
        sizeSpinner.setSelection(0)
    }

    private inner class ClothingSpinnerActivity : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
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
            YoYo.with(Techniques.FadeIn).duration(700).playOn(clothingImage)
            clothingImage.setImageResource(image)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Another interface callback
        }
    }

    private inner class SizeSpinnerActivity : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            CHOSEN_SIZE = sizes[pos]
            highlightSize(false)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Another interface callback
        }
    }

    private fun selectItemLeftPane(position: Int) {
        FROM_REGION = regions[position]
        mLeftDrawerList.setItemChecked(position, true)
        mDrawerLayout.closeDrawer(mLeftDrawerList)
        updateSizeItems()
        val id = mapOfFlagIcons[FROM_REGION] ?: R.drawable.question
        YoYo.with(Techniques.FadeIn).duration(700).playOn(sourceFlag)
        sourceFlag.setImageResource(id)
    }

    private fun selectItemRightPane(position: Int) {
        TO_REGION = regions[position]
        mRightDrawerList.setItemChecked(position, true)
        mDrawerLayout.closeDrawer(mRightDrawerList)
        val id = mapOfFlagIcons[TO_REGION] ?: R.drawable.question
        YoYo.with(Techniques.FadeIn).duration(700).playOn(targetFlag)
        targetFlag.setImageResource(id)
    }

    private fun highlightSize(status: Boolean) {
        val textView = findViewById<TextView>(R.id.result)
        textView.text = "•••"
        val sizeLabel = findViewById<TextView>(R.id.size_label)
        if (status) {
            sizeLabel.setTextColor("#ff4500".toColorInt())
            YoYo.with(Techniques.ZoomIn).duration(700).playOn(sizeLabel)
        } else {
            sizeLabel.setTextColor(Color.BLACK)
        }
    }

    override fun setTitle(title: CharSequence?) {
        mTitle = title
        supportActionBar?.title = mTitle
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    companion object {

        var SIZE = "[NOT SELECTED]"
        var CHOSEN_SIZE = "[NOT SELECTED]"
        var INT_REGION = "International"
        var FROM_REGION = "USA"
        var TO_REGION = "Germany"
        var CLOTHING_TYPE = "Tops"
        var DEPARTMENT: String = ""

        const val PREFS_NAME = "ApplicationPrefs"
        const val KEY_DB_RECREATED = "db_recreated"
        const val KEY_SELECTED_DEPARTMENT = "selected_department"
    }
}

