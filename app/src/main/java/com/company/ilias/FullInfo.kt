package com.company.ilias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.ilias.databinding.ActivityFullInfoBinding
import com.squareup.picasso.Picasso

class FullInfo : AppCompatActivity() {
    lateinit var binding: ActivityFullInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFullInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item= intent.getSerializableExtra("match") as mMatch
        Picasso.get().load(item.firstImage).fit().into(binding.fullFirstImage)
        Picasso.get().load(item.secondImage).fit().into(binding.fulladdSecondImage)

        binding.fullnameFirstTema.setText(item.firstTeam)
        binding.fullnameSecTeam.setText(item.secondTeam)



        binding.fullDate.setText(item.data)

        binding.fullscore.setText(item.score)

        binding.fullDesc.setText(item.desc)
    }
}