package com.example.currencyexchangeapi.currency

import java.math.BigDecimal


class CurrenciesToExchangeRate (){

    companion object{
        val EURO = Currency("EUR", BigDecimal(0.8))

        val USDOLLAR = Currency("USD",  BigDecimal(1.25))

        fun initCurrencies(): HashMap<String, Currency> {
            var currencies: HashMap<String, Currency> = HashMap();
            currencies.put(this.EURO.getName(), this.EURO);
            currencies.put(this.USDOLLAR.getName(), this.USDOLLAR);

            return currencies;
        }

    }


}