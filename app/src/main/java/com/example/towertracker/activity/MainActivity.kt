package com.example.towertracker.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.towertracker.LOGIN
import com.example.towertracker.R
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    lateinit var passwordTextField: TextInputLayout
    lateinit var usernameTextField: TextInputLayout
    lateinit var proceedButton: Button

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


        proceedButton.setOnClickListener {
            val user = usernameTextField.editText?.text.toString()
            val pass = passwordTextField.editText?.text.toString()

            val queue = Volley.newRequestQueue(applicationContext)

            val jsonObjectRequest = object :
                StringRequest(Method.POST, LOGIN,
                    Response.Listener {
                        try {

                            val jsonObject = JSONObject(it)
                            if (jsonObject.getInt("success") == 1) {
                                val empName = jsonObject.getJSONArray("data").getJSONObject(0).getString("Employee_Name")
                                sharedPref.edit().clear().apply()
                                sharedPref.edit().putBoolean("is_logged_in", true).apply()
                                sharedPref.edit().putString("uid", user).apply()
                                sharedPref.edit().putString("uname", empName).apply()
                                val intent = Intent(this@MainActivity, TowerLinesActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@MainActivity, "Invalid Login", Toast.LENGTH_SHORT).show()
                            }

                        } catch (e: Exception) {
                            Toast.makeText(this@MainActivity, "exception: $e", Toast.LENGTH_SHORT)
                                .show()
                            println("error: $e")
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
                    params["pwd"] = pass
                    return params
                }
            }
            queue.add(jsonObjectRequest)
        }
    }
}