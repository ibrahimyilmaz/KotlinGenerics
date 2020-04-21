# Kotlin Generics
This repository is created to show example for the medium post which you can find here:
[Covariance and Contravariance in Kotlin](https://medium.com/@cs.ibrahimyilmaz/covariance-and-contravarience-in-kotlin-4644b80f4611)

## Invariance
```kotlin
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
```
## Covariance
```kotlin
sealed class PaymentMethod(open val amount: Int) {
    class Cash(override val amount: Int) : PaymentMethod(amount)
    class Card(override val amount: Int) : PaymentMethod(amount)
}

interface PaymentProvider<out T : PaymentMethod> {
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
     *  we can define our paymentProvider as PaymentProvider<PaymentMethod>
     *  ```kotlin
       *     val paymentProvider:PaymentProvider<PaymentMethod> = CashPayment()
     *  ```
     *  because it is defined as covariant.
     */

    val paymentProvider: PaymentProvider<PaymentMethod> = CashPaymentProvider()
    val paymentMethod = paymentProvider.providePaymentMethod(10)
}
```
and 
## Contravariance
```kotlin
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

```
