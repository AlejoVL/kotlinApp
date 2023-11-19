    package com.example.apis

    import android.graphics.Color
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.view.View
    import android.widget.Button
    import android.widget.ImageView
    import android.widget.ProgressBar
    import android.widget.TextView
    import com.android.volley.Request
    import com.android.volley.Response
    import com.android.volley.VolleyError
    import com.android.volley.toolbox.StringRequest
    import com.android.volley.toolbox.Volley
    import com.bumptech.glide.Glide
    import com.google.android.material.textfield.TextInputEditText
    import org.json.JSONException
    import org.json.JSONObject

    class Login : AppCompatActivity() {
        lateinit var txtUser: TextInputEditText
        lateinit var txtPass: TextInputEditText
        lateinit var progreso1: ProgressBar
        lateinit var btnLogin: Button
        lateinit var btnCerra: Button
        lateinit var txtInfo2: TextView
        lateinit var txtNombreC: TextView
        lateinit var txtEmail2: TextView
        lateinit var txtGenero2: TextView
        lateinit var imgg: ImageView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)
            btnLogin = findViewById(R.id.btnLogin)
            btnCerra = findViewById(R.id.btnCerrar)
            txtUser = findViewById(R.id.txtUser)
            progreso1 = findViewById(R.id.progressBar3)
            txtPass = findViewById(R.id.txtPass)
            txtInfo2 = findViewById(R.id.txtInfo2)
            txtNombreC = findViewById(R.id.txtNomC)
            txtEmail2 = findViewById(R.id.txtEmaill)
            txtGenero2 = findViewById(R.id.txtGenero)
            imgg = findViewById(R.id.imgImg)
            progreso1.visibility = View.GONE
            txtNombreC.visibility = View.INVISIBLE
            txtEmail2.visibility = View.INVISIBLE
            txtGenero2.visibility = View.INVISIBLE
            imgg.visibility = View.INVISIBLE
            btnCerra.visibility = View.INVISIBLE
            txtInfo2.text = "Bienvenido por favor ingresa tus datos de inicio de sesion"

    }

        fun cerrarSession(view: View){
            txtNombreC.visibility = View.INVISIBLE
            txtEmail2.visibility = View.INVISIBLE
            txtGenero2.visibility = View.INVISIBLE
            imgg.visibility = View.INVISIBLE
            btnCerra.visibility = View.INVISIBLE
            txtUser.visibility = View.VISIBLE
            txtPass.visibility = View.VISIBLE
            txtNombreC.setText("")
            txtEmail2.setText("")
            txtGenero2.setText("")
            Glide.with(applicationContext).clear(imgg)
            btnLogin.visibility = View.VISIBLE
            txtInfo2.setTextColor(Color.BLACK)
            txtInfo2.text = "Bienvenido por favor ingresa tus datos de inicio de sesion"
        }

        fun login(view: View) {
            txtInfo2.visibility = View.INVISIBLE
            progreso1.visibility = View.VISIBLE
            btnLogin.visibility = View.GONE
            if (txtUser.text.toString().isNotEmpty() && txtPass.text.toString().isNotEmpty()) {
                val queue = Volley.newRequestQueue(this)
                val url = "https://dummyjson.com/auth/login"
                val jsonBody = JSONObject()
                jsonBody.put("username", txtUser.text.toString())
                jsonBody.put("password", txtPass.text.toString())
                var peticion = object : StringRequest(
                    Request.Method.POST,
                    url,
                    object : Response.Listener<String> {
                        override fun onResponse(response: String) {
                            try {
                                val jsonResponse = JSONObject(response)
                                val email = jsonResponse.getString("email")
                                val firstName = jsonResponse.getString("firstName")
                                val lastName = jsonResponse.getString("lastName")
                                val gender = jsonResponse.getString("gender")
                                val url = jsonResponse.getString("image")
                                txtNombreC.visibility = View.VISIBLE
                                txtEmail2.visibility = View.VISIBLE
                                txtGenero2.visibility = View.VISIBLE
                                imgg.visibility = View.VISIBLE
                                btnCerra.visibility = View.VISIBLE
                                txtUser.visibility = View.INVISIBLE
                                txtPass.visibility = View.INVISIBLE
                                txtNombreC.text = "Nombre: $firstName, $lastName"
                                txtEmail2.text = "Email: $email"
                                txtGenero2.text = "Genero: $gender"
                                Glide.with(applicationContext)
                                    .load(url)
                                    .into(imgg)
                                txtPass.setText("")
                                txtUser.setText("")
                                txtInfo2.visibility =View.VISIBLE
                                txtInfo2.setTextColor(Color.GREEN)
                                txtInfo2.text = "Usuario en Linea"
                                progreso1.visibility = View.GONE

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                    },
                    object : Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError) {
                            txtInfo2.visibility =View.VISIBLE
                            txtInfo2.setTextColor(Color.RED)
                            txtInfo2.text = "Usuario No existe en base de datos"
                            txtPass.setText("")
                            progreso1.visibility = View.GONE
                            btnLogin.visibility = View.VISIBLE
                        }
                    }
                ) {
                    override fun getBodyContentType(): String {
                        return "application/json"
                    }

                    override fun getBody(): ByteArray {
                        return jsonBody.toString().toByteArray()
                    }
                }
                queue.add(peticion)
            } else {
                txtUser.error = "Debes llenar el campo User"
                txtPass.error = "Debes llenar el campo Pass"
            }
        }

    }