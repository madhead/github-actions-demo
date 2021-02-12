package me.madhead.gad

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse
import me.madhead.gad.dao.QuotesDao
import me.madhead.gad.service.QuotesService
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@Suppress("unused")
class Handler : RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    val quotesService = QuotesService(
            quotesDao = QuotesDao(
                    dynamoDb = DynamoDbClient.create(),
                    table = System.getenv("TABLE_QUOTES")
            )
    )

    override fun handleRequest(input: APIGatewayV2HTTPEvent?, context: Context?): APIGatewayV2HTTPResponse {
        return APIGatewayV2HTTPResponse().apply {
            statusCode = 200
            headers = mapOf(
                    "Content-Type" to "text/plain"
            )
            body = quotesService.getRandomQuote()
        }
    }
}
