package com.example.currencyexchangeapi.currency

import java.math.BigDecimal

class CurrencyToStoreHistory(

    private var currencies: List<Currency>,

    ) {

    fun getCurrencies(): List<Currency> {
        return this.currencies;
    }
    fun setCurrencies(listCurrencies: List<Currency>) {
        this.currencies = listCurrencies;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrencyToStoreHistory

        return currencies == other.currencies
    }

    override fun hashCode(): Int {
        return currencies.hashCode()
    }

    override fun toString(): String {
        return "CurrencyToStoreHistory(currencies=$currencies)"
    }
}