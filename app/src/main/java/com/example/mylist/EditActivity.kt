package com.example.mylist

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.mylist.db.*

class EditActivity : AppCompatActivity() {

    var editTextHeader: EditText? = null
    var editTextDesc: EditText? = null
    var flag = "false"
    var time = ""

    val databaseManager = MyDatabaseManager(this)
    var id = 0
    var isEditState = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        editTextHeader = findViewById(R.id.editTextHeader)
        editTextDesc = findViewById(R.id.editTextDesc)
        getIntents()
    }

    override fun onResume() {
        super.onResume()
        databaseManager.openDatabase()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun save(view: View) {
        val myHeader = editTextHeader?.text.toString()
        val myDesc = editTextDesc?.text.toString()

        if (myHeader == "") {
            Toast.makeText(applicationContext, "Enter header...", Toast.LENGTH_LONG).show()
        }
        if (myDesc == "") {
            Toast.makeText(applicationContext, "Enter description...", Toast.LENGTH_LONG).show()
        }

        if (myHeader != "" && myDesc != "") {
            if (isEditState) {
                databaseManager.updateDatabase(myHeader, myDesc, id)
            } else {
                databaseManager.insertToDatabase(myHeader, myDesc, time, flag)
            }
            finish()
        }
    }

    fun getIntents() {
        val i = intent
        if (i.getStringExtra((MyConstants.TIME_KEY)) != null) time = i.getStringExtra(MyConstants.TIME_KEY)!!
        if (i.getStringExtra((MyConstants.HEADER_KEY)) != null) {
            isEditState = true
            editTextHeader?.setText(i.getStringExtra(MyConstants.HEADER_KEY))
            editTextDesc?.setText(i.getStringExtra(MyConstants.DESCRIPTION_KEY))
            id = i.getIntExtra(MyConstants.ID_KEY, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseManager.closeDatabase()
    }
}