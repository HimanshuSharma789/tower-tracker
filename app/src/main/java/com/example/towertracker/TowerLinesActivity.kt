package com.example.towertracker

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

lateinit var linearLayoutManager: RecyclerView.LayoutManager
lateinit var recyclerAdapter: TowerLinesRecyclerAdapter
lateinit var recyclerTowerLines: RecyclerView
private lateinit var database: DatabaseReference

class TowerLinesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tower_lines)

        val sharedPref =
            getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE)

        val user = sharedPref.getString("username", "null")
        supportActionBar?.title = "Hi, $user"
        database = Firebase.database.reference

        var towerLinesList = arrayListOf<TowerLines>()
        recyclerTowerLines = findViewById(R.id.recyclerTowerLines)
        linearLayoutManager = LinearLayoutManager(this@TowerLinesActivity)
        recyclerAdapter = TowerLinesRecyclerAdapter(this@TowerLinesActivity, towerLinesList)
        recyclerTowerLines.adapter = recyclerAdapter
        recyclerTowerLines.layoutManager = linearLayoutManager


        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
//                    dataSnapshot.children.mapNotNullTo(towerLinesList) { it.getValue<TowerLines>(TowerLines::class.java) }
                    for (snapshot in dataSnapshot.children) {
                        towerLinesList.add(snapshot.getValue<TowerLines>(TowerLines::class.java)!!)
                    }
                }
                recyclerAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                println("loadPost:onCancelled ${error.toException()}")
            }
        }
        database.child("lines").orderByChild("assigned_to").equalTo(user)
            .addListenerForSingleValueEvent(listener)


    }
}