package com.anahorn.fukusizer

import java.util.regex.Pattern

/**
 * Handles size conversion logic between regions.
 */
class SizeConverter(
    private val db: DatabaseHandler,
    private val asianCountries: List<String>
) {
    companion object {
        const val INT_REGION = "International"
    }

    /**
     * Calculates the converted size from one region to another.
     *
     * @param fromRegion Source region
     * @param toRegion Target region
     * @param chosenSize Size to convert
     * @param department Clothing department (Men/Women/Unisex)
     * @param clothingType Type of clothing (Tops, Bottoms, etc.)
     * @return Converted size string or empty if not found
     */
    fun calculateSize(
        fromRegion: String,
        toRegion: String,
        chosenSize: String,
        department: String,
        clothingType: String
    ): String {
        val clothings = db.getAllClothingItems()
        var resSize = ""

        for (cn in clothings) {
            if ((cn.dept == department || cn.dept == "Unisex") &&
                cn.clothing == clothingType &&
                (cn.sizes.contains("$fromRegion:$chosenSize,") ||
                        cn.sizes.contains("$INT_REGION:$chosenSize,"))
            ) {
                val pattern = Pattern.compile("$toRegion:([^,]+)", Pattern.CASE_INSENSITIVE)
                val matcher = pattern.matcher(cn.sizes)
                while (matcher.find()) {
                    // add "or" divider if there are sizes in the list already
                    if (resSize.isNotEmpty()) {
                        resSize += " or "
                    }
                    resSize += matcher.group(1)
                }
                // add international size for non-asian countries
                if (!asianCountries.contains(toRegion) && !chosenSize.contains("Int. size")) {
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

    /**
     * Validates if the conversion can proceed.
     *
     * @return ValidationResult indicating what's missing
     */
    fun validateConversion(
        fromRegion: String,
        toRegion: String,
        chosenSize: String,
        placeholder: String,
        unselectedPlaceholder: String
    ): ValidationResult {
        return when {
            fromRegion == unselectedPlaceholder && toRegion == unselectedPlaceholder ->
                ValidationResult.BOTH_REGIONS_MISSING

            fromRegion == unselectedPlaceholder ->
                ValidationResult.SOURCE_REGION_MISSING

            toRegion == unselectedPlaceholder ->
                ValidationResult.TARGET_REGION_MISSING

            fromRegion == toRegion ->
                ValidationResult.SAME_REGIONS

            chosenSize == unselectedPlaceholder || chosenSize == placeholder ->
                ValidationResult.SIZE_MISSING

            else -> ValidationResult.VALID
        }
    }

    enum class ValidationResult {
        VALID,
        BOTH_REGIONS_MISSING,
        SOURCE_REGION_MISSING,
        TARGET_REGION_MISSING,
        SAME_REGIONS,
        SIZE_MISSING
    }
}

