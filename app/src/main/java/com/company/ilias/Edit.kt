package com.company.ilias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.ilias.databinding.ActivityEditBinding
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class Edit : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra("match") as mMatch
        Picasso.get().load(item.firstImage).fit().into(binding.editFirstImage)
        Picasso.get().load(item.secondImage).fit().into(binding.editSecondImage)

        binding.editnameFirstTema.setText(item.firstTeam)
        binding.editnameSecTeam.setText(item.secondTeam)

        binding.editlinkFirstTeam.setText(item.firstImage)
        binding.editlinkSecTeam.setText(item.secondImage)

        binding.editDate.setText(item.data)

        binding.editscore.setText(item.score)

        binding.editDesc.setText(item.desc)

        binding.editMatch.setOnClickListener {
            val match=mMatch(item.id,binding.editnameFirstTema.text.toString(),binding.editnameSecTeam.text.toString(),binding.editscore.text.toString(),binding.editDate.text.toString(),binding.editlinkFirstTeam.text.toString(),binding.editlinkSecTeam.text.toString(),binding.editDesc.text.toString())

            database.getReference("Match").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(match.id.toString()).exists()){
                        Toast.makeText(this@Edit,"Такая запись уже существует", Toast.LENGTH_SHORT).show()
                    }
                    else{

                        database.getReference("Match").child(item.id.toString()).setValue(match)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        binding.editFirstImage.setOnClickListener {
            try {
                Picasso.get().load(binding.editlinkFirstTeam.text.toString()).fit().into(binding.editFirstImage)
            }catch (ex:Exception){
                Toast.makeText(this,"Нет ссылки на картинку", Toast.LENGTH_SHORT).show()
            }

        }
        binding.editSecondImage.setOnClickListener{
            try {
                Picasso.get().load(binding.editlinkSecTeam.text.toString()).fit().into(binding.editSecondImage)
            }catch (ex:Exception){
                Toast.makeText(this,"Нет ссылки на картинку", Toast.LENGTH_SHORT).show()
            }

        }
    }
}