package com.anahorn.fukusizer

import java.util.regex.Pattern

/**
 * Manages region and size data retrieval from the database.
 */
class RegionManager(
    private val db: DatabaseHandler,
    private val asianCountries: List<String>
) {
    companion object {
        private const val INT_REGION = "International"
    }

    /**
     * Retrieves list of available regions for the given department and clothing type.
     */
    fun getRegions(department: String, clothingType: String): MutableList<String> {
        val clothings = db.getAllClothingItems()
        val regions = mutableListOf<String>()

        for (cn in clothings) {
            if ((cn.dept == department || cn.dept == "Unisex") && cn.clothing == clothingType) {
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

    /**
     * Retrieves list of available sizes for the given region, department, and clothing type.
     */
    fun getSizes(
        fromRegion: String,
        department: String,
        clothingType: String,
        placeholder: String,
        unselectedPlaceholder: String
    ): MutableList<String> {
        val clothings = db.getAllClothingItems()
        val sizes = mutableListOf<String>()
        sizes.add(placeholder)

        if (fromRegion == unselectedPlaceholder) return sizes

        for (cn in clothings) {
            if ((cn.dept == department || cn.dept == "Unisex") && cn.clothing == clothingType) {
                val pattern = if (asianCountries.contains(fromRegion)) {
                    // No international alpha sizes for Asian countries
                    Pattern.compile("($fromRegion):([^,]+)", Pattern.CASE_INSENSITIVE)
                } else {
                    Pattern.compile("($fromRegion|$INT_REGION):([^,]+)", Pattern.CASE_INSENSITIVE)
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

    /**
     * Finds the new position of a region in the updated regions list.
     * If the region is European and not found, returns the position of "Europe".
     */
    fun findRegionPosition(
        region: String,
        regions: List<String>,
        europeanCountries: List<String>
    ): Int {
        var position = regions.indexOf(region)
        if (position == -1 && europeanCountries.contains(region)) {
            position = regions.indexOf("Europe")
        }
        return position
    }
}

