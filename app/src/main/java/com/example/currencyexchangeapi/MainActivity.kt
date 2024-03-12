package com.example.currencyexchangeapi

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangeapi.account.BankAccount
import com.example.currencyexchangeapi.screens.adapter.ListPersonalCurrency
import com.example.currencyexchangeapi.currency.AccountCurrency
import com.example.currencyexchangeapi.dbHelper.DbHelper
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DbHelper(this, null)
        val rDb = DbHelper(this, null);
        db.onCreate(db.readableDatabase);
        rDb.onCreate(rDb.writableDatabase);
        val accountCurrency = AccountCurrency(BigDecimal.valueOf(0.00), "USD");
        val accountCurrency2 = AccountCurrency(BigDecimal.valueOf(1000.00), "EUR");
        val map: HashMap<String, AccountCurrency> = HashMap();
        map.put(accountCurrency.getName(), accountCurrency);
        map.put(accountCurrency2.getName(), accountCurrency2);
        val bankAccount: BankAccount = BankAccount(1, map, 6);
        rDb.initDb(bankAccount);
        val recyclerView = findViewById<RecyclerView>(R.id.rec_view)
        val data = arrayListOf<String>();

        db.getCurrencyAmount().getCurrencies().forEach { entry ->
            data.add(0, "${entry.value.getName()} : ${entry.value.getAmount()}")
        }

        println(db.getCurrencyAmount());


        val balanceAdapter = ListPersonalCurrency(this, data);

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.adapter = balanceAdapter;
        var showDialog: Button = findViewById(R.id.buttonToExchange);
        showDialog.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(this);
            val mView = layoutInflater.inflate(R.layout.dialog_spinner, null);
            dialogBuilder.setTitle("Exchange currency")
            val currencyToSell = mView.findViewById<Spinner>(R.id.toSellCurrencyId);
            val currencyToBuy = mView.findViewById<Spinner>(R.id.toBuyCurrencyId);
            val arrayAdapter = ArrayAdapter<String>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                ArrayList<String>(db.getCurrencyAmount().getCurrencies().keys)
            );
            currencyToBuy.adapter = arrayAdapter;
            currencyToSell.adapter = arrayAdapter;

            dialogBuilder.setPositiveButton(
                "Ok",
                DialogInterface.OnClickListener { dialog, which ->
                    if (currencyToBuy.selectedItem.toString().equals("", true) ||
                        currencyToSell.selectedItem.toString().equals("", true)
                    ) {
                        Toast.makeText(this, "Select currencies", Toast.LENGTH_SHORT).show()
                    } else {
                        rDb.exchange(
                            currencyToSell.selectedItem.toString(),
                            currencyToBuy.selectedItem.toString(),
                            200.00, db.getCurrencyAmount()
                        )
                        println(currencyToSell.selectedItem.toString())
                        println(currencyToBuy.selectedItem.toString())
                        db.getCurrencyAmount().getCurrencies().forEach { entry ->
                            data.add(0, "${entry.value.getName()} : ${entry.value.getAmount()}")
                        }
                    }
                }

            );
            dialogBuilder.setView(mView);
            val alertDialog: AlertDialog = dialogBuilder.create();
            alertDialog.show();

        };
    }
}