package com.aliasgarlabs.sitmess;

import android.database.sqlite.SQLiteDatabase;



public class FoodDB {



	// Contacts table name
	public static final String TABLE_FOOD = "food";

	// Contacts Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";



	// Creating Tables

	public static void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FOOD + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NAME + " TEXT," + KEY_ADDRESS + " TEXT"
				+ ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);

		// Create tables again
		onCreate(db);
	}

}
