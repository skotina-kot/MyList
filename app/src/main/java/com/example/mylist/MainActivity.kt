package com.example.mylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.db.ListColumn
import com.example.mylist.db.MyAdapter
import com.example.mylist.db.MyConstants
import com.example.mylist.db.MyDatabaseManager

class MainActivity : AppCompatActivity(), MyAdapter.Listener {

    val databaseManager = MyDatabaseManager(this)
    val adapter = MyAdapter(ArrayList(), this, this)
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
        //getDelete()
        fillAdapter()

    }

    fun init() {
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
    }

    fun fillAdapter() {
        adapter.updateAdapter(databaseManager.readDatabase())
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseManager.closeDatabase()
    }

    override fun onClick(item: ListColumn) {
        databaseManager.changeFlag(item.id, item.flag)
        fillAdapter()
    }

    /*fun getDelete() {
        val int = intent
        val pos = int.getIntExtra(MyConstants.POS_KEY, 0)
        if (pos != 0 ) adapter.deleteItem(pos, databaseManager)
    }*/


}