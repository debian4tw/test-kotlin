package com.example

import io.ktor.html.insert

class TransactionsManager {
    private var transactions: MutableMap<String,Any> = mutableMapOf<String, Any>()
    private var accountStatus: Int = 0

    constructor()

    @Synchronized fun processTransaction(type: String, amount: Int): Any? {
        if (!this.validateData(type, amount)) {
            return null
        }

        val transactionType = if(type == TransactionType.CREDIT.getFormatedName()) TransactionType.CREDIT else TransactionType.DEBIT

        if (transactionType == TransactionType.DEBIT && this.accountStatus - amount < 0) {
            return null
        }

        when(transactionType) {
            TransactionType.DEBIT -> {
                this.accountStatus -= amount
            }
            TransactionType.CREDIT -> this.accountStatus += amount
        }

        val transactionToAdd = Transaction(transactionType, amount)
        this.transactions.put(transactionToAdd.getId(), transactionToAdd.serialize())

        return transactionToAdd.serialize()
    }

    fun findById(id: String): Any? {
        if (this.transactions.get(id) != null) {
            return this.transactions[id]
        }
        return null
    }

    private fun validateData(type: String, amount: Int): Boolean {
        if ((type != TransactionType.CREDIT.getFormatedName() || type != TransactionType.DEBIT.getFormatedName())
            && amount < 0) {
            return false
        }

        return true
    }

    fun get(): MutableCollection<Any> {
        return this.transactions.values
    }
}