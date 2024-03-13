package com.example.currencyexchangeapi.currency

import com.example.currencyexchangeapi.constants.Constants
import java.math.BigDecimal


class CurrenciesToExchangeRate (){
    companion object{
        val EURO = Currency("EUR", Constants.EURO_RATE_TO_DOLLAR)

        val USDOLLAR = Currency("USD", Constants.DOLLAR_RATE_TO_EURO)
        fun initCurrencies(): HashMap<String, Currency> {
            var currencies: HashMap<String, Currency> = HashMap();
            currencies.put(this.EURO.getName(), this.EURO);
            currencies.put(this.USDOLLAR.getName(), this.USDOLLAR);

            return currencies;
        }
    }
}