package com.anahorn.fukusizer

data class Clothing(
    var id: Int = 0,
    var dept: String = "",
    var clothing: String = "",
    var sizes: String = ""
) {
    // Secondary constructor for backward compatibility
    constructor(dept: String, clothing: String, sizes: String) : this(0, dept, clothing, sizes)
}

