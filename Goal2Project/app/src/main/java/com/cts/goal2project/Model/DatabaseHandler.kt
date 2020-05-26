package com.cts.goal2project.Model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log


class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "EmployeeDatabase"
        private val TABLE_CONTACTS = "EmployeeTable"
        private val KEY_ID= "id"
        private val KEY_FIRST_NAME= "firstname"
        private val KEY_LAST_NAME= "lastname"
        private val KEY_EMAIL = "email"
        private val KEY_DEST = "dest"
    }
    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIRST_NAME + " TEXT," + KEY_LAST_NAME +" TEXT,"
                + KEY_EMAIL + " TEXT, " + KEY_DEST + " TEXT" + ")" )
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }


    //method to insert data
    fun addEmployee(emp: EmpModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_FIRST_NAME, emp.firstName) // EmpModelClass Name
        contentValues.put(KEY_LAST_NAME, emp.lastName)
        contentValues.put(KEY_EMAIL,emp.userEmail )
        contentValues.put(KEY_DEST,emp.dest )
        Log.e("message" ,contentValues.toString())
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }

    //method to read data
    fun viewEmployee():List<EmpModelClass>{
        val empList:ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        Log.e("###########", selectQuery)
        val db = this.readableDatabase
        var cursor: Cursor;
        try{
            cursor = db.run { rawQuery(selectQuery, null) }
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        Log.e("#############", cursor.toString())

        var firstNameTxt: String
        var lastNameTxt: String
        var dest: String
        var userEmail: String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    firstNameTxt = cursor.getString(cursor.getColumnIndex("firstname"))
                    lastNameTxt = cursor.getString(cursor.getColumnIndex("lastname"))
                    dest = cursor.getString(cursor.getColumnIndex("dest"))
                    userEmail = cursor.getString(cursor.getColumnIndex("email"))
                    val emp= EmpModelClass( firstName = firstNameTxt, lastName = lastNameTxt,dest=dest,userEmail=userEmail)
                    empList.add(emp)
                }while (cursor.moveToNext())
            }
            Log.e("####################",empList[0].firstName);
        }
        return empList
    }
  /*  //method to update data
    fun updateEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(KEY_EMAIL,emp.userEmail )
        // EmpModelClass Email

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }*/
    /*//method to delete data
    fun deleteEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }*/
}