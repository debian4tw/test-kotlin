package com.example
import java.util.*
class Transaction {
    private val type: TransactionType
    private val id: String
    private val effectiveDate: Date
    private val amount: Int

    constructor(type: TransactionType, amount: Int) {
        this.id = UUID.randomUUID().toString()
        this.type = type
        this.amount = amount
        this.effectiveDate = Date()
    }

    fun serialize(): Any {
        return mapOf(
            "id" to this.id,
            "type" to this.type.getFormatedName(),
            "amount" to this.amount,
            "effectiveDate" to this.effectiveDate.toString()
        )
    }

    fun getId(): String {
        return this.id
    }
}