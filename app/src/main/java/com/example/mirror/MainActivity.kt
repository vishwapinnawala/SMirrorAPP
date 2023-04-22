package com.example.mirror

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mirror.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener{
            val uname=binding.editTextTextPersonName2.text.toString()
            val pwd=binding.editTextTextPassword.text.toString()
if (uname == "vishwa" && pwd == "vishwa"){
    val intent = Intent(this@MainActivity, dashboard1::class.java)
    startActivity(intent)
}
            else{
    val errorToast = Toast.makeText(
        this@MainActivity,
        "Invalid Login",
        Toast.LENGTH_SHORT
    )
    errorToast.show()
            }

        }
    }
}