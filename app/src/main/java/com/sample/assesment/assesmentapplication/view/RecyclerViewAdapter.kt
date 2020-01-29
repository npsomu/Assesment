package com.sample.assesment.assesmentapplication.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.assesment.assesmentapplication.BR
import com.sample.assesment.assesmentapplication.R
import com.sample.assesment.assesmentapplication.data.model.CountryInfoItem
import com.sample.assesment.assesmentapplication.databinding.RecylerviewRowLayoutBinding


class RecyclerViewAdapter(val mContext: Context, val listitemrows: List<CountryInfoItem>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : RecylerviewRowLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recylerview_row_layout, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = listitemrows.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listitemrows[position]
        holder.bind(data)
    }

    class MyViewHolder(private val recylerviewRowLayoutBinding: RecylerviewRowLayoutBinding) : RecyclerView.ViewHolder(recylerviewRowLayoutBinding.root) {
        fun bind(data:Any){
            recylerviewRowLayoutBinding.setVariable(BR.dataitem, data)
            recylerviewRowLayoutBinding.executePendingBindings()
        }
    }

}