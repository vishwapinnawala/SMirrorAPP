package com.example.mirror

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import com.example.mirror.databinding.ActivityCameralinkBinding
import com.google.firebase.database.*

class cameralink : AppCompatActivity() {
    private lateinit var binding : ActivityCameralinkBinding
    private lateinit var dbRef: DatabaseReference
    lateinit var videoView: VideoView
    var videoUrl ="https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1"


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

        videoView = findViewById(R.id.videoView);
        val uri: Uri = Uri.parse(videoUrl)
        videoView.setVideoURI(uri)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        mediaController.setMediaPlayer(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
    }
    private fun adddata(streamlink: String, username: String, pwd: String){
        val link=streammodel(streamlink,username,pwd)
        dbRef.setValue(link)
            .addOnCompleteListener{
                Toast.makeText(this,"Saved Successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener{ Toast.makeText(this,"Saving Failed", Toast.LENGTH_LONG).show()}
    }
    fun geturl():String{
        var streamlink=""
        dbRef=FirebaseDatabase.getInstance().getReference("CameraLink")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (eachsnap in listOf(snapshot)){
                        streamlink=eachsnap.child("streamlink").getValue().toString()
                        Log.d("TAG", streamlink)
                    }}}
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return (streamlink)}

}