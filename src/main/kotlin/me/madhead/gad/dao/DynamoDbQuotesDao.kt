package me.madhead.gad.dao

import me.madhead.gad.entity.Quote
import me.madhead.gad.entity.toQuote
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

class DynamoDbQuotesDao(
        private val dynamoDb: DynamoDbClient,
        private val table: String
) : QuotesDao {
    override fun list(): List<Quote> {
        return dynamoDb
                .scan {
                    it.tableName(table)
                }
                ?.items()
                ?.map {
                    it.toQuote()
                } ?: emptyList()
    }
}
