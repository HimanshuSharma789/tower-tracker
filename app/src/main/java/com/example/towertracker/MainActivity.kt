package com.example.towertracker

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


private lateinit var database: DatabaseReference
lateinit var passwordTextField: TextInputLayout
lateinit var usernameTextField: TextInputLayout
lateinit var proceedButton: Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main)

        val sharedPref =
            getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE)
        usernameTextField = findViewById(R.id.usernameTextField)
        passwordTextField = findViewById(R.id.passwordTextField)
        proceedButton = findViewById(R.id.proceedButton)
        database = Firebase.database.reference


        proceedButton.setOnClickListener {
            val user = usernameTextField.editText?.text.toString()
            val pass = passwordTextField.editText?.text.toString()

            val queue = Volley.newRequestQueue(applicationContext)

            val jsonObjectRequest = object :
                StringRequest(Method.POST, LOGIN,
                    Response.Listener {
                        try {
                            Toast.makeText(this@MainActivity, "data: $it", Toast.LENGTH_SHORT)
                                .show()
                        } catch (e: Exception) {
                            Toast.makeText(this@MainActivity, "exception: $e", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }, Response.ErrorListener {
                        Toast.makeText(
                            this@MainActivity,
                            "Volley error occurred: $it",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["username"] = user
                    return params
                }
            }
            queue.add(jsonObjectRequest)

            /*val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.exists()) {
                        val password = dataSnapshot.child(user).child("pass").getValue<String>()
                        if (pass == password) {
                            Toast.makeText(this@MainActivity, password, Toast.LENGTH_LONG).show()
                            sharedPref.edit().clear().apply()
                            sharedPref.edit().putBoolean("is_logged_in", true).apply()
                            sharedPref.edit().putString("username", user).apply()
                            val intent = Intent(this@MainActivity, TowerLinesActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "invalid $password",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "sorry, new user?", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            database.child("auth").orderByChild("username").equalTo(user)
                .addListenerForSingleValueEvent(listener)*/

        }
    }
}