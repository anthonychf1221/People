package com.anthonychaufrias.people.ui.persona

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anthonychaufrias.people.R
import com.anthonychaufrias.people.model.Persona
import com.anthonychaufrias.people.viewmodel.PaisVM
import com.anthonychaufrias.people.viewmodel.PersonaVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.lyt_sav_persona.*

class PersonaSaveActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var viewModelPais: PaisVM
    private lateinit var viewModelPers: PersonaVM
    private lateinit var objPer: Persona
    private var selPosPais: Int = 0
    companion object {
        @JvmStatic val ARG_ITEM: String = "objPer"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lyt_sav_persona)

        var tlt:String = ""
        // https://stackoverflow.com/questions/37949024/kotlin-typecastexception-null-cannot-be-cast-to-non-null-type-com-midsizemango
        // objPer = intent.getSerializableExtra(ARG_ITEM) as? Persona
        objPer = intent.getSerializableExtra(ARG_ITEM) as Persona
        if( objPer.idp == 0 ){
            tlt = getString(R.string.tlt_nper)
        }
        else{
            tlt = getString(R.string.tlt_eper)
            //Log.e("00000000 ", ""+objPer?.nombres)
            txtNombre.setText(objPer.nombres)
            txtDocumento.setText(objPer.documento)
        }
        this.supportActionBar!!.setTitle(tlt)

        viewModelPais = ViewModelProvider(this).get(PaisVM::class.java)
        viewModelPers = ViewModelProvider(this).get(PersonaVM::class.java)
        initUI()
    }

    private fun initUI(){
        getPaises(objPer.idpais)
        btnSave.setOnClickListener { view ->
            saveData(view)
        }
    }

    private fun saveData(view: View){
        try{
            val nombre: String = txtNombre.text.toString().trim()
            val docID: String = txtDocumento.text.toString().trim()
            val idPais: Int? = viewModelPais.lstPaises.value?.get(selPosPais)?.idpais
            val nbPais: String? = viewModelPais.lstPaises.value?.get(selPosPais)?.nombre

            if( !checkForm(nombre, docID) ){
                return;
            }

            objPer.nombres = nombre
            objPer.documento = docID
            objPer.idpais = idPais
            objPer.nombpais = nbPais
            if( objPer.idp == 0 ){
                //viewModelPers.addPersona(Persona(0, nombre, docID, idPais, nbPais))
                viewModelPers.addPersona(objPer)
            }
            else{
                viewModelPers.updatePersona(objPer)
            }

            viewModelPers.savePersona.observe(this, Observer { persona ->
                if (persona != null) {
                    Snackbar.make(
                        view,
                        getString(R.string.msgSuccess_Pers),
                        Snackbar.LENGTH_LONG )
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

        }
    }

    private fun checkForm(nombre: String, docID: String):Boolean {
        var x:Boolean = true
        if( nombre.equals("") ){
            txtNombre.setError("Este campo es obligatorio")
            x = false
        }
        if( docID.equals("") ){
            txtDocumento.setError("Este campo es obligatorio")
            x = false
        }
        return x
    }

    private fun getPaises(selectedId:Int? = 0){
        try{
            viewModelPais.getPaisesList()
            viewModelPais.lstPaises.observe(this, Observer { list ->
                var lstStrPais: MutableList<String> = mutableListOf()
                var idx:Int = 0
                var i:Int = 0
                // https://www.programiz.com/kotlin-programming/for-loop
                for(pais in list){
                    lstStrPais.add(pais.nombre)
                    if( pais.idpais == selectedId ){
                        idx = i
                    }
                    i++
                }
                //var languages = arrayOf("Java", "PHP", "Kotlin", "Javascript", "Python", "Swift")
                var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, lstStrPais)
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                with(spPais){
                    adapter = aa
                    setSelection(idx, false)
                    onItemSelectedListener = this@PersonaSaveActivity
                    prompt = getString(R.string.lblCbPais)
                    gravity = Gravity.CENTER
                }
            })
        }
        catch (e: Exception) {
            Log.e("initUI-Exception==", e.message.toString());
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?,
                                view: View?, position: Int, id: Long) {
        //Toast.makeText(this, viewModel.lstPaises.value?.get(position)?.nombre, Toast.LENGTH_LONG).show()
        selPosPais = position
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

}