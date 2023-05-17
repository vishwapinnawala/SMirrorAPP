package com.example.mirror

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mirror.databinding.ActivityDashboard1Binding
import com.google.firebase.database.*
import kotlinx.coroutines.delay


class dashboard1 : AppCompatActivity() {
    private lateinit var binding : ActivityDashboard1Binding
    private lateinit var dbRef:DatabaseReference
    var test:String=" "
    var status=10




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboard1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        status=getdata()
        gettempdata()
        gethumiditydata()


        binding.imageButton2.setOnClickListener{
            status=getdata()

            when (status) {
                1 -> {
                    //binding.textView3.text = "Device is On"
                    binding.imageButton2.setImageResource(R.drawable.bulboff)
                    bulboff()
                }
                0 -> {
                    //binding.textView3.text = "Device is Off"
                    binding.imageButton2.setImageResource(R.drawable.bulbon)
                    bulbon()
                }
                else -> {
                    //binding.textView3.text = "Failed to Retrieve Data"
                }
            }

        //bulbonoff()
        }
        binding.imageButton.setOnClickListener{
            val intent = Intent(this, cameralink::class.java)
            startActivity(intent)
        }
    }
    private fun getdata(): Int {
        //var test=""
        dbRef=FirebaseDatabase.getInstance().getReference("Device1")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (eachsnap in listOf(snapshot)){
                       test = eachsnap.getValue(bulbmodel::class.java).toString()
                        if (test=="bulbmodel(devicestate=On)"){

                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        var boolvar=10
        if (test=="bulbmodel(devicestate=On)"){
            boolvar=1
        }
        else if(test=="bulbmodel(devicestate=Off)"){
            boolvar=0
        }
            return boolvar}
    private fun gettempdata(){
        var tempstring=""
        dbRef=FirebaseDatabase.getInstance().getReference("Temperature")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (eachsnap in listOf(snapshot)){
                        tempstring = eachsnap.getValue(tempmodel::class.java).toString()

                        val regex = Regex("[^0-9]")
                        tempstring = regex.replace(tempstring, "")
                        tempstring=(tempstring+"°C")
                        Log.d("TAG", tempstring)
                        binding.textView4.setText(tempstring)
                    }}}

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })}
    private fun gethumiditydata(){
        var humstring=""
        dbRef=FirebaseDatabase.getInstance().getReference("Humidity")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (eachsnap in listOf(snapshot)){
                        humstring = eachsnap.getValue(hummodel::class.java).toString()

                        val regex = Regex("[^0-9]")
                        humstring = regex.replace(humstring, "")
                        humstring=(humstring+"%")
                        Log.d("TAG", humstring)
                        binding.textView5.setText(humstring)
                    }}}

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })}

    private fun bulbon(){
        dbRef=FirebaseDatabase.getInstance().getReference("Device1")
//val id=dbRef.push().key!!
        val bulb=bulbmodel("On")
        dbRef.setValue(bulb)
            .addOnCompleteListener{
                Toast.makeText(this,"Device Turned On",Toast.LENGTH_LONG).show()
            }.addOnFailureListener{ Toast.makeText(this,"Device Turned Failed",Toast.LENGTH_LONG).show()}
    }
    private fun bulboff(){
        dbRef=FirebaseDatabase.getInstance().getReference("Device1")
//val id=dbRef.push().key!!
        val bulb=bulbmodel("Off")
        dbRef.setValue(bulb)
            .addOnCompleteListener{
                Toast.makeText(this,"Device Turned Off",Toast.LENGTH_LONG).show()
            }.addOnFailureListener{ Toast.makeText(this,"Device Turned Failed",Toast.LENGTH_LONG).show()}
    }
}