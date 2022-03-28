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
import com.example.mylist.R

class MyAdapter(list: ArrayList<ListColumn>, contextMA: Context) : RecyclerView.Adapter<MyAdapter.MyHolder>() {

    var listArray = list
    var context = contextMA



    class MyHolder(markView: View, context: Context) : RecyclerView.ViewHolder(markView) {
        val flag: CheckBox = markView.findViewById(R.id.checkBox)
        val textL: TextView = markView.findViewById(R.id.textL)
        val context = context

        fun dataList(item: ListColumn) {
            flag.text = item.header
            textL.text = item.description
            flag.setOnLongClickListener {
                val i = Intent(context, EditActivity::class.java).apply {
                    putExtra(MyConstants.ID_KEY, item.id)
                    putExtra(MyConstants.HEADER_KEY, item.header)
                    putExtra(MyConstants.DESCRIPTION_KEY, item.description)
                }
                context.startActivity(i)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.markup, parent, false), context)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.dataList(listArray[position])
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

}