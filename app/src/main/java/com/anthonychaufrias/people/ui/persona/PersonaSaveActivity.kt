package com.anthonychaufrias.people.ui.persona

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anthonychaufrias.people.R
import com.anthonychaufrias.people.config.Constantes
import com.anthonychaufrias.people.model.Persona
import com.anthonychaufrias.people.viewmodel.PaisViewModel
import com.anthonychaufrias.people.viewmodel.PersonaViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.lyt_sav_persona.*

class PersonaSaveActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var viewModelPais: PaisViewModel
    private lateinit var viewModelPers: PersonaViewModel
    private lateinit var objPersona: Persona
    private var posicionPais: Int = 0
    companion object {
        @JvmStatic val ARG_ITEM: String = "objPersona"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lyt_sav_persona)

        objPersona = intent.getSerializableExtra(ARG_ITEM) as Persona
        setToolbar()

        viewModelPais = ViewModelProvider(this).get(PaisViewModel::class.java)
        viewModelPers = ViewModelProvider(this).get(PersonaViewModel::class.java)
        initUI()
    }

    private fun initUI(){
        setFields()
        getPaises(objPersona.idPais)
        btnSave.setOnClickListener { view ->
            saveData(view)
        }
    }

    private fun setToolbar(){
        var title:String = ""
        if( objPersona.idPersona == 0 ){
            title = getString(R.string.tlt_nper)
        }
        else{
            title = getString(R.string.tlt_eper)
        }
        this.supportActionBar!!.setTitle(title)
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setFields(){
        if( objPersona.idPersona > 0 ){
            txtNombre.setText(objPersona.nombres)
            txtDocumento.setText(objPersona.documento)
        }
    }

    private fun saveData(view: View){
        try{
            val nombres: String = txtNombre.text.toString().trim()
            val documento: String = txtDocumento.text.toString().trim()
            val idPais: Int? = viewModelPais.liveDataCountriesList.value?.get(posicionPais)?.idPais
            val pais: String? = viewModelPais.liveDataCountriesList.value?.get(posicionPais)?.nombre

            if( !isFormValidated(nombres, documento) ){
                return;
            }

            objPersona.nombres = nombres
            objPersona.documento = documento
            objPersona.idPais = idPais
            objPersona.pais = pais
            viewModelPers.savePersona(objPersona)

            viewModelPers.liveDataPeopleSave.observe(this, Observer { persona ->
                if (persona != null) {
                    Snackbar.make(view, getString(R.string.msgSuccess_Pers), Snackbar.LENGTH_LONG )
                    .setAction("Action", null)
                    .show()
                    finish()
                } else {
                    Snackbar.make(view, getString(R.string.msgFailure), Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show()
                }
            })

        }
        catch(e: Exception){
            print(e.message)
        }
    }

    private fun isFormValidated(nombre: String, docID: String):Boolean {
        var x:Boolean = true
        if( nombre.isEmpty() ){
            txtNombre.setError(getString(R.string.requiredField))
            x = false
        }
        if( docID.isEmpty() ){
            txtDocumento.setError(getString(R.string.requiredField))
            x = false
        }
        else if(docID.length != Constantes.PERSON_DOCUMENT_LENGTH){
            txtDocumento.setError(getString(R.string.docIDLen, Constantes.PERSON_DOCUMENT_LENGTH.toString()))
            x = false
        }
        return x
    }

    private fun getPaises(selectedId:Int? = 0){
        try{
            viewModelPais.getPaisesList(selectedId)
            viewModelPais.liveDataCountriesList.observe(this, Observer { list ->
                val aadapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, viewModelPais.countryNamesList)
                aadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                with(spPais){
                    adapter = aadapter
                    setSelection(viewModelPais.selectedIndex, false)
                    onItemSelectedListener = this@PersonaSaveActivity
                    prompt = getString(R.string.lblCbPais)
                    gravity = Gravity.CENTER
                }
            })
        }
        catch (e: Exception) {
            print(e.message)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        posicionPais = position
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}