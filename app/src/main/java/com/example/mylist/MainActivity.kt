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
import com.example.mylist.db.MyAdapter
import com.example.mylist.db.MyDatabase
import com.example.mylist.db.MyDatabaseManager

class MainActivity : AppCompatActivity() {

    val databaseManager = MyDatabaseManager(this)
    val adapter = MyAdapter(ArrayList(), this)
    var viewHolder: RecyclerView.ViewHolder? = null

    var recyclerView: RecyclerView? = null
    var flag: CheckBox? = null
    var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        flag = findViewById(R.id.checkBox)
        textView = findViewById(R.id.textView)
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
    fun flagCheckBox () {
        flag?.setOnClickListener {  }
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseManager.closeDatabase()
    }


}