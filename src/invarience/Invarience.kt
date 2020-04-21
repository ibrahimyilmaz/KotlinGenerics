package invarience

sealed class PaymentMethod(open val amount: Int) {
    class Cash(override val amount: Int) : PaymentMethod(amount)
    class Card(override val amount: Int) : PaymentMethod(amount)
}

interface PaymentProvider<T : PaymentMethod> {
    fun providePaymentMethod(amount: Int): T
}

class CashPaymentProvider : PaymentProvider<PaymentMethod.Cash> {
    override fun providePaymentMethod(amount: Int) = PaymentMethod.Cash(amount)
}

class CardPaymentProvider : PaymentProvider<PaymentMethod.Card> {
    override fun providePaymentMethod(amount: Int) = PaymentMethod.Card(amount)
}

fun main() {
    /***
     *  we cannot define our paymentProvider as PaymentProvider<PaymentMethod>
     *  ```kotlin
     *     val paymentProvider:PaymentProvider<PaymentMethod> = CashPayment()
     *  ```
     *  because it is defined as invariant.
     */

    val paymentProvider = CashPaymentProvider()
    val paymentMethod = paymentProvider.providePaymentMethod(10)
}