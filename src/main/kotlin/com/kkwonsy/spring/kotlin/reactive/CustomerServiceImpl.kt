package com.kkwonsy.spring.kotlin.reactive

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerServiceImpl : CustomerService {

    companion object {
        val initialCustomers = arrayOf(
                Customer(1, "Kotlin")
                , Customer(2, "Java")
                , Customer(3, "Javascript")
                , Customer(4, "Python"))
    }

    val customers = ConcurrentHashMap(initialCustomers.associateBy(Customer::id))

    override fun getCustomer(id: Int) = customers[id]?.toMono()

    override fun searchCustomers(nameFilter: String): Flux<Customer> =
            customers
                    .filter { it.value.name.contains(nameFilter, true) }
                    .map { it.value }.toFlux()
}