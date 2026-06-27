package mate.academy

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class FinancialServiceTest {

    private val financialService = FinancialService()

    @Test
    fun transferFunds_ValidInput_SuccessfulTransfer() {
        // given
        val source = AccountNumber("1234567890")
        val destination = AccountNumber("0987654321")
        val amount = CurrencyAmount(100.0)
        val currencyCode = CurrencyCode("USD")
        val transactionId = TransactionId("txn123")
        val expected =
            "Transferred 100.0 USD from 1234567890 to 0987654321. Transaction ID: txn123"

        // when
        val result = financialService.transferFunds(
            source,
            destination,
            amount,
            currencyCode,
            transactionId
        )

        // then
        assertEquals(expected, result)
    }

    @Test
    fun transferFunds_InvalidSourceAccount_ThrowsException() {
        // given
        val invalidSource = "12345" // Not matching the required format

        // when & then
        assertThrows(IllegalArgumentException::class.java) {
            AccountNumber(invalidSource)
        }
    }

    @Test
    fun transferFunds_InvalidDestinationAccount_ThrowsException() {
        // given
        val invalidDestination = "12345" // Not matching the required format

        // when & then
        assertThrows(IllegalArgumentException::class.java) {
            AccountNumber(invalidDestination)
        }
    }

    @Test
    fun transferFunds_NegativeTransferAmount_ThrowsException() {
        // given
        val negativeAmount = -100.0

        // when & then
        assertThrows(IllegalArgumentException::class.java) {
            CurrencyAmount(negativeAmount)
        }
    }

    @Test
    fun transferFunds_InvalidCurrencyCode_ThrowsException() {
        // given
        val invalidCurrencyCode = "US" // Not matching the required format

        // when & then
        assertThrows(IllegalArgumentException::class.java) {
            CurrencyCode(invalidCurrencyCode)
        }
    }

    @Test
    fun transferFunds_InvalidTransactionId_ThrowsException() {
        // given
        val emptyTransactionId = "" // Not matching the required format

        // when & then
        assertThrows(IllegalArgumentException::class.java) {
            TransactionId(emptyTransactionId)
        }
    }

    @Test
    fun transferFunds_SameSourceAndDestination_ExpectedBehavior() {
        // given
        val sourceAndDestination = AccountNumber("1234567890")
        val amount = CurrencyAmount(100.0)
        val currencyCode = CurrencyCode("USD")
        val transactionId = TransactionId("txn123")
        val expected =
            "Transferred 100.0 USD from 1234567890 to 1234567890. Transaction ID: txn123"

        // when
        val result = financialService.transferFunds(
            sourceAndDestination,
            sourceAndDestination,
            amount,
            currencyCode,
            transactionId
        )

        // then
        assertEquals(expected, result)
    }

    @Test
    fun convertCurrency_ValidInput_SuccessfulConversion() {
        // given
        val amount = CurrencyAmount(100.0)
        val fromCurrency = CurrencyCode("USD")
        val toCurrency = CurrencyCode("EUR")

        // when
        val result = financialService.convertCurrency(amount, fromCurrency, toCurrency)

        // then
        assertEquals(93.0, result.amount)
    }

    @Test
    fun convertCurrency_InvalidFromCurrencyCode_ThrowsException() {
        // given
        val invalidFromCurrency = "US" // Not matching the required format

        // when & then
        assertThrows(IllegalArgumentException::class.java) {
            CurrencyCode(invalidFromCurrency)
        }
    }

    @Test
    fun convertCurrency_InvalidToCurrencyCode_ThrowsException() {
        // given
        val invalidToCurrency = "EU" // Not matching the required format

        // when & then
        assertThrows(IllegalArgumentException::class.java) {
            CurrencyCode(invalidToCurrency)
        }
    }

    @Test
    fun convertCurrency_NegativeAmount_ThrowsException() {
        // given
        val negativeAmount = -100.0

        // when & then
        assertThrows(IllegalArgumentException::class.java) {
            CurrencyAmount(negativeAmount)
        }
    }

    @Test
    fun convertCurrency_ExpectedConversionRate_USDtoEUR() {
        // given
        val amount = CurrencyAmount(100.0)
        val fromCurrency = CurrencyCode("USD")
        val toCurrency = CurrencyCode("EUR")

        // when
        val result = financialService.convertCurrency(amount, fromCurrency, toCurrency)

        // then
        assertEquals(93.0, result.amount)
    }

    @Test
    fun convertCurrency_ExpectedConversionRate_USDtoGBP() {
        // given
        val amount = CurrencyAmount(100.0)
        val fromCurrency = CurrencyCode("USD")
        val toCurrency = CurrencyCode("GBP")

        // when
        val result = financialService.convertCurrency(amount, fromCurrency, toCurrency)

        // then
        assertEquals(82.0, result.amount)
    }

    @Test
    fun convertCurrency_ExpectedConversionRate_UnsupportedPair() {
        // given
        val amount = CurrencyAmount(100.0)
        val fromCurrency = CurrencyCode("EUR")
        val toCurrency = CurrencyCode("JPY")

        // when
        val result = financialService.convertCurrency(amount, fromCurrency, toCurrency)

        // then
        assertEquals(100.0, result.amount) // Expecting a 1:1 conversion rate for unsupported pairs
    }

    // Tests for CurrencyAmount
    @Test
    fun currencyAmount_ValidAmount_Success() {
        assertDoesNotThrow { CurrencyAmount(100.0) }
    }

    @Test
    fun currencyAmount_NegativeAmount_ThrowsException() {
        assertThrows(IllegalArgumentException::class.java) {
            CurrencyAmount(-100.0)
        }
    }

    @Test
    fun currencyAmount_ZeroAmount_Success() {
        assertDoesNotThrow { CurrencyAmount(0.0) }
    }

    // Tests for CurrencyCode
    @Test
    fun currencyCode_ValidCode_Success() {
        assertDoesNotThrow {
            CurrencyCode("USD")
        }
    }

    @Test
    fun currencyCode_InvalidCode_ThrowsException() {
        assertThrows(IllegalArgumentException::class.java) {
            CurrencyCode("usd")
        } // not uppercase
        assertThrows(IllegalArgumentException::class.java) {
            CurrencyCode("EU")
        } // not 3 letters
        assertThrows(IllegalArgumentException::class.java) {
            CurrencyCode("EURO")
        } // more than 3 letters
    }

    // Tests for AccountNumber
    @Test
    fun accountNumber_ValidNumber_Success() {
        assertDoesNotThrow {
            AccountNumber("1234567890")
        }
    }

    @Test
    fun accountNumber_InvalidNumberFormat_ThrowsException() {
        assertThrows(IllegalArgumentException::class.java) {
            AccountNumber("123456789")
        } // less than 10 digits
        assertThrows(IllegalArgumentException::class.java) {
            AccountNumber("12345678901")
        } // more than 10 digits
        assertThrows(IllegalArgumentException::class.java) {
            AccountNumber("12345abcde")
        } // contains letters
    }

    // Tests for TransactionId
    @Test
    fun transactionId_ValidId_Success() {
        assertDoesNotThrow {
            TransactionId("txn123")
        }
    }

    @Test
    fun transactionId_EmptyId_ThrowsException() {
        assertThrows(IllegalArgumentException::class.java) {
            TransactionId("")
        }
    }
}
