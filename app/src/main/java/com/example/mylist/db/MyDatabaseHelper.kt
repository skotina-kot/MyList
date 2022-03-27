package com.example.mylist.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context): SQLiteOpenHelper(context, MyDatabase.DATABASE_NAME, null, MyDatabase.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(MyDatabase.CREATE_LIST)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(MyDatabase.SQLITE_DELETE_LIST)
        onCreate(db)
    }
}