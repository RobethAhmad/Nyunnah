package com.robeth.nyunnah

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Detail : AppCompatActivity() {
    private lateinit var tvDoa: TextView
    private lateinit var tvArtiDoa: TextView
    private lateinit var tvNiat: TextView
    private lateinit var tvNiatLatin: TextView
    private lateinit var tvTatacara: TextView
    private lateinit var tvJudul: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvDoa = findViewById(R.id.doa)
        tvArtiDoa = findViewById(R.id.artiDoa)
        tvNiat = findViewById(R.id.niat)
        tvNiatLatin = findViewById(R.id.niatLatin)
        tvTatacara = findViewById(R.id.tataCara)
        tvJudul = findViewById(R.id.tvJudul)

        val judul =intent.getStringExtra("judul")
        val doa = intent.getStringExtra("doa")
        val artiDoa = intent.getStringExtra("artiDoa")
        val niat = intent.getStringExtra("niat")
        val niatLatin = intent.getStringExtra("niatLatin")
        val tatacara = intent.getStringExtra("tatacara")

        tvDoa.text = doa
        tvArtiDoa.text = artiDoa
        tvNiat.text = niat
        tvNiatLatin.text = niatLatin
        tvTatacara.text = tatacara
        tvJudul.text = judul
    }
}
