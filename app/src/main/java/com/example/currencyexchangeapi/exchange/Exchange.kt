package com.example.currencyexchangeapi.exchange

import com.example.currencyexchangeapi.account.BankAccount
import com.example.currencyexchangeapi.currency.AccountCurrency

interface Exchange {
    fun  exchange(
        currencyToSellName: String, currencyToBuyName: String, amountToExchange: Double,
        bankAccount: BankAccount
    ): BankAccount;

}