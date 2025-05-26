#!/bin/zsh

print_help() {
  echo "require params"
  echo "./sub_sns_topic.sh <TOPIC_NAME> <SUBSCRIBER_QUEUE_URL>"
}

TOPIC_ARN=$1
SUBSCRIBER_QUEUE_URL=$2

if [ -z "$TOPIC_ARN" ] || [ -z "$SUBSCRIBER_QUEUE_URL" ]; then  print_help
  print_help
  exit 1
fi

OUTPUT=$(awslocal --topic-arn "$TOPIC_ARN" --protocool sqs --notification-endpoint "$SUBSCRIBER_QUEUE_URL")

echo "$OUTPUT"

exit 0