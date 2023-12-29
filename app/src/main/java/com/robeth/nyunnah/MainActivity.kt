package com.robeth.nyunnah

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import com.robeth.nyunnah.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var hariIniTextView: TextView
    lateinit var binding : ActivityMainBinding
    private var firebaseDatabase : FirebaseDatabase? = null
    private var databaseRerence : DatabaseReference? = null
    private var list = mutableListOf<Sunnah>()
    private var adapter : SunnahAdapter? = null
    var selectedCategory: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseRerence = firebaseDatabase?.getReference("data")

        getData()


        var btnWaktu = findViewById<Button>(R.id.btnWaktu)
        var btnTempat = findViewById<Button>(R.id.btnTempat)
        var btnKondisi = findViewById<Button>(R.id.btnKondisi)

        btnWaktu.setOnClickListener{
            selectedCategory = "waktu"
            val intent = Intent(this, list_sunnah::class.java)
            intent.putExtra("selectedCategory", selectedCategory)
            startActivity(intent)
        }

        btnTempat.setOnClickListener{
            selectedCategory = "tempat"
            val intent = Intent(this, list_sunnah::class.java)
            intent.putExtra("selectedCategory", selectedCategory)
            startActivity(intent)
        }

        btnKondisi.setOnClickListener{
            selectedCategory = "kondisi"
            val intent = Intent(this, list_sunnah::class.java)
            intent.putExtra("selectedCategory", selectedCategory)
            startActivity(intent)
        }



        hariIniTextView = findViewById(R.id.hariIni)

        // Mendapatkan tanggal hari ini
        val calendar: Calendar = Calendar.getInstance()
        val date: Date = calendar.time

        // Format tanggal menjadi nama hari
        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val namaHari: String = dateFormat.format(date)

        // Set nama hari ke TextView
        hariIniTextView.text = namaHari
    }

    private fun initRecyclerView() {
        adapter = SunnahAdapter(this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }


    private fun getData() {
        databaseRerence?.addValueEventListener(object : ValueEventListener{
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
