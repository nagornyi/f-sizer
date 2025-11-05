package com.anahorn.fukusizer

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(private val context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    context.resources.getInteger(R.integer.database_version)
) {

    init {
        println("Database version: ${context.resources.getInteger(R.integer.database_version)}")
        val forceRecreateDb = context.resources.getBoolean(R.bool.force_recreate_db)
        val prefs = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE)
        val dbRecreated = prefs.getBoolean(MainActivity.KEY_DB_RECREATED, false)
        if (forceRecreateDb && !dbRecreated) {
            println("Force recreating database")
            context.deleteDatabase(DATABASE_NAME)
            // Set flag to indicate that the database has been recreated
            prefs.edit().putBoolean(MainActivity.KEY_DB_RECREATED, true).apply()
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLOTHINGS")
        val createClothingsTable = """
            CREATE TABLE $TABLE_CLOTHINGS (
                $KEY_ID INTEGER PRIMARY KEY,
                $DEPT TEXT,
                $CLOTHING TEXT,
                $SIZE TEXT
            )
        """.trimIndent()
        db.execSQL(createClothingsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Create tables again
        onCreate(db)
    }

    // Add new clothing
    fun addClothingItem(clothing: Clothing) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DEPT, clothing.dept)
            put(CLOTHING, clothing.clothing)
            put(SIZE, clothing.sizes)
        }
        db.insert(TABLE_CLOTHINGS, null, values)
        db.close()
    }

    // Get single clothing item
    fun getClothingItem(id: Int): Clothing? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_CLOTHINGS,
            arrayOf(KEY_ID, DEPT, CLOTHING, SIZE),
            "$KEY_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )

        return cursor?.use {
            if (it.moveToFirst()) {
                Clothing(
                    id = it.getInt(0),
                    dept = it.getString(1),
                    clothing = it.getString(2),
                    sizes = it.getString(3)
                )
            } else null
        }
    }

    // Get all clothing items
    fun getAllClothingItems(): List<Clothing> {
        val clothingList = mutableListOf<Clothing>()
        val selectQuery = "SELECT * FROM $TABLE_CLOTHINGS"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val clothing = Clothing(
                        id = it.getInt(0),
                        dept = it.getString(1),
                        clothing = it.getString(2),
                        sizes = it.getString(3)
                    )
                    clothingList.add(clothing)
                } while (it.moveToNext())
            }
        }

        return clothingList
    }

    // Update single clothing item
    fun updateClothingItem(clothing: Clothing): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DEPT, clothing.dept)
            put(CLOTHING, clothing.clothing)
            put(SIZE, clothing.sizes)
        }
        return db.update(
            TABLE_CLOTHINGS,
            values,
            "$KEY_ID = ?",
            arrayOf(clothing.id.toString())
        )
    }

    // Delete single clothing item
    fun deleteClothingItem(clothing: Clothing) {
        val db = this.writableDatabase
        db.delete(TABLE_CLOTHINGS, "$KEY_ID = ?", arrayOf(clothing.id.toString()))
        db.close()
    }

    // Get the number of clothing items
    fun getClothingsCount(): Int {
        val countQuery = "SELECT * FROM $TABLE_CLOTHINGS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val count = cursor.count
        cursor.close()
        return count
    }

    companion object {
        // Database Name
        private const val DATABASE_NAME = "Fukusizer"

        // Clothings table name
        private const val TABLE_CLOTHINGS = "clothings"

        // Clothings Table Columns names
        private const val KEY_ID = "id"
        private const val DEPT = "department"
        private const val CLOTHING = "clothing"
        private const val SIZE = "size"
    }
}

