package com.android.fbmodulefirebase

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.android.fbmodulefirebase.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idEditDate.setOnClickListener(){
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.idEditDate.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }



        binding.bookSlotButton.setOnClickListener {
            val id: Int = binding.radioGroup.checkedRadioButtonId
            val id1: Int = binding.radioGroup1.checkedRadioButtonId
            val dateText = binding.idEditDate.text.toString()
            validate_and_calculate(id,id1,dateText)
        }
    }

    private fun validate_and_calculate(id: Int, id1: Int, dateText: String){
        if (id != -1 && id1!=-1 && dateText.isNotEmpty()) { // If any radio button checked from radio group
            // Get the instance of radio button using id
            val radio: RadioButton = findViewById(id)
            val radio1: RadioButton = findViewById(id1)
            val radio_text = radio.text.toString()
            val radio1_text = radio1.text.toString()
            val arrayList = ArrayList<String>()
            val new_price = check_price(radio_text,radio1_text).toString()
            arrayList.add(radio_text)
            arrayList.add(dateText)
            arrayList.add(radio1_text)
            arrayList.add(new_price)

            database = FirebaseDatabase.getInstance().getReference("Slots")
            val slotBooking = SlotBooking(arrayList)
            database.child(radio_text).child(dateText).child(radio1_text).get().addOnSuccessListener {
                if(it.exists()){
                    Toast.makeText(this,"Booking Failed, Already Booked!",Toast.LENGTH_SHORT).show()
                }else{
                    database.child(radio_text).child(dateText).child(radio1_text).setValue(slotBooking).addOnSuccessListener {
                        Toast.makeText(this,"Booked, Rs."+new_price,Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this,"Failed!",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }else {
            // If no radio button checked in this radio group
            Toast.makeText(
                applicationContext, "Please select all the options to continue",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun check_price(radio1_selected: String, radio2_selected: String): Int {
        val slot1_price = 100
        val slot2_price = 500
        val slot1_tennis_price = 50
        var booking_amount = 0
        if(radio1_selected == "Clubhouse" && radio2_selected == "10AM-4PM"){
            booking_amount=slot1_price*6
        }
        else if (radio1_selected == "Clubhouse" && radio2_selected == "4PM-10PM"){
            booking_amount = slot2_price*6
        }
        else if (radio1_selected == "Tennis Court") {
            booking_amount = slot1_tennis_price * 6
        }

        /*
        && radio2 == "10AM-4PM"
        else if(radio1 == "Tennis Court" && radio2 == "4PM-10PM"){
            booking_amount = slot1_tennis_price*6
        }*/
        return booking_amount
    }
}