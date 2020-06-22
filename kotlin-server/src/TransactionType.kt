package com.example

enum class TransactionType {
    DEBIT,CREDIT;
    fun getFormatedName() = name.toLowerCase()
}
