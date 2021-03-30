package com.example.qrcode

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


const val DATABASE = "Database"
const val COL_IMAGE = "image"
const val COL_NAME = "name"
const val COL_DATE = "date"
const val COL_ID = "id"


class QrCodeDatabase(private var context: Context, private val TABLE: String):
    SQLiteOpenHelper(context, DATABASE, null, 1){

    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE $TABLE (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_IMAGE + " BLOB," +
                COL_NAME + " VARCHAR(256)," +
                COL_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData() {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_IMAGE, user.name)
        contentValues.put(COL_NAME, user.age)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
}