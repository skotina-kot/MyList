package com.example.mylist

import android.content.Intent
import android.icu.text.Transliterator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.db.ListColumn
import com.example.mylist.db.MyAdapter
import com.example.mylist.db.MyDatabase
import com.example.mylist.db.MyDatabaseManager

class MainActivity : AppCompatActivity() {

    val databaseManager = MyDatabaseManager(this)
    val adapter = MyAdapter(ArrayList(), this)
    var item = ListColumn()

    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        init()
    }

    fun newPage(view: View) {
        val i = Intent(this, EditActivity::class.java)
        startActivity(i)
    }

    override fun onResume() {
        super.onResume()
        databaseManager.openDatabase()
        fillAdapter()

    }

    fun init() {
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
    }

    fun fillAdapter() {
        adapter.updateAdapter(databaseManager.readDatabase())
    }

    fun flagCheck (view: View) {
        val flag: CheckBox = findViewById(R.id.checkBox)
        val textL: TextView = findViewById(R.id.textL)

        val myHeader = flag.text.toString()
        val myDesc = textL.text.toString()

        val id = item.id
        val time = item.time

        if (flag.isChecked) {
            databaseManager.updateDatabase(myHeader, myDesc, id, time, "true")
        } else {
            databaseManager.updateDatabase(myHeader, myDesc, id, time, "false")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        databaseManager.closeDatabase()
    }


}