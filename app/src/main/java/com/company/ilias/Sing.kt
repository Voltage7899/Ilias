package com.company.ilias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.company.ilias.databinding.ActivitySingBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Sing : AppCompatActivity() {
    private lateinit var binding: ActivitySingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signSign.setOnClickListener {
            val database = FirebaseDatabase.getInstance().getReference("User")
            if (TextUtils.isEmpty(binding.phoneSign.text.toString()) && TextUtils.isEmpty(binding.passSign.text.toString())) {
                Toast.makeText(this@Sing, "Введите все данные", Toast.LENGTH_SHORT).show()
            } else {
                database.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(binding.phoneSign.text.toString()).exists()) {
                            val userCurrentData: mUser? = snapshot.child(binding.phoneSign.text.toString()).getValue(
                                mUser::class.java
                            )
                            mUser.currentUser = userCurrentData
                            if (userCurrentData?.phone.equals(binding.phoneSign.text.toString()) && userCurrentData?.pass.equals(binding.passSign.text.toString())) {
                                Toast.makeText(this@Sing, "Вы вошли как Юзер", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@Sing, UserList::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@Sing, "Неверные данные", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this@Sing, "Номера не существует", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }
        binding.adminSign.setOnClickListener {
            val database = FirebaseDatabase.getInstance().getReference("Admin")
            if (TextUtils.isEmpty(binding.phoneSign.text.toString()) && TextUtils.isEmpty(binding.passSign.text.toString())) {
                Toast.makeText(this@Sing, "Введите все данные", Toast.LENGTH_SHORT).show()
            } else {
                database.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(binding.phoneSign.text.toString()).exists()) {
                            val userCurrentData: mUser? = snapshot.child(binding.phoneSign.text.toString()).getValue(mUser::class.java)

                            mUser.currentUser = userCurrentData

                            if (userCurrentData?.phone.equals(binding.phoneSign.text.toString()) && userCurrentData?.pass.equals(
                                    binding.passSign.text.toString()))
                            {
                                Toast.makeText(this@Sing, "Вы вошли как админ", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@Sing, AdminList::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@Sing, "Неверные данные", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this@Sing, "Номера не существует", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }
    }
}