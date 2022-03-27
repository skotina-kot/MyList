package com.example.mylist.db

import android.provider.BaseColumns

object MyDatabase: BaseColumns {

    const val LIST_NAME = "my_table"
    const val COLUMN_NAME_HEADER = "header"
    const val COLUMN_NAME_DESCRIPTION = "description"
    const val COLUMN_NAME_TIME = "time"
    const val COLUMN_NAME_FLAG = "flag"


    const val DATABASE_VERSION = 3
    const val DATABASE_NAME = "MyToDoList.db"

    const val CREATE_LIST = "CREATE TABLE IF NOT EXISTS $LIST_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_HEADER TEXT, $COLUMN_NAME_DESCRIPTION TEXT, $COLUMN_NAME_TIME TEXT, $COLUMN_NAME_FLAG TEXT)"
    const val SQLITE_DELETE_LIST = "DROP TABLE IF EXISTS $LIST_NAME"

}