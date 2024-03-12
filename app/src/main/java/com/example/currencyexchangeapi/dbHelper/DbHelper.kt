package com.example.currencyexchangeapi.dbHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.sqlite.transaction
import com.example.currencyexchangeapi.account.BankAccount
import com.example.currencyexchangeapi.currency.AccountCurrency
import com.example.currencyexchangeapi.currency.CurrencyToStoreHistory
import com.example.currencyexchangeapi.exchange.Exchange
import com.example.currencyexchangeapi.exchange.ExchangeImpl
import com.example.currencyexchangeapi.exchange.SaveHistory
import com.google.gson.Gson
import java.math.BigDecimal


class DbHelper(
    private val context: Context,
    private val factory: SQLiteDatabase.CursorFactory?,

    ) : SQLiteOpenHelper(
    context, "app", factory, 1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("DROP TABLE IF EXISTS bank_account");
        db!!.execSQL("DROP TABLE IF EXISTS history");
        val accountQuery =
            "CREATE TABLE bank_account (id INT PRIMARY KEY, bank_account_stat TEXT)";
        val historyQuery =
            "CREATE TABLE history (id INTEGER PRIMARY KEY AUTOINCREMENT, historyStat TEXT)";
        db!!.execSQL(accountQuery);
        db!!.execSQL(historyQuery);

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS bank_account");
        db?.execSQL("DROP TABLE IF EXISTS history");
        onCreate(db);
    }

    fun initDb(
    ) {
        val accountCurrency = AccountCurrency(BigDecimal.valueOf(0.00), "USD");
        val accountCurrency2 = AccountCurrency(BigDecimal.valueOf(1000.00), "EUR");
        val map: HashMap<String, AccountCurrency> = HashMap();
        map.put(accountCurrency.getName(), accountCurrency);
        map.put(accountCurrency2.getName(), accountCurrency2);
        val bankAccount: BankAccount = BankAccount(1, map, 6);

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
        bankAccount: BankAccount,
        bankAccountStatic: BankAccount
    ) {

        val  bankAccountToPassCurrencies = bankAccountStatic.getCurrencies();
        val exchanges: Exchange = ExchangeImpl();
        val db = this.writableDatabase;

        db.transaction {
            val gson = Gson();
            val values = ContentValues();

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

            val currencyToStoreHistory = CurrencyToStoreHistory(
                SaveHistory.saveHistory(
                    bankAccountToPassCurrencies, bankAccount, currencyToExchange, currencyToGet
                )
            )

            saveExchangeHistory(currencyToStoreHistory);

            db.update("bank_account", values, "id=?", arrayOf(bankAccount.getId().toString()));
        }
    }

    fun saveExchangeHistory(
        accountHistory: CurrencyToStoreHistory,
    ) {
        val db = this.writableDatabase;
        val gson = Gson();

        val values = ContentValues();

        values.put(
            "historyStat", gson.toJson(
                accountHistory
            )
        )
        db.insert("history", null, values)
    }

    fun getExchangeHistory(): CurrencyToStoreHistory {
        val db = this.readableDatabase;

        val result = db.rawQuery(
            "SELECT * FROM history ORDER BY id DESC LIMIT 1", null
        )
        result.moveToFirst();
        val gson = Gson();

        return gson.fromJson(result.getString(1), CurrencyToStoreHistory::class.java)
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

