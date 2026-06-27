package mate.academy

private const val SAMPLE_AMOUNT = 100.0

fun main() {
    val financialService = FinancialService()

    val transferMessage = financialService.transferFunds(
        source = AccountNumber("1234567890"),
        destination = AccountNumber("0987654321"),
        amount = CurrencyAmount(SAMPLE_AMOUNT),
        currencyCode = CurrencyCode("USD"),
        transactionId = TransactionId("TX12345")
    )
    println(transferMessage)

    val convertedAmount = financialService.convertCurrency(
        amount = CurrencyAmount(SAMPLE_AMOUNT),
        fromCurrency = CurrencyCode("USD"),
        toCurrency = CurrencyCode("EUR")
    )
    println("Converted amount: ${convertedAmount.amount} EUR")
}
