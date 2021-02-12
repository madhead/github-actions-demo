package me.madhead.gad.dao

import by.dev.madhead.aws_junit5.common.AWSClient
import by.dev.madhead.aws_junit5.dynamo.v2.DynamoDB
import me.madhead.gad.entity.Quote
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@ExtendWith(DynamoDB::class)
@Tag("db")
internal class DynamoDbQuotesDaoTest {
    @AWSClient(endpoint = Endpoint::class)
    lateinit var dynamoDB: DynamoDbClient

    @Test
    fun `getRandomQuote should return a quote`() {
        val sut = DynamoDbQuotesDao(dynamoDB, "test")

        Assertions.assertEquals(
                setOf(
                        Quote("001", "Quote 1"),
                        Quote("002", "Quote 2"),
                        Quote("003", "Quote 3"),
                ),
                sut.list().toSet()
        )
    }
}
