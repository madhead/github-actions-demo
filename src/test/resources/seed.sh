#!/usr/bin/env sh

set -x

aws --version
aws --endpoint-url "${DYNAMODB_URL}" dynamodb delete-table --table-name test || true
aws --endpoint-url "${DYNAMODB_URL}" dynamodb create-table --cli-input-json file://src/test/resources/test.table.json
aws --endpoint-url "${DYNAMODB_URL}" dynamodb batch-write-item --request-items file://src/test/resources/test.items.json
