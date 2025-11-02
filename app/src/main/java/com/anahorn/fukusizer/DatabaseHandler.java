package com.anahorn.fukusizer;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.SharedPreferences;

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Name
    private static final String DATABASE_NAME = "Fukusizer";

    // Clothings table name
    private static final String TABLE_CLOTHINGS = "clothings";

    // Clothings Table Columns names
    private static final String KEY_ID = "id";
    private static final String DEPT = "department";
    private static final String CLOTHING = "clothing";
    private static final String SIZE = "size";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null,
                context.getResources().getInteger(R.integer.database_version));
        System.out.println("Database version: "+context.getResources().getInteger(R.integer.database_version));
        boolean forceRecreateDb = context.getResources().getBoolean(R.bool.force_recreate_db);
        SharedPreferences prefs = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        boolean dbRecreated = prefs.getBoolean(MainActivity.KEY_DB_RECREATED, false);
        if (forceRecreateDb && !dbRecreated) {
            System.out.println("Force recreating database");
            context.deleteDatabase(DATABASE_NAME);
            // Set flag to indicate that the database has been recreated
            prefs.edit().putBoolean(MainActivity.KEY_DB_RECREATED, true).apply();
        }
    }

    // Creating tables and populating data
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHINGS);
        String CREATE_CLOTHINGS_TABLE = "CREATE TABLE " + TABLE_CLOTHINGS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, " + DEPT + " TEXT, "
                + CLOTHING + " TEXT, " + SIZE + " TEXT" + ");";
        db.execSQL(CREATE_CLOTHINGS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Add new clothing
    void addClothingItem(Clothing clothing) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DEPT, clothing.getDept());
        values.put(CLOTHING, clothing.getClothing());
        values.put(SIZE, clothing.getSizes());

        // Inserting Row
        db.insert(TABLE_CLOTHINGS, null, values);
        db.close(); // Closing database connection
    }

    // Get single clothing item
    Clothing getClothingItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLOTHINGS, new String[] { KEY_ID,
                        DEPT, CLOTHING, SIZE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Clothing clothing = new Clothing(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        return clothing;
    }

    // Get all clothing items
    public List<Clothing> getAllClothingItems() {
        List<Clothing> clothingList = new ArrayList<Clothing>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CLOTHINGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Clothing clothing = new Clothing();
                clothing.setID(Integer.parseInt(cursor.getString(0)));
                clothing.setDept(cursor.getString(1));
                clothing.setClothing(cursor.getString(2));
                clothing.setSize(cursor.getString(3));
                // Adding clothing to list
                clothingList.add(clothing);
            } while (cursor.moveToNext());
        }

        return clothingList;
    }

    // Update single clothing item
    public int updateClothingItem(Clothing clothing) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DEPT, clothing.getDept());
        values.put(CLOTHING, clothing.getClothing());
        values.put(SIZE, clothing.getSizes());

        // updating row
        return db.update(TABLE_CLOTHINGS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(clothing.getID()) });
    }

    // Delete single clothing item
    public void deleteClothingItem(Clothing clothing) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLOTHINGS, KEY_ID + " = ?",
                new String[] { String.valueOf(clothing.getID()) });
        db.close();
    }

    // Get the number of clothing items
    public int getClothingsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CLOTHINGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
