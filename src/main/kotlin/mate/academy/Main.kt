package mate.academy

fun main() {
    val financialService = FinancialService()

    val transferMessage = financialService.transferFunds(
        source = AccountNumber("1234567890"),
        destination = AccountNumber("0987654321"),
        amount = CurrencyAmount(100.0),
        currencyCode = CurrencyCode("USD"),
        transactionId = TransactionId("TX12345")
    )
    println(transferMessage)

    val convertedAmount = financialService.convertCurrency(
        amount = CurrencyAmount(100.0),
        fromCurrency = CurrencyCode("USD"),
        toCurrency = CurrencyCode("EUR")
    )
    println("Converted amount: ${convertedAmount.amount} EUR")
}
