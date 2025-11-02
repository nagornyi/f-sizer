package com.anahorn.fukusizer;

import java.util.HashMap;
import java.util.Map;

public class IconHelpers {
    protected static Map<String, Integer> getMapOfFlagIcons() {
        Map<String, Integer> drawableMap = new HashMap<>();
        drawableMap.put("Australia", R.drawable.australia);
        drawableMap.put("Belgium", R.drawable.belgium);
        drawableMap.put("China", R.drawable.china);
        drawableMap.put("Europe", R.drawable.europe);
        drawableMap.put("France", R.drawable.france);
        drawableMap.put("Germany", R.drawable.germany);
        drawableMap.put("India", R.drawable.india);
        drawableMap.put("Italy", R.drawable.italy);
        drawableMap.put("Japan", R.drawable.japan);
        drawableMap.put("Korea", R.drawable.korea);
        drawableMap.put("New Zealand", R.drawable.newzealand);
        drawableMap.put("Spain", R.drawable.spain);
        drawableMap.put("UK", R.drawable.uk);
        drawableMap.put("USA", R.drawable.usa);
        return drawableMap;
    }

    protected static Map<String, Integer> getMapOfWomenClothingIcons() {
        Map<String, Integer> drawableMap = new HashMap<>();
        drawableMap.put("Tops", R.drawable.tops_women);
        drawableMap.put("Bottoms", R.drawable.bottoms_women);
        drawableMap.put("Dresses", R.drawable.dresses);
        drawableMap.put("Outerwear", R.drawable.outerwear);
        drawableMap.put("Shoes", R.drawable.shoes);
        drawableMap.put("Bra", R.drawable.bra);
        drawableMap.put("Panties", R.drawable.panties_women);
        drawableMap.put("Pantyhose", R.drawable.pantyhose);
        drawableMap.put("Swimwear", R.drawable.swimwear_women);
        drawableMap.put("Hats", R.drawable.hats_women);
        return drawableMap;
    }

    protected static Map<String, Integer> getMapOfMenClothingIcons() {
        Map<String, Integer> drawableMap = new HashMap<>();
        drawableMap.put("Tops", R.drawable.tops_men);
        drawableMap.put("Bottoms", R.drawable.bottoms_men);
        drawableMap.put("Suiting/Jackets", R.drawable.suiting_jackets_men);
        drawableMap.put("Suiting/Pants", R.drawable.bottoms_men);
        drawableMap.put("Outerwear", R.drawable.outerwear);
        drawableMap.put("Shoes", R.drawable.shoes);
        drawableMap.put("Hats", R.drawable.hats_men);
        return drawableMap;
    }
}