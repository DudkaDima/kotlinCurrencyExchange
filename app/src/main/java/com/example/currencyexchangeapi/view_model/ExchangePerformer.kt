//package com.example.currencyexchangeapi.view_model
//
//import android.content.Context
//import android.view.View
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import android.widget.Spinner
//import android.widget.Toast
//import com.example.currencyexchangeapi.R
//
//class ExchangePerformer (
//    context: Context,
//    list:List<String>,
//
//) {
//    val spinnerId = findViewById<Spinner>(R.id.spinId);
//    val currencies = bankAccount.getAllCurrencies();
//    val currenciesAdapter =
//        ArrayAdapter(this, R.layout.dialog_fragment_layout, arrayListOf(dialog))
//    spinnerId.adapter = currenciesAdapter;
//    spinnerId?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//        override fun onItemSelected(
//            parent: AdapterView<*>?,
//            view: View?,
//            position: Int,
//            id: Long,
//        ) {
//            Toast.makeText(this)
//            TODO("Not yet implemented")
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>?) {
//            TODO("Not yet implemented")
//        }
//    }
//}