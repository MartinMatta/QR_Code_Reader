package com.example.qrcode

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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
                COL_IMAGE + " BLOB," +  //BLOB
                COL_NAME + " TEXT," +
                COL_DATE + " TEXT)"

        val myCodeTable = "CREATE TABLE $TABLE_MY_CODE (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_IMAGE + " BLOB," +  //BLOB
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
    fun insertHistory(name: String, image: Byte) {

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

    fun readHistory(): MutableList<Model> {
        val list: ArrayList<Model> = ArrayList()
        val db = this.readableDatabase

        val cursor: Cursor = readableDatabase.query(
                TABLE_HISTORY,
                arrayOf(COL_ID, COL_IMAGE, COL_NAME, COL_DATE),
                null, null, null, null, null
        )

        cursor.use { cursor ->
            if (cursor.count != 0) {
                cursor.moveToFirst()
                if (cursor.count > 0) {
                    do {
                        val id : Int = cursor.getInt(cursor.getColumnIndex(COL_ID))
                        val image: String = cursor.getString(cursor.getColumnIndex(COL_IMAGE))
                        val name: String = cursor.getString(cursor.getColumnIndex(COL_NAME))
                        val date: String = cursor.getString(cursor.getColumnIndex(COL_DATE))

                        list.add(Model(name, date, R.mipmap.ic_launcher)
                        )
                    } while ((cursor.moveToNext()))
                }
            }
        }

        return list.asReversed()
    }


    fun readMyCode(): MutableList<Model> {//ArrayList<Model>{
        val list: ArrayList<Model> = ArrayList()
        val db = this.readableDatabase

        val cursor: Cursor = readableDatabase.query(
                TABLE_MY_CODE,
                arrayOf(COL_ID, COL_IMAGE, COL_NAME, COL_DATE),
                null, null, null, null, null
        )

        cursor.use { cursor ->
            if (cursor.count != 0) {
                cursor.moveToFirst()
                if (cursor.count > 0) {
                    do {
                        val id : Int = cursor.getInt(cursor.getColumnIndex(COL_ID))
                        val image: String = cursor.getString(cursor.getColumnIndex(COL_IMAGE))
                        val name: String = cursor.getString(cursor.getColumnIndex(COL_NAME))
                        val date: String = cursor.getString(cursor.getColumnIndex(COL_DATE))

                        list.add(Model(name, date, R.mipmap.ic_launcher)
                        )
                    } while ((cursor.moveToNext()))
                }
            }
        }

        return list.asReversed()
    }

    fun delete(id: Int, table: String) :Int{
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