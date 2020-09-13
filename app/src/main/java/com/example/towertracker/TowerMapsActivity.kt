package com.example.towertracker

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject

class TowerMapsActivity : AppCompatActivity(), GoogleMap.OnInfoWindowClickListener,
    OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tower_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        getJSONData(googleMap)
    }

    override fun onInfoWindowClick(marker: Marker) {
        Toast.makeText(this, marker.title, Toast.LENGTH_SHORT).show()
    }

    private fun getJSONData(googleMap: GoogleMap) {
        val lineId = intent.getStringExtra("lineId") ?: ""

        val queue = Volley.newRequestQueue(applicationContext)

        val jsonObjectRequest = object :
            StringRequest(Method.POST, TOWER_DETAILS,
                Response.Listener {
                    try {
                        println("youth: $it")
                        val jsonObject = JSONObject(it)
                        if (jsonObject.getInt("success") == 1) {
                            addToMap(jsonObject, googleMap)
                            Log.v("youth", "json fetched !")

                        } else {
                            Toast.makeText(
                                this@TowerMapsActivity,
                                "success : 0",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } catch (e: Exception) {
                        Toast.makeText(this@TowerMapsActivity, "exception: $e", Toast.LENGTH_SHORT)
                            .show()
                        println("error: $e")
                    }

                }, Response.ErrorListener {
                    Toast.makeText(
                        this@TowerMapsActivity,
                        "Connection error occurred",
                        Toast.LENGTH_LONG
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

    private fun addToMap(jsonObject: JSONObject, googleMap: GoogleMap) {
        mMap = googleMap
        val bounds = LatLngBounds.Builder()

        val towers = jsonObject.getJSONArray("towers")
        for (i in 0 until towers.length()) {
            val singleTower = towers.getJSONObject(i)
            val coordinates = LatLng(
                singleTower.getString("Latitude").toDouble(),
                singleTower.getString("Longitude").toDouble()
            )
            bounds.include(coordinates)
            mMap.addMarker(
                MarkerOptions().position(coordinates)
                    .title("TowerID: " + singleTower.getString("TowerID"))
            )
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        googleMap.setOnInfoWindowClickListener(this)
    }

}