package com.anthonychaufrias.people.ui.persona

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(PersonaVM::class.java)
        initUI()
        //test()
        fab.setOnClickListener { view ->
            //var  objPer: Persona? = null
            val intent = Intent(this, PersonaSaveActivity::class.java)
            intent.putExtra(PersonaSaveActivity.ARG_ITEM, Persona(0, "", "", 0, ""))
            startActivity(intent)
        }
    }
    private fun initUI(){
        rvPersonas.layoutManager = LinearLayoutManager(this)
        rvPersonas.adapter = PersListAdapter({
            val intent = Intent(this, PersonaSaveActivity::class.java)
            intent.putExtra(PersonaSaveActivity.ARG_ITEM, it)
            startActivity(intent)
        },{
            deletePersona(it)
        })
        getPersonas("")
    }

    private fun getPersonas(busqueda: String){
        try{
            viewModel.getPersonasList(busqueda)
            viewModel.ldLstPersonas.observe(this, Observer { list ->
                (rvPersonas.adapter as PersListAdapter).setData(list)
                var vis = View.GONE
                if( list.size == 0 ){
                    vis = View.VISIBLE
                }
                lblNoRecords.visibility = vis
            })
        }
        catch (e: Exception) {

        }
    }

    private fun deletePersona(persona: Persona){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.tlt_lpers))
        builder.setMessage(getString(R.string.msgAreYouSureDel))

        builder.setPositiveButton(R.string.ansYes) { dialog, which ->
            viewModel.deletePersona(persona)
        }
        builder.setNegativeButton(R.string.ansNo) { dialog, which ->

        }
        /*builder.setNeutralButton("Maybe") { dialog, which ->
            Toast.makeText(applicationContext,
                "Maybe", Toast.LENGTH_SHORT).show()
        }*/
        builder.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.mn_search)
        if( searchItem != null ){
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    getPersonas(query)
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if( newText!!.isEmpty() ){
                        getPersonas("")
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
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