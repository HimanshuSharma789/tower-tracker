package com.example.towertracker.activity

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.towertracker.R
import com.example.towertracker.TOWER_DETAILS
import com.example.towertracker.adapter.TowerListRecyclerAdapter
import com.example.towertracker.model.TowerLists
import org.json.JSONObject


class TowerListActivity : AppCompatActivity() {

    lateinit var linearLayoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: TowerListRecyclerAdapter
    lateinit var recyclerTowerList: RecyclerView
    lateinit var labelLineDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tower_list)
        supportActionBar?.title = "Tower Details"

        val lineId= intent.getStringExtra("lineId")?: ""
        val lineDesc= intent.getStringExtra("lineDesc")?: ""
        labelLineDesc = findViewById(R.id.labelLineDesc)
        labelLineDesc.text = lineDesc

        var towerList = arrayListOf<TowerLists>()
        recyclerTowerList = findViewById(R.id.recyclerTowerList)

        linearLayoutManager = LinearLayoutManager(this@TowerListActivity)
        recyclerAdapter = TowerListRecyclerAdapter(this@TowerListActivity, towerList)
        recyclerTowerList.adapter = recyclerAdapter
        recyclerTowerList.layoutManager = linearLayoutManager

        val queue = Volley.newRequestQueue(applicationContext)

        val jsonObjectRequest = object :
            StringRequest(Method.POST, TOWER_DETAILS,
                Response.Listener {
                    try {
                        println("youth: $it")
                        val jsonObject = JSONObject(it)
                        if(jsonObject.getInt("success") == 1) {
                            val towers = jsonObject.getJSONArray("towers")
                            for(i in 0 until towers.length()) {
                                val singleTower = towers.getJSONObject(i)
                                val tower = TowerLists(
                                    singleTower.getString("TowerID"),
                                    singleTower.getString("Type_Of_Tower"),
                                    singleTower.getString("No_Of_CKT"),
                                    singleTower.getString("Forward_Span")
                                )
                                towerList.add(tower)
                            }
                            recyclerAdapter.notifyDataSetChanged()
//
                        } else {
                            Toast.makeText(this@TowerListActivity, "success : 0", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
                        Toast.makeText(this@TowerListActivity, "exception: $e", Toast.LENGTH_SHORT)
                            .show()
                        println("error: $e")
                    }

                }, Response.ErrorListener {
                    Toast.makeText(
                        this@TowerListActivity,
                        "Volley error occurred: $it",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["LineID"] = lineId
                return params
            }
        }
        queue.add(jsonObjectRequest)

    }
}