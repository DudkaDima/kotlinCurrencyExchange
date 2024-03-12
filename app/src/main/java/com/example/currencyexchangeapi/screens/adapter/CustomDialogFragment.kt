package com.example.currencyexchangeapi.screens.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import com.example.currencyexchangeapi.R
import com.example.currencyexchangeapi.dbHelper.DbHelper

class CustomDialogFragment(
    var mainContext: Context,
    var writeDb: DbHelper,
    var readDb: DbHelper,
    var currencies:List<String>

): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.dialog_fragment_layout, container, false)
        val selectedCurrencyToSell = rootView.findViewById<Spinner>(R.id.toSellCurrencyId)
        val selectedCurrencyToBuy = rootView.findViewById<Spinner>(R.id.toBuyCurrencyId)

        val currenciesAdapter =
            context?.let { it1 ->
                ArrayAdapter(
                    it1,
                    R.layout.dialog_fragment_layout,
                    arrayListOf(currencies)
                )
            }

        selectedCurrencyToSell.adapter = currenciesAdapter;
        selectedCurrencyToBuy.adapter = currenciesAdapter;
        selectedCurrencyToBuy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                Toast.makeText(context, currencies[position], Toast.LENGTH_LONG).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO()
            }

        }
        rootView.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            dismiss()
        }

        rootView.findViewById<Button>(R.id.submitExchange).setOnClickListener {


        }
        return rootView;
    }

    fun performSpinFields (
        currencySet: Set<String>,

    ) =  object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long,
        ) {
            var currencyList = ArrayList(currencySet)
            Toast.makeText(mainContext, currencyList[position], Toast.LENGTH_LONG).show()


        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            Toast.makeText(mainContext, "Nothing selected", Toast.LENGTH_LONG).show()
        }
    }
}