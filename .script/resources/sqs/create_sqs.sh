#!/bin/bash

SQS_NAME=$1

awslocal --profile localstack create-queue --queue-name "$SQS_NAME"

QUEUE_URL=$(awslocal --profile localstack sqs get-queue-url --queue-name "$SQS_NAME" --query "QueueUrl" --output text)

echo "$QUEUE_URL"