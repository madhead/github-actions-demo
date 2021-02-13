package me.madhead.gad

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse
import me.madhead.gad.dao.DynamoDbQuotesDao
import me.madhead.gad.service.QuotesService
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@Suppress("unused")
class Handler : RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    private val quotesService = QuotesService(
            quotesDao = DynamoDbQuotesDao(
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
            body = "Quote of the day: " + quotesService.getRandomQuote()
        }
    }
}
