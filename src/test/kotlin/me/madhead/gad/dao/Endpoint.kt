package me.madhead.gad.dao

import by.dev.madhead.aws_junit5.common.AWSEndpoint

class Endpoint : AWSEndpoint {
    override fun url(): String = System.getenv("DYNAMODB_URL")!!

    override fun region(): String = System.getenv("AWS_DEFAULT_REGION")!!

    override fun accessKey(): String = System.getenv("AWS_ACCESS_KEY_ID")!!

    override fun secretKey(): String = System.getenv("AWS_SECRET_ACCESS_KEY")!!
}
