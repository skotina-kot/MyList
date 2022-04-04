package com.example.mylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.ItemTouchHelper
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
        supportActionBar?.setHomeButtonEnabled(true)
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
        val swapHelper = getSwapMg()
        swapHelper.attachToRecyclerView(recyclerView)
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

    private fun getSwapMg() : ItemTouchHelper {
        return ItemTouchHelper(object:
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteItem(viewHolder.adapterPosition, databaseManager)
            }
        })
    }


}