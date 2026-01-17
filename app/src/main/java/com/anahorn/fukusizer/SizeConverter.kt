package com.anahorn.fukusizer

import androidx.core.text.HtmlCompat
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
        const val SIZE_SEPARATOR = "<br>• "
    }

    /**
     * Use green text with bold and underline for visibility
     */
    private fun wrapInGreenBorder(text: String): String {
        return "<font color=\"#4CAF50\"><b><u>[$text]</u></b></font>"
    }

    /**
     * Calculates the converted size from one region to another.
     *
     * @param fromRegion Source region
     * @param toRegion Target region
     * @param chosenSize Size to convert
     * @param department Clothing department (Men/Women/Unisex)
     * @param clothingType Type of clothing (Tops, Bottoms, etc.)
     * @return Converted size CharSequence with styled international sizes, or empty if not found
     */
    fun calculateSize(
        fromRegion: String,
        toRegion: String,
        chosenSize: String,
        department: String,
        clothingType: String
    ): CharSequence {
        val clothings = db.getAllClothingItems()
        val sizesList = mutableListOf<Pair<String, String?>>() // Pair of (regional size, international size or null)

        for (cn in clothings) {
            if ((cn.dept == department || cn.dept == "Unisex") &&
                cn.clothing == clothingType &&
                (cn.sizes.contains("$fromRegion:$chosenSize,") ||
                        cn.sizes.contains("$INT_REGION:$chosenSize,"))
            ) {
                val pattern = Pattern.compile("$toRegion:([^,]+)", Pattern.CASE_INSENSITIVE)
                val matcher = pattern.matcher(cn.sizes)

                // Collect all regional sizes
                val regionalSizes = mutableListOf<String>()
                while (matcher.find()) {
                    val size = matcher.group(1)
                    if (size != null && !regionalSizes.contains(size)) {
                        regionalSizes.add(size)
                    }
                }

                // Get international size for non-asian countries
                var intSize: String? = null
                if (!asianCountries.contains(toRegion) && !chosenSize.contains("Int. size")) {
                    val patternInt = Pattern.compile("$INT_REGION:([^,]+)", Pattern.CASE_INSENSITIVE)
                    val matcherInt = patternInt.matcher(cn.sizes)
                    if (matcherInt.find()) {
                        intSize = matcherInt.group(1)?.trim()
                        // Clean up the international size text (remove any "(Int. size)" suffix if present)
                        intSize = intSize?.replace(Regex("\\s*\\(Int\\.?\\s*size\\)", RegexOption.IGNORE_CASE), "")?.trim()
                    }
                }

                // Add to sizes list - if we only have one regional size, pair it with int size
                // If multiple regional sizes, add int size as separate item
                if (regionalSizes.isNotEmpty()) {
                    if (regionalSizes.size == 1 && intSize != null) {
                        sizesList.add(Pair(regionalSizes[0], intSize))
                    } else {
                        regionalSizes.forEach { regional ->
                            if (!sizesList.any { it.first == regional }) {
                                sizesList.add(Pair(regional, null))
                            }
                        }
                        if (intSize != null && !sizesList.any { it.first == intSize }) {
                            sizesList.add(Pair(intSize, null))
                        }
                    }
                }
            }
        }

        // Build HTML string with green borders around international sizes
        if (sizesList.isEmpty()) {
            return ""
        }

        val htmlBuilder = StringBuilder()
        sizesList.forEachIndexed { index, (regional, international) ->
            if (index > 0) {
                htmlBuilder.append(SIZE_SEPARATOR)
            }

            // Add regional size
            htmlBuilder.append(regional)

            // Add international size with green border if present
            if (international != null) {
                htmlBuilder.append(" ")
                htmlBuilder.append(wrapInGreenBorder(international))
            }
        }

        // Convert HTML to Spanned for TextView using HtmlCompat for backward compatibility
        return HtmlCompat.fromHtml(htmlBuilder.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
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

