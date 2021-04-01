package com.example.qrcode

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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
                COL_IMAGE + " TEXT," +  //BLOB
                COL_NAME + " TEXT," +
                COL_DATE + " TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertData(name: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_IMAGE, "image")
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_DATE, getDateTime())
        val result = database.insert(TABLE, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData(): ArrayList<Model>{
        val list: ArrayList<Model> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {

                list.add(
                    Model(
                        result.getString(result.getColumnIndex(COL_NAME)),
                        result.getString(result.getColumnIndex(COL_DATE)), // date
                        R.mipmap.ic_launcher
                    )
                )
            }
            while (result.moveToNext())
        }
        return list
    }

    fun delete(id: Int):Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE, "id=$id",null)
        db.close()
        return success
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateTime(): String {
        val dateTime = LocalDateTime.now()
        return dateTime.format(DateTimeFormatter.ofPattern("d/M/y HH:mm a"))
    }
}