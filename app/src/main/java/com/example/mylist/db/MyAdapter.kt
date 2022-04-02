package com.example.mylist.db

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.EditActivity
import com.example.mylist.MainActivity
import com.example.mylist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyAdapter(list: ArrayList<ListColumn>, contextMA: Context, val listener: Listener) : RecyclerView.Adapter<MyAdapter.MyHolder>() {

    var listArray = list
    var context = contextMA



    class MyHolder(markView: View, context: Context) : RecyclerView.ViewHolder(markView) {
        val flag: CheckBox = markView.findViewById(R.id.checkBox)
        val del: FloatingActionButton = markView.findViewById(R.id.fb_delete_item)
        val context = context
        //val markView = RecyclerView.ViewHolder

        fun dataList(item: ListColumn) {
            flag.text = item.header

            flag.setOnLongClickListener {
                val i = Intent(context, EditActivity::class.java).apply {
                    putExtra(MyConstants.ID_KEY, item.id)
                    putExtra(MyConstants.HEADER_KEY, item.header)
                    putExtra(MyConstants.DESCRIPTION_KEY, item.description)
                    putExtra(MyConstants.FLAG_KEY, item.flag)
                    putExtra(MyConstants.POS_KEY, adapterPosition)
                }
                context.startActivity(i)
                return@setOnLongClickListener true
            }

            del.setOnClickListener {

            }

        }
        fun dataFlag(item: ListColumn, listener: Listener) {
            val flagS = item.flag
            if (flagS == "false") flag.isChecked = false
            else flag.isChecked = true

            flag.setOnClickListener {
                listener.onClick(item)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.markup, parent, false), context)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.dataList(listArray[position])
        holder.dataFlag(listArray[position], listener)
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(listData: List<ListColumn>) {
        listArray.clear()
        listArray.addAll(listData)
        notifyDataSetChanged()
    }

    fun deleteItem(id: Int, DatabaseManager: MyDatabaseManager) {

        DatabaseManager.deleteToDatabase(id.toString())
        listArray.removeAt(id)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(id)
    }

    interface Listener {
        fun onClick(item: ListColumn)
    }

}