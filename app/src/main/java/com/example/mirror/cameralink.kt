package com.example.mirror

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.example.mirror.databinding.ActivityCameralinkBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class cameralink : AppCompatActivity() {
    private lateinit var binding : ActivityCameralinkBinding
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameralinkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef= FirebaseDatabase.getInstance().getReference("CameraLink")

        binding.button2.setOnClickListener{
           var streamlink= binding.streamlink.text.toString()
           var username= binding.username.text.toString()
           var pwd=binding.pwd.text.toString()
           adddata(streamlink, username, pwd)
        }
    }
    private fun adddata(streamlink: String, username: String, pwd: String){
        val link=streammodel(streamlink,username,pwd)
        dbRef.setValue(link)
            .addOnCompleteListener{
                Toast.makeText(this,"Saved Successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener{ Toast.makeText(this,"Saving Failed", Toast.LENGTH_LONG).show()}
    }
}