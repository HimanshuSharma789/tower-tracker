package com.example.towertracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TowerLinesRecyclerAdapter(val context: Context, var linesList: ArrayList<TowerLines>) :
    RecyclerView.Adapter<TowerLinesRecyclerAdapter.TowerLinesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TowerLinesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_towerlines_single_row, parent, false)
        return TowerLinesViewHolder(view)
    }

    class TowerLinesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
        val txtLineId: TextView = view.findViewById(R.id.txtLineId)
        val txtLineName: TextView = view.findViewById(R.id.txtLineName)
        val txtLineLength: TextView = view.findViewById(R.id.txtLineLength)
        val txtLineStatus: TextView = view.findViewById(R.id.txtLineStatus)

    }

    override fun onBindViewHolder(holder: TowerLinesViewHolder, position: Int) {
        val lines = linesList[position]
        holder.txtLineId.text = lines.line_id
        holder.txtLineName.text = lines.name
        holder.txtLineLength.text = lines.length
        holder.txtLineStatus.text = lines.status
    }

    override fun getItemCount(): Int {
        return linesList.size
    }

}