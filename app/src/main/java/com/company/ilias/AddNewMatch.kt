package com.company.ilias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.ilias.databinding.ActivityAddNewMatchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlin.random.Random

class AddNewMatch : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewMatchBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddNewMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addMatch.setOnClickListener {

            val id = Random.nextInt(1,100000)

            val match=mMatch(id,binding.nameFirstTema.text.toString(),binding.nameSecTeam.text.toString(),binding.score.text.toString(),binding.addDate.text.toString(),binding.linkFirstTeam.text.toString(),binding.linkSecTeam.text.toString(),binding.addDesc.text.toString())

            database.getReference("Match").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(match.id.toString()).exists()){
                        Toast.makeText(this@AddNewMatch,"Уже есть", Toast.LENGTH_SHORT).show()
                    }
                    else{

                        database.getReference("Match").child(id.toString()).setValue(match)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        binding.addFirstImage.setOnClickListener {
            try {
                Picasso.get().load(binding.linkFirstTeam.text.toString()).fit().into(binding.addFirstImage)
            }catch (ex:Exception){
                Toast.makeText(this,"Нет ссылки на картинку", Toast.LENGTH_SHORT).show()
            }

        }
        binding.addSecondImage.setOnClickListener{
            try {
                Picasso.get().load(binding.linkSecTeam.text.toString()).fit().into(binding.addSecondImage)
            }catch (ex:Exception){
                Toast.makeText(this,"Нет ссылки на картинку", Toast.LENGTH_SHORT).show()
            }

        }
    }
}