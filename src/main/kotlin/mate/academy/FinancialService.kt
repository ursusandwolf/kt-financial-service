package mate.academy

private const val USD_CODE = "USD"
private const val EUR_CODE = "EUR"
private const val GBP_CODE = "GBP"
private const val USD_TO_EUR_RATE = 0.93
private const val USD_TO_GBP_RATE = 0.82

class FinancialService {
    fun transferFunds(
        source: AccountNumber,
        destination: AccountNumber,
        amount: CurrencyAmount,
        currencyCode: CurrencyCode,
        transactionId: TransactionId
    ) : String {
        return "Transferred ${amount.amount} ${currencyCode.code} " +
            "from ${source.id} to ${destination.id}. " +
            "Transaction ID: ${transactionId.id}"
    }

    fun convertCurrency(
        amount: CurrencyAmount,
        fromCurrency: CurrencyCode,
        toCurrency: CurrencyCode
    ): CurrencyAmount {
        val exchangeRate = getExchangeRate(fromCurrency, toCurrency)
        return CurrencyAmount(amount.amount * exchangeRate)
    }

    private fun getExchangeRate(fromCurrency: CurrencyCode, toCurrency: CurrencyCode): Double {
        // Placeholder exchange rate - in a real application, you'd fetch this from a financial API
        return when {
            fromCurrency.code == USD_CODE && toCurrency.code == EUR_CODE -> USD_TO_EUR_RATE
            fromCurrency.code == USD_CODE && toCurrency.code == GBP_CODE -> USD_TO_GBP_RATE
            else -> 1.0
        }
    }
}

@JvmInline
value class AccountNumber(val id: String) {
    init {
        require(id.matches(Regex("\\d{10}"))) { "Account number must contain exactly 10 digits" }
    }
}

@JvmInline
value class CurrencyAmount(val amount: Double) {
    init {
        require(amount >= 0) { "Currency amount must be non-negative" }
    }
}

@JvmInline
value class CurrencyCode(val code: String) {
    init {
        require(code.matches(Regex("[A-Z]{3}"))) {
            "Currency code must be exactly 3 uppercase letters"
        }
    }
}

@JvmInline
value class TransactionId(val id: String) {
    init {
        require(id.isNotBlank()) { "Transaction ID must not be empty" }
    }
}
