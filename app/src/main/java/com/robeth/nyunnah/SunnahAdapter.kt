package com.robeth.nyunnah

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class SunnahAdapter(private val context: Context) : RecyclerView.Adapter<SunnahAdapter.SunnahViewHolder>() {
    private var sunnahList = mutableListOf<Sunnah>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SunnahViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.sunnah_hari, parent, false)
        return SunnahViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return sunnahList.size
    }

    override fun onBindViewHolder(holder: SunnahViewHolder, position: Int) {
        val sunnah = sunnahList[position]
        holder.setItem(sunnah)
    }

    fun setItem(list: MutableList<Sunnah>) {
        this.sunnahList = list
        notifyDataSetChanged()
    }

    inner class SunnahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvJudul: TextView? = null
        private var tvDoa: TextView? = null
        private var tvArtiDoa: TextView? = null
        private var tvNiat: TextView? = null
        private var tvNiatLatin: TextView? = null
        private var tvTatacara: TextView? = null

        init {
            tvJudul = itemView.findViewById(R.id.tvJudul)
            tvDoa = itemView.findViewById(R.id.doa)
            tvArtiDoa = itemView.findViewById(R.id.artiDoa)
            tvNiat = itemView.findViewById(R.id.niat)
            tvNiatLatin = itemView.findViewById(R.id.niatLatin)
            tvTatacara = itemView.findViewById(R.id.tataCara)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val sunnah = sunnahList[position]
                    val intent = Intent(context, Detail::class.java)
                    intent.putExtra("judul", sunnah.judul)
                    intent.putExtra("doa", sunnah.doa)
                    intent.putExtra("artiDoa", sunnah.artiDoa)
                    intent.putExtra("niat", sunnah.niat)
                    intent.putExtra("niatLatin",sunnah.niatLatin)
                    intent.putExtra("tatacara",sunnah.tatacara)
                    context.startActivity(intent)
                }
            }
        }

        fun setItem(data: Sunnah) {
            tvJudul?.text = data.judul
            tvDoa?.text = data.doa
            tvArtiDoa?.text = data.artiDoa
            tvNiat?.text = data.niat
            tvNiatLatin?.text = data.niatLatin
            tvTatacara?.text = data.tatacara
        }
    }
}