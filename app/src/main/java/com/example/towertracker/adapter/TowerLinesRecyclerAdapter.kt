package com.example.towertracker.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.towertracker.R
import com.example.towertracker.TowerMapsActivity
import com.example.towertracker.activity.TowerListActivity
import com.example.towertracker.model.TowerLines

class TowerLinesRecyclerAdapter(val context: Context, var linesList: ArrayList<TowerLines>) :
    RecyclerView.Adapter<TowerLinesRecyclerAdapter.TowerLinesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TowerLinesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_towerlines_single_row, parent, false)
        return TowerLinesViewHolder(view)
    }

    class TowerLinesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
        val llContentExtra: LinearLayout = view.findViewById(R.id.llContentExtra)
        val txtLineId: TextView = view.findViewById(R.id.txtLineId)
        val txtLineName: TextView = view.findViewById(R.id.txtLineName)
        val txtLineLength: TextView = view.findViewById(R.id.txtLineLength)
        val txtLineStatus: TextView = view.findViewById(R.id.txtLineStatus)
        val txtLineMap: TextView = view.findViewById(R.id.txtLineMap)
        val txtLineDetails: TextView = view.findViewById(R.id.txtLineDetails)

    }

    override fun onBindViewHolder(holder: TowerLinesViewHolder, position: Int) {
        val lines = linesList[position]
        holder.txtLineId.text = lines.LineID.toString()
        holder.txtLineName.text = lines.Line_desc
        holder.txtLineLength.text = (lines.Towers.toString() + " towers")

        holder.llContent.setOnClickListener {
            if (holder.llContentExtra.visibility == View.GONE) {
                holder.llContentExtra.visibility = View.VISIBLE
            } else {
                holder.llContentExtra.visibility = View.GONE
            }
        }

        holder.txtLineMap.setOnClickListener {
            Toast.makeText(context, "MAP", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, TowerMapsActivity::class.java)
            intent.putExtra("lineId", lines.LineID.toString())
            context.startActivity(intent)
        }
        holder.txtLineDetails.setOnClickListener {
            val intent = Intent(context, TowerListActivity::class.java)
            intent.putExtra("lineId", lines.LineID.toString())
            intent.putExtra("lineDesc", lines.Line_desc)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return linesList.size
    }

}