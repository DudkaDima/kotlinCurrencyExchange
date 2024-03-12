package com.example.currencyexchangeapi.exchange

import android.annotation.SuppressLint
import com.example.currencyexchangeapi.account.BankAccount
import com.example.currencyexchangeapi.constants.Constants
import com.example.currencyexchangeapi.currency.AccountCurrency
import com.example.currencyexchangeapi.currency.Currencies
import com.example.currencyexchangeapi.currency.Currency
import java.math.BigDecimal
import kotlin.Exception


class ExchangeImpl : Exchange {

    val currencies: HashMap<String, Currency> = Currencies.initCurrencies();

    @SuppressLint("SuspiciousIndentation")
    override fun exchange(
        currencyToSellName: String, currencyToBuyName: String,
        amountToExchange: Double,
        bankAccount: BankAccount
    ): BankAccount {


        val accountCurrencyToSellAmount: AccountCurrency =
            bankAccount.getCurrencies().get(currencyToSellName)!!;
        val accountCurrencyToBuyAmount: AccountCurrency =
            bankAccount.getCurrencies().get(currencyToBuyName)!!;


        bankAccount.getCurrencies().put(
            currencyToSellName,
            currencyToSellAmountAfterExchange(
                accountCurrencyToSellAmount,
                amountToExchange,
                bankAccount.getExchanges()
            )
        );

        bankAccount.getCurrencies().put(
            currencyToBuyName,
            currencyToBuyAmountAfterExchange(
                accountCurrencyToBuyAmount,
                amountToExchange
            )
        )
        bankAccount.setExchanges(bankAccount.getExchanges() + 1);
        return bankAccount;
    }

    private fun currencyToSellAmountAfterExchange(
        accountCurrency: AccountCurrency,
        amountToExchange: Double, exchangeCount: Int
    ): AccountCurrency {
        val calculatedAccountCurrency: AccountCurrency;
        if (exchangeCount < 5) {
            calculatedAccountCurrency = AccountCurrency(
                accountCurrency.getAmount().subtract(BigDecimal.valueOf(amountToExchange)),
                accountCurrency.getName()
            );

        } else {
            calculatedAccountCurrency = AccountCurrency(
                accountCurrency.getAmount().subtract(
                    BigDecimal.valueOf(amountToExchange)
                        .add(
                            BigDecimal.valueOf(amountToExchange)
                                .multiply(BigDecimal.valueOf(Constants.EXCHANGE_CURRENCY_FEE))
                        )
                ).stripTrailingZeros(), accountCurrency.getName()
            );
        }
        if (calculatedAccountCurrency.getAmount() < BigDecimal.valueOf(0)) {

            throw Exception("Not enough money to exchange on ${accountCurrency.getName()} bill")
        } else {
            return calculatedAccountCurrency;
        }

    }


    private fun currencyToBuyAmountAfterExchange(
        accountCurrency: AccountCurrency,
        amountToExchange: Double,
    ): AccountCurrency {
        val currencies = Currencies.initCurrencies();
        return AccountCurrency(
            accountCurrency.getAmount().add(
                BigDecimal.valueOf(amountToExchange)
                    .multiply(
                        currencies.get(accountCurrency.getName())?.getPrice()
                    ).stripTrailingZeros()
            ), accountCurrency.getName()
        );
    }
}