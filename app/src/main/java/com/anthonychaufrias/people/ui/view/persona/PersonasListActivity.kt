package com.anthonychaufrias.people.ui.view.persona

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
import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.ui.viewmodel.PersonaViewModel
import kotlinx.android.synthetic.main.lyt_lst_personas.*

class PersonasListActivity : AppCompatActivity() {
    private lateinit var viewModel: PersonaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lyt_lst_personas)
        setToolbar()

        viewModel = ViewModelProvider(this).get(PersonaViewModel::class.java)
        initUI()
        fab.setOnClickListener { view ->
            val intent = Intent(this, PersonaSaveActivity::class.java)
            intent.putExtra(PersonaSaveActivity.ARG_ITEM, Persona(0, "", "", 0, ""))
            startActivity(intent)
        }
    }

    private fun initUI(){
        rvPersonas.layoutManager = LinearLayoutManager(this)
        rvPersonas.adapter = PersonaListAdapter({
            val intent = Intent(this, PersonaSaveActivity::class.java)
            intent.putExtra(PersonaSaveActivity.ARG_ITEM, it)
            startActivity(intent)
        },{
            deletePersona(it)
        })
        getPersonas("")
    }

    private fun setToolbar(){
        val title: String = getString(R.string.tlt_lpers)
        this.supportActionBar!!.setTitle(title)
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun getPersonas(busqueda: String){
        try{
            viewModel.getListaPersonas(busqueda)
            viewModel.liveDataPeopleList.observe(this, Observer { list ->
                (rvPersonas.adapter as PersonaListAdapter).setData(list)
                var vis = View.GONE
                if( list.size == 0 ){
                    vis = View.VISIBLE
                }
                lblNoRecords.visibility = vis
            })
        }
        catch (e: Exception) {
            print(e.message)
        }
    }

    private fun deletePersona(persona: Persona){
        try{
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.tlt_lpers))
            builder.setMessage(getString(R.string.msgAreYouSureDel))

            builder.setPositiveButton(R.string.ansYes) { dialog, which ->
                viewModel.deletePersona(persona)
            }
            builder.setNegativeButton(R.string.ansNo) { dialog, which ->
            }
            builder.show()
        }
        catch(e: Exception){
            print(e.message)
        }
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

}