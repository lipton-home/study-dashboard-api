#!/bin/zsh

print_help() {
  echo "require params"
  echo "./create_sqs.sh <SQS_NAME>"
}

SQS_NAME=$1

if [ -z "$SQS_NAME" ]; then
  print_help
  exit 1
fi

awslocal create-queue --queue-name "$SQS_NAME"

QUEUE_URL=$(awslocal sqs get-queue-url --queue-name "$SQS_NAME" --query "QueueUrl" --output text)

echo "$QUEUE_URL"

exit 0