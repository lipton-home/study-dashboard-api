#!/bin/zsh

print_help() {
  echo "require params"
  echo "./create_sns.sh <TOPIC_NAME>"
}

TOPIC_NAME=$1

if [ -z "$TOPIC_NAME" ]; then
  print_help
  exit 1
fi

TOPIC_ARN=$(awslocal sns create-topic --name "$TOPIC_NAME" --query "TopicArn" --output text)

echo "$TOPIC_ARN"

exit 0