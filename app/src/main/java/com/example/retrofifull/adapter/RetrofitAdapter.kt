package com.example.retrofifull.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.retrofifull.databinding.ItemRvBinding
import com.example.retrofifull.models.Info

class RetrofitAdapter(var rvAction: RvAction,var list: ArrayList<Info>):RecyclerView.Adapter<RetrofitAdapter.Vh>() {
    inner class Vh(var itemRvBinding: ItemRvBinding):ViewHolder(itemRvBinding.root){
        fun onBind(info: Info,position: Int){
            itemRvBinding.name1.text = info.sarlavha
            itemRvBinding.name2.text = info.zarurlik
            itemRvBinding.name3.text = info.sana
            itemRvBinding.name4.text = info.oxirgi_muddat
            itemRvBinding.name5.text = info.batafsil
            itemRvBinding.btnMore.setOnClickListener {
                rvAction.moreClick(info,position,itemRvBinding.btnMore)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int  = list.size
    override fun onBindViewHolder(holder: Vh, position: Int) {
        return holder.onBind(list[position],position)
    }
    interface RvAction{
        fun moreClick(info: Info,position: Int,imageView: ImageView)
    }
}