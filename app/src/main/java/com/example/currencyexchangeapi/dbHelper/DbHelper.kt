package com.example.currencyexchangeapi.dbHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.sqlite.transaction
import com.beust.klaxon.Klaxon
import com.example.currencyexchangeapi.MainActivity
import com.example.currencyexchangeapi.account.BankAccount
import com.example.currencyexchangeapi.exchange.Exchange
import com.example.currencyexchangeapi.exchange.ExchangeImpl
import com.google.gson.Gson
import kotlin.math.log


class DbHelper(
    private val context: Context,
    private val factory: SQLiteDatabase.CursorFactory?,

) : SQLiteOpenHelper(
    context, "app", factory, 1
) {


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("DROP TABLE IF EXISTS bank_account");
        val query =
            "CREATE TABLE bank_account (id INT PRIMARY KEY, bank_account_stat TEXT)";
        db!!.execSQL(query);


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS bank_account");
        onCreate(db);
    }

    fun initDb(
        bankAccount: BankAccount,
    ) {
        val gson: Gson = Gson();
        val values = ContentValues();
        values.put("id", 1)
        values.put("bank_account_stat", gson.toJson(bankAccount));
        val db = this.writableDatabase;
        db.insert("bank_account", null, values);
    }

    fun exchange(
        currencyToExchange: String, currencyToGet: String,
        amountToExchange: Double,
        bankAccount: BankAccount
    ) {
        val db = this.writableDatabase;
        db.transaction {
            val gson: Gson = Gson();
            val values = ContentValues();
            val exchanges: Exchange = ExchangeImpl();

            try {
                values.put(
                    "bank_account_stat", gson.toJson(
                        exchanges.exchange(
                            currencyToExchange,
                            currencyToGet,
                            amountToExchange,
                            bankAccount
                        )
                    )
                );
                db.update("bank_account", values, "id=?", arrayOf(bankAccount.getId().toString()));

            } catch (ex: Exception) {
                println(ex.message);
            }
        }
    }

    fun getCurrencyAmount(): BankAccount {
        val db = this.readableDatabase;

        val result = db.rawQuery(
            "SELECT * FROM bank_account", null
        );
        result.moveToFirst();
        val gson: Gson = Gson();
        return gson.fromJson(result.getString(1), BankAccount::class.java);
    }




}

