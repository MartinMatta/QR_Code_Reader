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

const val TABLE_HISTORY = "history"
const val TABLE_MY_CODE = "myCode"


class QrCodeDatabase(private var context: Context, private val TABLE: String):
    SQLiteOpenHelper(context, DATABASE, null, 1){

    val TABLE_HISTORY = "history"
    val TABLE_MY_CODE = "myCode"

    override fun onCreate(db: SQLiteDatabase?) {

        val historyTable = "CREATE TABLE $TABLE_HISTORY (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_IMAGE + " TEXT," +  //BLOB
                COL_NAME + " TEXT," +
                COL_DATE + " TEXT)"

        val myCodeTable = "CREATE TABLE $TABLE_MY_CODE (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_IMAGE + " TEXT," +  //BLOB
                COL_NAME + " TEXT," +
                COL_DATE + " TEXT)"

        db?.execSQL(historyTable)
        db?.execSQL(myCodeTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MY_CODE")
    }

    private fun insert(table: String, contentValues: ContentValues) {
        val database = this.writableDatabase
        val result = database.insert(table, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertHistory(name: String) {

        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_IMAGE, "image")
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_DATE, getDateTime())

        insert(TABLE_HISTORY, contentValues)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun insertMyCode(name: String) {

        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_IMAGE, "image")
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_DATE, getDateTime())

        insert(TABLE_MY_CODE, contentValues)
    }

    fun readMyCode(): ArrayList<Model>{
        val list: ArrayList<Model> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_MY_CODE"
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

    fun _readMyCode(): ArrayList<Model>{
        val list: ArrayList<Model> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_MY_CODE"
        //val result = db.rawQuery(query, null)
        val res = db.rawQuery("select * from $TABLE_MY_CODE", null)
        //result.moveToFirst()
        //result.close()
        return list
    }

    fun delete(id: Int, table: String):Int{
        val db = this.writableDatabase
        val success = db.delete(table, "id=$id",null)
        db.close()
        return success
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateTime(): String {
        val dateTime = LocalDateTime.now()
        return dateTime.format(DateTimeFormatter.ofPattern("d/M/y HH:mm a"))
    }
}