package com.example.apis

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    lateinit var txtInfo: TextView
    lateinit var progreso: ProgressBar
    lateinit var btnConsultar:Button
    lateinit var txtId: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtInfo = findViewById(R.id.txtInfo)
        progreso = findViewById(R.id.progressBar)
        btnConsultar = findViewById(R.id.button)
        txtId = findViewById(R.id.txtId)
        progreso.visibility = View.GONE
    }

    fun pasar(b : View){
        val intent = Intent(applicationContext, Login::class.java)
        startActivity(intent)
    }

    fun consultar(view: View) {
        if (txtId.text.toString().length>0){
        progreso.visibility = View.VISIBLE
        btnConsultar.visibility = View.GONE
        val queue = Volley.newRequestQueue(this)
        val url = "https://dummyjson.com/products/${txtId.text.toString()}"

        val peticion = StringRequest(Request.Method.GET ,url, Response.Listener<String>{
            txtInfo.setTextColor(Color.BLACK)
            txtInfo.text= it
            progreso.visibility = View.GONE
            btnConsultar.visibility = View.VISIBLE
        }, Response.ErrorListener{
            txtInfo.setTextColor(Color.RED)
            txtInfo.text= it.toString()
            progreso.visibility = View.GONE
            btnConsultar.visibility = View.VISIBLE
        }
        )
        queue.add(peticion)
        }else{
            txtId.error = "Debes enviar el ID"
        }
    }
}