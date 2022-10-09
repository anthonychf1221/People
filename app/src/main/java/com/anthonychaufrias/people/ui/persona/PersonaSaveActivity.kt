package com.anthonychaufrias.people.ui.persona

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anthonychaufrias.people.R
import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.model.Persona
import com.anthonychaufrias.people.model.PersonaSaveResult
import com.anthonychaufrias.people.model.ValidationResult
import com.anthonychaufrias.people.viewmodel.PaisViewModel
import com.anthonychaufrias.people.viewmodel.PersonaViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.lyt_sav_persona.*

class PersonaSaveActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var viewModelPais: PaisViewModel
    private lateinit var viewModelPers: PersonaViewModel
    private lateinit var objPersona: Persona
    private var action: Int = 0
    private var posicionPais: Int = 0
    companion object {
        @JvmStatic val ARG_ITEM: String = "objPersona"
        @JvmStatic val ARG_ACTION: String = "action"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lyt_sav_persona)

        objPersona = intent.getSerializableExtra(ARG_ITEM) as Persona
        action = intent.getIntExtra(ARG_ACTION, Constantes.INSERT) as Int
        setToolbar()

        viewModelPais = ViewModelProvider(this).get(PaisViewModel::class.java)
        viewModelPers = ViewModelProvider(this).get(PersonaViewModel::class.java)
        initUI()
    }

    private fun initUI(){
        setFields()
        loadPaises(objPersona.idPais)
        btnSave.setOnClickListener { view ->
            saveData()
        }

        viewModelPers.liveDataPeopleSave.observe(this, Observer { result ->
            showResult(result)
        })
    }

    private fun setToolbar(){
        var title:String = ""
        if( action == Constantes.INSERT ){
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

    private fun saveData(){
        try{
            val nombres: String = txtNombre.text.toString().trim()
            val documento: String = txtDocumento.text.toString().trim()
            val idPais: Int? = viewModelPais.liveDataCountriesList.value?.get(posicionPais)?.idPais
            val pais: String? = viewModelPais.liveDataCountriesList.value?.get(posicionPais)?.nombre

            objPersona.nombres = nombres
            objPersona.documento = documento
            objPersona.idPais = idPais
            objPersona.pais = pais
            viewModelPers.savePersona(objPersona, action)
        }
        catch(e: Exception){
            print(e.message)
        }
    }


    private fun showResult(result: PersonaSaveResult) {
        txtNombre.setError(null)
        txtDocumento.setError(null)
        when(result){
            is PersonaSaveResult.OK -> {
                finishWithSuccess(result.persona);
            }
            is PersonaSaveResult.OperationFailed -> {
                Snackbar.make(btnSave, getString(R.string.msgFailure), Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
            is PersonaSaveResult.InvalidInputs -> {
                showInputErrors(result.errors)
            }
        }
    }
    private fun finishWithSuccess(persona: Persona){
        Snackbar.make(btnSave, getString(R.string.msgSuccess_Pers), Snackbar.LENGTH_LONG ).setAction("Action", null).show()

        val data = Intent()
        data.putExtra(ARG_ITEM, persona)
        data.putExtra(ARG_ACTION, action)
        setResult(Activity.RESULT_OK, data)

        finish()
    }
    private fun showInputErrors(errors: List<ValidationResult> ){
        for (item in errors) {
            if(item == ValidationResult.NAME_EMPTY){
                txtNombre.setError(getString(R.string.requiredField))
            }
            if(item == ValidationResult.DOCUMENT_ID_INVALID){
                txtDocumento.setError(getString(R.string.docIDLen, Constantes.PERSON_DOCUMENT_LENGTH.toString()))
            }
        }
    }



    private fun loadPaises(selectedId:Int? = 0){
        try{
            viewModelPais.loadPaisesList(selectedId)
            viewModelPais.liveDataCountriesList.observe(this, Observer { list ->
                val paisesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, viewModelPais.countryNamesList)
                paisesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                with(spPais){
                    adapter = paisesAdapter
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