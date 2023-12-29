package com.robeth.nyunnah

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.robeth.nyunnah.databinding.ActivityDaftarSunnahBinding
import com.robeth.nyunnah.databinding.ActivityMainBinding

class DaftarSunnah : AppCompatActivity() {
    lateinit var binding : ActivityDaftarSunnahBinding
    private var firebaseDatabase : FirebaseDatabase? = null
    private var databaseRerence : DatabaseReference? = null
    private var list = mutableListOf<Sunnah>()
    private var adapter : SunnahAdapter? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_sunnah)

        binding = ActivityDaftarSunnahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseRerence = firebaseDatabase?.getReference("data")

        getData()

        var btnInsert = findViewById<Button>(R.id.btn_insert)

        btnInsert.setOnClickListener{
            val intent = Intent(this, Insert::class.java)
            startActivity(intent)
        }
        var btnHome = findViewById<Button>(R.id.btnHome)

        btnHome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initRecyclerView() {
        adapter = SunnahAdapter(this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@DaftarSunnah)
            adapter = this@DaftarSunnah.adapter
        }
    }


    private fun getData() {
        databaseRerence?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//               Log.e("tess","onDataChange: $snapshot")
                list.clear()
                for (ds in snapshot.children){
                    val id = ds.key
                    val judul = ds.child("judul").value.toString()
                    val doa = ds.child("doa").value.toString()
                    val artiDoa = ds.child("artiDoa").value.toString()
                    val niat = ds.child("niat").value.toString()
                    val niatLatin = ds.child("niatLatin").value.toString()
                    val kategori = ds.child("kategori").value.toString()
                    val tatacara = ds.child("tatacara").value.toString()

                    val sunnah = Sunnah(
                        id = id,
                        judul = judul,
                        doa = doa,
                        artiDoa = artiDoa,
                        niat = niat,
                        niatLatin = niatLatin,
                        kategori = kategori,
                        tatacara = tatacara
                    )
                    list.add(sunnah)
                }
                Log.e("tess","size:${list.size}")
                adapter?.setItem(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("tess","onDataChange: ${error.toException()}")
            }
        })

    }
}