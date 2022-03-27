package com.example.mylist.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class MyDatabaseManager(context: Context) {
    val myDatabaseHelper = MyDatabaseHelper(context)
    var db: SQLiteDatabase? = null

    fun openDatabase () {
        db = myDatabaseHelper.writableDatabase
    }

    fun insertToDatabase (header: String, description: String, time: String, flag: String) {
        val values = ContentValues().apply {
            put(MyDatabase.COLUMN_NAME_HEADER, header)
            put(MyDatabase.COLUMN_NAME_DESCRIPTION, description)
            put(MyDatabase.COLUMN_NAME_TIME, time)
            put(MyDatabase.COLUMN_NAME_FLAG, flag)

        }
        db?.insert(MyDatabase.LIST_NAME, null, values)
    }


    fun updateDatabase (header: String, description: String, id: Int, time: String, flag: String) {
        val selection = BaseColumns._ID + "=$id"
        val values = ContentValues().apply {
            put(MyDatabase.COLUMN_NAME_HEADER, header)
            put(MyDatabase.COLUMN_NAME_DESCRIPTION, description)
            put(MyDatabase.COLUMN_NAME_TIME, time)
            put(MyDatabase.COLUMN_NAME_FLAG, flag)
        }
        db?.update(MyDatabase.LIST_NAME, values, selection, null)
    }

    fun deleteToDatabase (id: String) {
        val selection = BaseColumns._ID + "=$id"
        db?.delete(MyDatabase.LIST_NAME, selection, null)
    }

    @SuppressLint("Range")
    fun readDatabase(): ArrayList<ListColumn> {
        val listDatabase = ArrayList<ListColumn>()
        val cursor = db?.query(MyDatabase.LIST_NAME, null, null, null, null, null, null)

        while (cursor?.moveToNext()!!) {
            val item = ListColumn()
            item.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            item.header = cursor.getString(cursor.getColumnIndex(MyDatabase.COLUMN_NAME_HEADER))
            item.description = cursor.getString(cursor.getColumnIndex(MyDatabase.COLUMN_NAME_DESCRIPTION))
            item.time = cursor.getString(cursor.getColumnIndex(MyDatabase.COLUMN_NAME_TIME))
            //item.flag = cursor.getString(cursor.getColumnIndex(MyDatabase.COLUMN_NAME_FLAG))
            listDatabase.add(item)
        }
        cursor.close()
        return listDatabase

    }

    fun closeDatabase () {
        myDatabaseHelper.close()
    }
}