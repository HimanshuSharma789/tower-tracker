package com.example.towertracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.towertracker.R
import com.example.towertracker.model.TowerLists

class TowerListRecyclerAdapter(val context: Context, var towerList: ArrayList<TowerLists>) :
    RecyclerView.Adapter<TowerListRecyclerAdapter.TowerListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TowerListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_towerlist_single_row, parent, false)
        return TowerListViewHolder(view)
    }

    class TowerListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
        val txtTowerId: TextView = view.findViewById(R.id.txtTowerId)
        val txtTowerType: TextView = view.findViewById(R.id.txtTowerType)
        val txtTowerCKT: TextView = view.findViewById(R.id.txtTowerCKT)
        val txtTowerSpan: TextView = view.findViewById(R.id.txtTowerSpan)

    }

    override fun onBindViewHolder(holder: TowerListViewHolder, position: Int) {
        val tower = towerList[position]
        holder.txtTowerId.text = tower.TowerID
        holder.txtTowerType.text = ("Type: " + tower.Type_Of_Tower)
        holder.txtTowerCKT.text = ("CKT: " + tower.No_Of_CKT)
        holder.txtTowerSpan.text = (tower.Forward_Span + "m")

    }

    override fun getItemCount(): Int {
        return towerList.size
    }

}