package com.example.currencyexchangeapi.constants

import com.example.currencyexchangeapi.R
import java.math.BigDecimal

class Constants {
    companion object {
        val EXCHANGE_CURRENCY_FEE:Double = 0.007 ;
        var currencies_base: HashMap<String, Int> = HashMap();
        
        fun initCurrencies() {
            this.currencies_base.put("EUR", R.xml.eur_currency_price);
            this.currencies_base.put("USD", R.xml.usd_currency_price)
        }
    }

}