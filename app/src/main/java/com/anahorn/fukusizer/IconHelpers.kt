package com.anahorn.fukusizer

object IconHelpers {
    fun getMapOfFlagIcons(): Map<String, Int> {
        return mapOf(
            "Australia" to R.drawable.australia,
            "Belgium" to R.drawable.belgium,
            "China" to R.drawable.china,
            "Europe" to R.drawable.europe,
            "France" to R.drawable.france,
            "Germany" to R.drawable.germany,
            "India" to R.drawable.india,
            "Italy" to R.drawable.italy,
            "Japan" to R.drawable.japan,
            "Korea" to R.drawable.korea,
            "New Zealand" to R.drawable.newzealand,
            "Spain" to R.drawable.spain,
            "UK" to R.drawable.uk,
            "USA" to R.drawable.usa
        )
    }

    fun getMapOfWomenClothingIcons(): Map<String, Int> {
        return mapOf(
            "Tops" to R.drawable.tops_women,
            "Bottoms" to R.drawable.bottoms_women,
            "Dresses" to R.drawable.dresses,
            "Outerwear" to R.drawable.outerwear,
            "Shoes" to R.drawable.shoes,
            "Bra" to R.drawable.bra,
            "Panties" to R.drawable.panties_women,
            "Pantyhose" to R.drawable.pantyhose,
            "Swimwear" to R.drawable.swimwear_women,
            "Hats" to R.drawable.hats_women
        )
    }

    fun getMapOfMenClothingIcons(): Map<String, Int> {
        return mapOf(
            "Tops" to R.drawable.tops_men,
            "Bottoms" to R.drawable.bottoms_men,
            "Suiting/Jackets" to R.drawable.suiting_jackets_men,
            "Suiting/Pants" to R.drawable.bottoms_men,
            "Outerwear" to R.drawable.outerwear,
            "Shoes" to R.drawable.shoes,
            "Hats" to R.drawable.hats_men
        )
    }
}

