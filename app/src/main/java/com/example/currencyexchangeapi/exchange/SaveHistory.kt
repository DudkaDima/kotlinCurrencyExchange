package com.example.currencyexchangeapi.exchange

import com.example.currencyexchangeapi.account.BankAccount
import com.example.currencyexchangeapi.currency.AccountCurrency
import com.example.currencyexchangeapi.currency.Currency
import com.example.currencyexchangeapi.currency.CurrencyToStoreHistory

class SaveHistory() {

    companion object{
        fun saveHistory(
            bankAccountBeforeSave: HashMap<String, AccountCurrency>, bankAccountToSave: BankAccount, currencyToSell: String,
            currencyToBuy: String,
        ):List<Currency> {
            val historyToSave: CurrencyToStoreHistory;
            var listOf:ArrayList<Currency> = ArrayList();
            listOf.add(Currency(currencyToSell, bankAccountBeforeSave.get(currencyToSell)?.getAmount()!!.stripTrailingZeros().subtract(
                bankAccountToSave.getCurrencies().get(currencyToSell)!!.getAmount().stripTrailingZeros()
            )))
            listOf.add(Currency(currencyToBuy, bankAccountBeforeSave.get(currencyToBuy)?.getAmount()!!.add(
                bankAccountToSave.getCurrencies().get(currencyToBuy)!!.getAmount().stripTrailingZeros()
            )))


            return listOf;
        }
    }
}