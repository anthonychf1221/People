package com.anthonychaufrias.people.ui.persona

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anthonychaufrias.people.R
import com.anthonychaufrias.people.model.Persona
import kotlinx.android.synthetic.main.row_persona.view.*

class PersonaListAdapter(val onPersonaClick: (Persona) -> Unit, val onEliminarPersonaClick: (Persona) -> Unit): RecyclerView.Adapter<PersonaListAdapter.SearchViewHolder>() {
    var personasList: List<Persona> = emptyList()
    fun setData(list: List<Persona>){
        personasList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_persona, parent,false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        var obj = personasList[position]
        holder.itemView.txtNombre.text = obj.nombres
        holder.itemView.txtPais.text = obj.pais
        holder.itemView.setOnClickListener { onPersonaClick(obj) }
        holder.itemView.imgDelete.setOnClickListener { onEliminarPersonaClick(obj) }
    }

    override fun getItemCount(): Int {
        return personasList.size
    }

    class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}