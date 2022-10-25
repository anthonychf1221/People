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
import com.anthonychaufrias.people.core.Constantes
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
            intent.putExtra(PersonaSaveActivity.ARG_ACTION, Constantes.INSERT)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            val persona: Persona = data.getSerializableExtra(PersonaSaveActivity.ARG_ITEM) as Persona
            val action: Int = data.getIntExtra(PersonaSaveActivity.ARG_ACTION, Constantes.INSERT) as Int
            viewModel.refreshList(persona, action)
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
        loadPersonas("")
        viewModel.liveDataPeopleList.observe(this, Observer { list ->
            (rvPersonas.adapter as PersonaListAdapter).setData(list)
            setMessageNoRecords(list.size)
        })
    }

    private fun setToolbar(){
        val title: String = getString(R.string.tlt_lpers)
        this.supportActionBar!!.setTitle(title)
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadPersonas(busqueda: String){
        try{
            viewModel.loadListaPersonas(busqueda)
        }
        catch (e: Exception) {
            print(e.message)
        }
    }

    private fun setMessageNoRecords(size: Int){
        lblNoRecords.visibility = if(size == 0) View.VISIBLE else View.GONE
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
                    loadPersonas(query)
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if( newText!!.isEmpty() ){
                        loadPersonas("")
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

}