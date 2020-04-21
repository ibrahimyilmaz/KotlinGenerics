package contravarience

sealed class PaymentMethod(open val amount: Int) {
    class Cash(override val amount: Int) : PaymentMethod(amount)
    class Card(override val amount: Int) : PaymentMethod(amount)
}


interface Store<in T> where T : PaymentMethod {
    fun acceptPayment(paymentMethod: T)
}

class OnlyCashAcceptingStore : Store<PaymentMethod.Cash> {
    override fun acceptPayment(paymentMethod: PaymentMethod.Cash) = Unit
}

class AnyPaymentMethodAcceptingStore : Store<PaymentMethod> {
    override fun acceptPayment(paymentMethod: PaymentMethod) = Unit
}

fun main() {
    /***
     *  we can define our  more genetic payment store  as more specific one
     *  ```kotlin
     *     val store:Store<PaymentMethod.Cash> =  AnyPaymentMethodAcceptingStore()
     *  ```
     *  because it is defined as contravariant.
     */

    val store = AnyPaymentMethodAcceptingStore()

    (store as Store<PaymentMethod.Cash>).acceptPayment(PaymentMethod.Cash(10))
}

