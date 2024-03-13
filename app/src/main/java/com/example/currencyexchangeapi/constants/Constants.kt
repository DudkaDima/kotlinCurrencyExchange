package com.example.currencyexchangeapi.constants

import com.example.currencyexchangeapi.R
import java.math.BigDecimal

class Constants {
    companion object {
        val EXCHANGE_CURRENCY_FEE:Double = 0.007;
        val DOLLAR_RATE_TO_EURO = BigDecimal(1.25);
        val EURO_RATE_TO_DOLLAR = BigDecimal(0.8);
    }

}