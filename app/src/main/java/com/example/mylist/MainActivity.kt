package com.example.mylist

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.db.ListColumn
import com.example.mylist.db.MyAdapter
import com.example.mylist.db.MyConstants
import com.example.mylist.db.MyDatabaseManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), MyAdapter.Listener {

    val databaseManager = MyDatabaseManager(this)
    val adapter = MyAdapter(ArrayList(), this, this)
    val cal = Calendar.getInstance()
    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    var recyclerView: RecyclerView? = null


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(itemM: MenuItem): Boolean {

        val data = datePickDiaLis()
        DatePickerDialog(this, data,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()

        return super.onOptionsItemSelected(itemM)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun datePickDiaLis(): DatePickerDialog.OnDateSetListener {
        val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            fillAdapter()
        }
        return data
    }

    fun newPage(view: View) {

        val i = Intent(this, EditActivity::class.java).apply {
            putExtra(MyConstants.TIME_KEY, formatter.format(cal.timeInMillis).toString())
        }
        startActivity(i)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
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
        adapter.updateAdapter(databaseManager.readDatabase(), formatter.format(cal.time).toString())
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