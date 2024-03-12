package com.example.currencyexchangeapi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangeapi.account.BankAccount
import com.example.currencyexchangeapi.screens.adapter.CustomDialogFragment
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

        val recyclerView = findViewById<RecyclerView>(R.id.rec_view)
        val data = arrayListOf<String>();


        bankAccount.getCurrencies().forEach { entry ->
            data.add("${entry.value.getName()} : ${entry.value.getAmount()}")
        }

        val balanceAdapter = ListPersonalCurrency(this, data);





        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.adapter = balanceAdapter;


        rDb.initDb(bankAccount);
        println(db.getCurrencyAmount())
        println(db.getCurrencyAmount())
        rDb.exchange("EUR", "USD", 50.00, db.getCurrencyAmount());
        println(db.getCurrencyAmount());

        var makeExchange: TextView = findViewById(R.id.exchangeBtn);
        makeExchange.setOnClickListener {
            var dialog = CustomDialogFragment(
                this,
                db,
                rDb,
                ArrayList(db.getCurrencyAmount().getCurrencies().keys)
            );

            val currencies = bankAccount.getAllCurrencies();


            val toSellCurrencyChose = findViewById<Spinner>(R.id.toSellCurrencyId);
//            val toBuyCurrencyChose = findViewById<Spinner>(R.id.toBuyCurrencyId);
            R.id.exchangeBtn.apply {
                dialog.show(supportFragmentManager, "customDialog")
            }


//            toSellCurrencyChose?.onItemSelectedListener = performSpinFields(currencies, this);

        }

    }
}