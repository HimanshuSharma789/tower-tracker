package com.example.towertracker

//import com.google.gson.annotations.SerializedName
//
//data class TowerLines (
//    @SerializedName("assigned_to") val lineAssigned: String,
//    @SerializedName("length")val lineLength: String,
//    @SerializedName("line_id") val lineId: String,
//    @SerializedName("name")val lineName: String,
//    @SerializedName("status")val lineStatus: Boolean
//)
data class TowerLines(
    val assigned_to: String = "",
    val length: String = "",
    val line_id: String = "",
    val name: String = "",
    val status: String = ""
)