package com.robeth.nyunnah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.robeth.nyunnah.databinding.ActivityDaftarSunnahBinding
import com.robeth.nyunnah.databinding.ActivityInsertBinding

class Insert : AppCompatActivity() {
    lateinit var binding : ActivityInsertBinding
    private var firebaseDatabase : FirebaseDatabase? = null
    private var databaseRerence : DatabaseReference? = null
    private var list = mutableListOf<Sunnah>()
    private var adapter : SunnahAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseRerence = firebaseDatabase?.getReference("data")

        getData()

        binding.btnSubmit.setOnClickListener{
            saveData()
            val intent = Intent(this, DaftarSunnah::class.java)
            startActivity(intent)
        }

    }

    private fun saveData() {

//        val etDoa = findViewById<EditText>(R.id.etDoa)
//        val etArtiDoa = findViewById<EditText>(R.id.etArtiDoa)
//        val etNiat = findViewById<EditText>(R.id.etNiat)
//        val etNiatLatin = findViewById<EditText>(R.id.etNiatLatin)
//        val etKategori = findViewById<EditText>(R.id.etKategori)
//        val etTatacara = findViewById<EditText>(R.id.etTatacara)


        val etJudul = findViewById<EditText>(R.id.etJudul).text.toString()
        val etDoa = findViewById<EditText>(R.id.etDoa).text.toString()
        val etArtiDoa = findViewById<EditText>(R.id.etArtiDoa).text.toString()
        val etNiat = findViewById<EditText>(R.id.etNiat).text.toString()
        val etNiatLatin = findViewById<EditText>(R.id.etNiatLatin).text.toString()
        val etKategori = findViewById<EditText>(R.id.etKategori).text.toString()
        val etTatacara = findViewById<EditText>(R.id.etTatacara).text.toString()


        val sunnah = Sunnah(
            judul = etJudul,
            doa = etDoa,
            artiDoa = etArtiDoa,
            niat = etNiat,
            niatLatin = etNiatLatin,
            kategori = etKategori,
            tatacara = etTatacara
        )

        databaseRerence?.child(getId(6))?.setValue(sunnah)
    }

    private fun getId(length: Int ): String {
        val allowChar = ('A'..'Z')+('a'..'z')+(0..9)
        return (1..length).map { allowChar.random() }.joinToString(separator = "")
    }


    private fun getData() {
        databaseRerence?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//               Log.e("tess","onDataChange: $snapshot")
                list.clear()
                for (ds in snapshot.children) {
                    val id = ds.key
                    val judul = ds.child("judul").value.toString()
                    val doa = ds.child("doa").value.toString()

                    val sunnah = Sunnah(id = id, judul = judul, doa = doa)
                    list.add(sunnah)
                }
                Log.e("tess", "size:${list.size}")
                adapter?.setItem(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("tess", "onDataChange: ${error.toException()}")
            }
        })

    }
}