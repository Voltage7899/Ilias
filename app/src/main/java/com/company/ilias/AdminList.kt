package com.company.ilias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.ilias.databinding.ActivityAdminListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminList : AppCompatActivity(), FootballAdapter.ClickListener {
    lateinit var binding: ActivityAdminListBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var matchListAdapter:FootballAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAdminListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newAdmin.setOnClickListener {
            startActivity(Intent(this,AddNewMatch::class.java))
        }



        binding.recyclerViewAdmin.layoutManager= LinearLayoutManager(this)
        matchListAdapter= FootballAdapter(this)
        binding.recyclerViewAdmin.adapter=matchListAdapter
        matchListAdapter?.loadListToAdapter(getData())

        val simpleCallback =object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id =matchListAdapter?.deleteItem(viewHolder.adapterPosition)
                database.getReference("Match").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (id != null) {
                            database.getReference("Match").child(id.toString()).removeValue()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            }

        }
        val itemTouchHelper= ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewAdmin)
    }


    fun getData():ArrayList<mMatch>{



        val List=ArrayList<mMatch>()
        database.getReference("Match").get().addOnSuccessListener {
            for (el in it.children){
                var match=el.getValue(mMatch::class.java)
                if(match!=null){
                    List.add(match)
                    matchListAdapter?.loadListToAdapter(List)
                }

            }
        }
        return List
    }
    override fun onClick(match: mMatch) {
        startActivity(Intent(this, Edit::class.java).apply {
            putExtra("match",match)

        })
    }

    override fun onStart() {
        super.onStart()
        matchListAdapter?.loadListToAdapter(getData())
    }
}