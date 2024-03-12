package com.example.currencyexchangeapi.account

import com.example.currencyexchangeapi.currency.AccountCurrency

class BankAccount (
    private var id: Int,
    private var currencies: HashMap<String, AccountCurrency>,
    private var exchanges: Int,
) {
    constructor(bankAccount: BankAccount) : this(bankAccount.getId(), bankAccount.getCurrencies(), bankAccount.getExchanges())  {
        this.currencies = bankAccount.getCurrencies();
        this.exchanges = bankAccount.getExchanges();
    }
    fun getCurrencies(): HashMap<String, AccountCurrency> {
        return currencies
    }

    fun setCurrencies(currencies: HashMap<String, AccountCurrency>) {
        this.currencies.clear()
        this.currencies.putAll(currencies)
    }

    fun getExchanges(): Int {
        return exchanges
    }

    fun setExchanges(exchanges: Int) {
        this.exchanges = exchanges
    }

    fun getId(): Int {
        return this.id;
    }

    fun setId(id: Int) {
        this.id = id
    }

    override fun toString(): String {
        return "BankAccount(id=$id, currencies=$currencies, exchanges=$exchanges)"
    }

    fun getAllCurrencies(): Set<String>{
        return currencies.keys;
    }

}