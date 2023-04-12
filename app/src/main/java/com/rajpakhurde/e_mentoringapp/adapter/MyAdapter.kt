package com.rajpakhurde.e_mentoringapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rajpakhurde.e_mentoringapp.R
import com.rajpakhurde.e_mentoringapp.data.DataClass

class MyAdapter(private val context: Context, private var dataList: List<DataClass>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
         val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].profileImage).into(holder.recImage)
        holder.recName.text = dataList[position].name
        holder.recUserMode.text = dataList[position].userMode
    }

    fun searchDataList(searchList: List<DataClass>) {
        dataList = searchList
        notifyDataSetChanged()
    }
}

class MyViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
    var recImage: ImageView
    var recName: TextView
    var recUserMode: TextView
    var recCard: CardView

    init {
        recImage = itemview.findViewById(R.id.recImage)
        recName = itemview.findViewById(R.id.recName)
        recUserMode = itemview.findViewById(R.id.recUserMode)
        recCard = itemview.findViewById(R.id.recCard)
    }
}