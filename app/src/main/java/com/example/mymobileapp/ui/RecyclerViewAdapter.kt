package com.example.mymobileapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymobileapp.R

class RecyclerViewAdapter(private val entityList: List<Entity>, private val onItemClick: (Entity) -> Unit) :
    RecyclerView.Adapter<RecyclerViewAdapter.EntityViewHolder>() {

    class EntityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eproperty1: TextView = view.findViewById(R.id.property1)
        val eproperty2: TextView = view.findViewById(R.id.property2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entity, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val entity = entityList[position]
        holder.eproperty1.text = entity.property1
        holder.eproperty2.text = entity.property2

        holder.itemView.setOnClickListener {
            onItemClick(entity)
        }
    }

    override fun getItemCount(): Int = entityList.size
}
