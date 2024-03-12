package com.example.currencyexchangeapi.currency

import java.math.BigDecimal

class Currency (
    private val name:String,
    private val price: BigDecimal,
){
    fun getName():String {
        return this.name;
    }

    fun getPrice():BigDecimal {
        return this.price;
    }

    override fun toString(): String {
        return "Currency(name='$name', price=$price)"
    }


}