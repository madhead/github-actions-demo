package me.madhead.gad.entity

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

data class Quote(
        val id: String,
        val quote: String,
)

fun Map<String, AttributeValue>.toQuote(): Quote = Quote(
        id = this["id"]?.s() ?: throw IllegalStateException("Missing id property"),
        quote = this["quote"]?.s() ?: throw IllegalStateException("Missing quote property"),
)
