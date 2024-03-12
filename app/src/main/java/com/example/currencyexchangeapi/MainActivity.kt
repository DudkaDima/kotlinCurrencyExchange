package com.example.currencyexchangeapi

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangeapi.account.BankAccount
import com.example.currencyexchangeapi.constants.Constants
import com.example.currencyexchangeapi.screens.adapter.ListPersonalCurrency
import com.example.currencyexchangeapi.currency.AccountCurrency
import com.example.currencyexchangeapi.dbHelper.DbHelper
import com.example.currencyexchangeapi.history.ExchangesHistory
import com.example.currencyexchangeapi.screens.adapter.ExchangesAdapter
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DbHelper(this, null)
        val rDb = DbHelper(this, null);
        rDb.onCreate(rDb.writableDatabase);
        db.onCreate(db.readableDatabase);
        rDb.initDb();

        //ViewForCurrentUserBalance
        val balanceView = findViewById<RecyclerView>(R.id.rec_view)
        //ViewForUserHistory
        val historyBalance = findViewById<RecyclerView>(R.id.history_view)


        //Init history
        var historyList: ArrayList<ExchangesHistory> = ArrayList();
        historyBalance.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        historyBalance.adapter = ExchangesAdapter(this, historyList)
        val data = arrayListOf<String>();


        //Init balance
        db.getCurrencyAmount().getCurrencies().forEach { entry ->
            data.add("${entry.value.getName()} : ${entry.value.getAmount()}")
        }

        //Adapter for Balance
        val balanceAdapter = ListPersonalCurrency(this, data);

        balanceView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        );
        balanceView.adapter;
        balanceView.adapter = balanceAdapter;



        //Button for making exchanges
        var showDialog: Button = findViewById(R.id.buttonToExchange);
        showDialog.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(this);
            val mView = layoutInflater.inflate(R.layout.dialog_spinner, null);
            dialogBuilder.setTitle("Exchange currency")
            val userData = mView.findViewById<EditText>(R.id.amountCurrencyToExchange);
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
                "Exchange"

            ) { dialog, which ->
                if (currencyToBuy.selectedItem.toString().equals("", true) ||
                    currencyToSell.selectedItem.toString().equals("", true)
                ) {
                    Toast.makeText(this, "Select currencies", Toast.LENGTH_SHORT).show()
                } else {

                    try {
                        rDb.exchange(
                            currencyToSell.selectedItem.toString(),
                            currencyToBuy.selectedItem.toString(),
                            BigDecimal(userData.text.toString()).toDouble(),
                            db.getCurrencyAmount(),
                            db.getCurrencyAmount()
                        )
                        var i = 0;

                        db.getCurrencyAmount().getCurrencies().forEach { entry ->
                            data.set(
                                i,
                                "${entry.value.getName()} : ${
                                    String.format(
                                        "%.1f",
                                        entry.value.getAmount().toDouble()
                                    )
                                }"
                            )
                            i++;
                        }

                        val historyFormat = db.getExchangeHistory();
                        historyList.add(
                            ExchangesHistory(
                                currencyToSell.selectedItem.toString(),
                                "sell",
                                String.format(
                                    "%.1f",
                                    historyFormat.getCurrencies().get(0).getPrice().toDouble()
                                ),
                                R.drawable.sell
                            )
                        )

                        historyList.add(
                            ExchangesHistory(
                                currencyToBuy.selectedItem.toString(),
                                "buy",
                                "+${
                                    String.format(
                                        "%.1f",
                                        historyFormat.getCurrencies().get(1).getPrice().toDouble()
                                    )
                                }",
                                R.drawable.buy
                            )
                        )
                    } catch (
                        ex: Exception
                    ) {
                        Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
                    }


                    balanceView.adapter;
                    balanceView.adapter = balanceAdapter;
                    historyBalance.adapter = ExchangesAdapter(this, historyList)


                }
            };
            dialogBuilder.setNegativeButton("Dismiss"
            ) { dialog, which ->
                dialog.dismiss();
            };
            dialogBuilder.setView(mView);
            val alertDialog: AlertDialog = dialogBuilder.create();
            alertDialog.show();


        };

    }
}