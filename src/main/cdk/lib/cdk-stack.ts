import * as cdk from '@aws-cdk/core';
import {Duration} from '@aws-cdk/core';
import * as apigateway from "@aws-cdk/aws-apigateway";
import * as lambda from "@aws-cdk/aws-lambda";
import * as dynamodb from "@aws-cdk/aws-dynamodb";

export class CdkStack extends cdk.Stack {
    constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
        super(scope, id, props);

        const quotesTable = new dynamodb.Table(this, 'github-actions-demo-quotes', {
            tableName: 'github-actions-demo-quotes',
            partitionKey: {name: 'id', type: dynamodb.AttributeType.STRING},
            billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
        });

        const quotesLambda = new lambda.Function(this, 'github-actions-demo-lambda', {
            functionName: 'github-actions-demo-lambda',
            runtime: lambda.Runtime.JAVA_11,
            timeout: Duration.seconds(30),
            memorySize: 1024,
            code: lambda.Code.fromAsset('../../../build/libs/github-actions-demo-all.jar'),
            handler: 'me.madhead.gad.Handler',
            environment: {
                'LOG_THRESHOLD': 'DEBUG',
                'TABLE_QUOTES': quotesTable.tableName,
            },
        });

        quotesTable.grantReadData(quotesLambda);

        const quotesApi = new apigateway.RestApi(this, 'github-actions-demo', {
            restApiName: 'github-actions-demo',
            cloudWatchRole: false,
            endpointTypes: [apigateway.EndpointType.REGIONAL],
            deployOptions: {
                loggingLevel: apigateway.MethodLoggingLevel.INFO,
                dataTraceEnabled: true,
                metricsEnabled: true,
                tracingEnabled: true,
            },
        });

        quotesApi.root.addMethod('GET', new apigateway.LambdaIntegration(quotesLambda))

        new cdk.CfnOutput(this, 'URL', {
            value: quotesApi.deploymentStage.urlForPath()
        })
    }
}
