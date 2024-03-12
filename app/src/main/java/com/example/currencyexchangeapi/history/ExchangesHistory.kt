package com.example.currencyexchangeapi.history

class ExchangesHistory(
    private var currencyName: String,
    private var operation: String,
    private var currencyAmount: String,
    private var operationImage: Int,
) {

    fun getName() = this.currencyName;
    fun getOperation() = this.operation;
    fun getCurrencyAmount() = this.currencyAmount;
    fun getOperationImage() = this.operationImage;

    fun setCurrencyName(currencyName: String) {
        this.currencyName = currencyName;
    }

    fun setOperation(operation: String) {
        this.operation = operation;
    }

    fun setCurrencyAmount(currencyAmount: String) {
        this.currencyAmount = currencyAmount;
    }

    fun setOperationImage(operationImage: Int) {
        this.operationImage = operationImage;
    }

    override fun toString(): String {
        var s = "St"
        return " $operation   $currencyAmount \t $currencyName";
    }
}