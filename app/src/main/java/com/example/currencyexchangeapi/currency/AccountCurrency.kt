package com.example.currencyexchangeapi.currency

import java.math.BigDecimal

class AccountCurrency(
    private var amount: BigDecimal,
    private val name: String,
)
{
    fun getAmount(): BigDecimal {
        return this.amount;

    }

    fun getName(): String {
        return this.name;
    }

    fun setAmount(amount: BigDecimal)  {
        this.amount = amount;
    }

    fun isAmountOk(): Boolean {
        return this.amount >= BigDecimal.ZERO;
    }

    override fun toString(): String {
        return "Currency(amount=$amount, name='$name')"
    }


}