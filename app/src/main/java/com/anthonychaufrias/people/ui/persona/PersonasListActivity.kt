package com.anthonychaufrias.people.ui.persona

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthonychaufrias.people.R
import com.anthonychaufrias.people.model.Persona
import com.anthonychaufrias.people.viewmodel.PersonaVM
import kotlinx.android.synthetic.main.lyt_lst_personas.*

class PersonasListActivity : AppCompatActivity() {
    private lateinit var viewModel: PersonaVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lyt_lst_personas)

        val tlt: String = getString(R.string.tlt_lpers)
        this.supportActionBar!!.setTitle(tlt)

        viewModel = ViewModelProvider(this).get(PersonaVM::class.java)
        initUI()
        //test()
    }
    private fun initUI(){
        getPersonas()
    }

    private fun getPersonas(){
        try{
            rvPersonas.layoutManager = LinearLayoutManager(this)
            rvPersonas.adapter = PersListAdapter{

            }
            viewModel.getPersonasList()
            viewModel.lstPersonas.observe(this, Observer { list ->
                Log.e("cammbiossss==", "cammbiosssscammbiossss");
                (rvPersonas.adapter as PersListAdapter).setData(list)
            })
        }
        catch (e: Exception) {
            Log.e("initUI-Exception==", e.message.toString());
        }
    }

    /*private fun test(){
        rvPersonas.layoutManager = LinearLayoutManager(this)
        rvPersonas.adapter = PersListAdapter()
        var lstPersonas : MutableList<Persona>  = mutableListOf()
        val p1 = Persona(1, "Antyhony", "123123", 1)
        val p2 = Persona(2, "SSSSS", "444444", 1)
        lstPersonas.add(p1)
        lstPersonas.add(p2)
        (rvPersonas.adapter as PersListAdapter).setData(lstPersonas)
    }*/
}