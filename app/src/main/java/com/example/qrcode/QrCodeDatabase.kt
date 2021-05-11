package com.example.qrcode

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.annotation.RequiresApi
import com.google.zxing.WriterException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


const val DATABASE = "Database"
const val COL_NAME = "image"
const val COL_TYPE = "name"
const val COL_DATE = "date"
const val COL_ID = "id"

const val TABLE_HISTORY = "history"
const val TABLE_MY_CODE = "myCode"


class QrCodeDatabase(private var context: Context, private val TABLE: String):
    SQLiteOpenHelper(context, DATABASE, null, 1){

    private var bitmap: Bitmap? = null
    private var qrgEncoder: QRGEncoder? = null

    val TABLE_HISTORY = "history"
    val TABLE_MY_CODE = "myCode"

    override fun onCreate(db: SQLiteDatabase?) {

        val historyTable = "CREATE TABLE $TABLE_HISTORY (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT," +  //BLOB
                COL_TYPE + " TEXT," +
                COL_DATE + " TEXT)"

        val myCodeTable = "CREATE TABLE $TABLE_MY_CODE (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT," +  //BLOB
                COL_TYPE + " TEXT," +
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
            //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertHistory(name: String, type: String) {

        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_TYPE, type)
        contentValues.put(COL_DATE, getDateTime())

        insert(TABLE_HISTORY, contentValues)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun insertMyCode(name: String, type: String) {

        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_TYPE, type)
        contentValues.put(COL_DATE, getDateTime())

        insert(TABLE_MY_CODE, contentValues)
    }

    fun readHistory(): MutableList<Model> {
        val list: ArrayList<Model> = ArrayList()
        val db = this.readableDatabase

        val cursor: Cursor = readableDatabase.query(
                TABLE_HISTORY,
                arrayOf(COL_ID, COL_NAME, COL_TYPE, COL_DATE),
                null, null, null, null, null
        )

        cursor.use { cursor ->
            if (cursor.count != 0) {
                cursor.moveToFirst()
                if (cursor.count > 0) {
                    do {
                        val id : Int = cursor.getInt(cursor.getColumnIndex(COL_ID))
                        val name: String = cursor.getString(cursor.getColumnIndex(COL_NAME))
                        val type: String = cursor.getString(cursor.getColumnIndex(COL_TYPE))
                        val date: String = cursor.getString(cursor.getColumnIndex(COL_DATE))

                        list.add(Model(name, date, R.mipmap.ic_launcher)
                        )
                    } while ((cursor.moveToNext()))
                }
            }
        }

        return list.asReversed()
    }


    fun readMyCode(context: Context): MutableList<Model> {//ArrayList<Model>{
        val list: ArrayList<Model> = ArrayList()
        val db = this.readableDatabase

        val cursor: Cursor = readableDatabase.query(
                TABLE_MY_CODE,
                arrayOf(COL_ID, COL_NAME, COL_TYPE, COL_DATE),
                null, null, null, null, null
        )

        cursor.use { cursor ->
            if (cursor.count != 0) {
                cursor.moveToFirst()
                if (cursor.count > 0) {
                    do {
                        val id : Int = cursor.getInt(cursor.getColumnIndex(COL_ID))
                        val name: String = cursor.getString(cursor.getColumnIndex(COL_NAME))
                        val type: String = cursor.getString(cursor.getColumnIndex(COL_TYPE))
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

    fun generateQRCode(context: Context, name: String, type: String): Bitmap? {

        var _type = "text"

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(context,
                "Enter some text to generate QR Code",
                Toast.LENGTH_SHORT).show();
        } else {

            when (type) {
                "text" -> _type =  QRGContents.Type.TEXT
                "contact" -> _type =  QRGContents.Type.CONTACT
                "sms" -> _type =  QRGContents.Type.SMS
                "phone" -> _type =  QRGContents.Type.PHONE
                "location" -> _type =  QRGContents.Type.LOCATION
                "email" -> _type =  QRGContents.Type.EMAIL
                else -> print("otherwise")
            }

            qrgEncoder = QRGEncoder(name, null, _type, 3)

            try {
                bitmap = qrgEncoder!!.encodeAsBitmap()
                //qrImage?.setImageBitmap(bitmap)
            } catch (e: WriterException) {
                Log.e("Tag", e.toString())
            }

        }
        return bitmap
    }
}