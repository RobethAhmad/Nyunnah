package com.robeth.nyunnah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.robeth.nyunnah.databinding.ActivityListSunnahBinding

class list_sunnah : AppCompatActivity() {
    lateinit var binding: ActivityListSunnahBinding
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    var adapter: SunnahAdapter? = null
    var list = mutableListOf<Sunnah>()
    var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListSunnahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("data")

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        selectedCategory = intent.getStringExtra("selectedCategory")

        // Buat data dummy untuk ditampilkan di RecyclerView
        val sunnahList = mutableListOf<Sunnah>()

        // Tambahkan data lainnya sesuai kebutuhan

        adapter?.setItem(sunnahList)

        getData()
    }

    private fun initRecyclerView() {
        adapter = SunnahAdapter(this)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@list_sunnah)
            recyclerView.adapter = adapter
        }
    }

    private fun getData() {
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (ds in snapshot.children) {
                    val id = ds.key
                    val judul = ds.child("judul").value.toString()
                    val kategori = ds.child("kategori").value.toString()
                    val doa = ds.child("doa").value.toString()
                    val artiDoa = ds.child("artiDoa").value.toString()
                    val niat = ds.child("niat").value.toString()
                    val niatLatin = ds.child("niatLatin").value.toString()
                    val tatacara = ds.child("tatacara").value.toString()


                    if (kategori.equals(selectedCategory, ignoreCase = true)) {
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
                }
                adapter?.setItem(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("tess", "onDataChange: ${error.toException()}")
            }
        })
    }
}
