package com.example.towertracker.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.towertracker.LINES
import com.example.towertracker.R
import com.example.towertracker.adapter.TowerLinesRecyclerAdapter
import com.example.towertracker.model.TowerLines
import org.json.JSONObject

class TowerLinesActivity : AppCompatActivity() {

    lateinit var linearLayoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: TowerLinesRecyclerAdapter
    lateinit var recyclerTowerLines: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tower_lines)

        val sharedPref =
            getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE)

        val userId = sharedPref.getString("uid", "null")
        val uName = sharedPref.getString("uname", "null")

        supportActionBar?.title = "Hi, $uName"

        var linesList = arrayListOf<TowerLines>()
        recyclerTowerLines = findViewById(R.id.recyclerTowerLines)
        linearLayoutManager = LinearLayoutManager(this@TowerLinesActivity)
        recyclerAdapter = TowerLinesRecyclerAdapter(this@TowerLinesActivity, linesList)
        recyclerTowerLines.adapter = recyclerAdapter
        recyclerTowerLines.layoutManager = linearLayoutManager

        val queue = Volley.newRequestQueue(applicationContext)

        val jsonObjectRequest = object :
            StringRequest(Method.POST, LINES,
                Response.Listener {
                    try {
                        println("youth: $it")
                        val jsonObject = JSONObject(it)
                        if(jsonObject.getInt("success") == 1) {
                            val lines = jsonObject.getJSONArray("lines")
                            for(i in 0 until lines.length()) {
                                val singleLine = lines.getJSONObject(i)
                                val line = TowerLines(
                                    singleLine.getInt("LineID"),
                                    singleLine.getString("Line_desc"),
                                    singleLine.getInt("Towers")
                                )
                                linesList.add(line)
                            }
                            recyclerAdapter.notifyDataSetChanged()

                        } else {
                            Toast.makeText(this@TowerLinesActivity, "success : 0", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
                        Toast.makeText(this@TowerLinesActivity, "exception: $e", Toast.LENGTH_SHORT)
                            .show()
                        println("error: $e")
                    }

                }, Response.ErrorListener {
                    Toast.makeText(
                        this@TowerLinesActivity,
                        "Volley error occurred: $it",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = userId!!
                return params
            }
        }
        queue.add(jsonObjectRequest)

    }
}