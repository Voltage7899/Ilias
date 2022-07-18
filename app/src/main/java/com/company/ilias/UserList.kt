package com.company.ilias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.ilias.databinding.ActivityUserListBinding
import com.google.firebase.database.FirebaseDatabase

class UserList : AppCompatActivity(),FootballAdapter.ClickListener {
    lateinit var binding: ActivityUserListBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var matchListAdapter:FootballAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recUser.layoutManager= LinearLayoutManager(this)
        matchListAdapter= FootballAdapter(this)
        binding.recUser.adapter=matchListAdapter
        matchListAdapter?.loadListToAdapter(getData())
    }

    fun getData():ArrayList<mMatch>{



        val matchList=ArrayList<mMatch>()
        database.getReference("Match").get().addOnSuccessListener {
            for (i in it.children){
                var match=i.getValue(mMatch::class.java)
                if(match!=null){
                    matchList.add(match)
                    matchListAdapter?.loadListToAdapter(matchList)
                }

            }
        }
        return matchList
    }
    override fun onClick(match: mMatch) {
        startActivity(Intent(this, FullInfo::class.java).apply {
            putExtra("match",match)

        })
    }
    override fun onStart() {
        super.onStart()
        matchListAdapter?.loadListToAdapter(getData())
    }
}